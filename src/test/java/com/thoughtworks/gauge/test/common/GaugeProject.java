package com.thoughtworks.gauge.test.common;


import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.connection.GaugeConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class GaugeProject {

    private static final String PRODUCT_ROOT = "GAUGE_ROOT";
    private static final String PRODUCT_PREFIX = "GAUGE_";
    static final String PRINT_PARAMS = "print params";
    static final String THROW_EXCEPTION = "throw exception";
    public static GaugeProject currentProject;
    private static String executableName = "gauge";
    private static String specsDirName = "specs";
    private ArrayList<Concept> concepts = new ArrayList<>();
    private File projectDir;
    private String language;
    private ArrayList<Specification> specifications = new ArrayList<>();
    private String lastProcessStdout;
    private GaugeService service;

    protected GaugeProject(String language, String projName) throws IOException {
        this.language = language;
        currentProject = this;

        this.projectDir = Files.createTempDirectory(projName + "_").toFile();
        this.projectDir.deleteOnExit();
    }

    public static GaugeProject getCurrentProject() {
        if (currentProject == null) {
            throw new RuntimeException("Gauge project is not initialized yet");
        }
        return currentProject;
    }

    public static GaugeProject createProject(String language, String projName) throws IOException {
        switch (language.toLowerCase()) {
            case "java":
                return new JavaProject(projName);
            case "ruby":
                return new RubyProject(projName);
            case "csharp":
                return new CSharpProject(projName);
            case "js":
                return new JavascriptProject(projName);
            case "python":
                return new PythonProject(projName);
            default:
                return new UnknownProject(language, projName);
        }
    }

    public void createGaugeService() throws IOException, InterruptedException {
        int freePortForApi = SocketUtils.findFreePortForApi();
        Process process = currentProject.executeGaugeDaemon(freePortForApi);
        GaugeConnection gaugeConnection = initializeGaugeConnection(freePortForApi);
        service = new GaugeService(process, gaugeConnection);
    }

    private static GaugeConnection initializeGaugeConnection(int apiPort) {
        if (apiPort != -1)
            return new GaugeConnection(apiPort);
        return null;
    }

    public void addConcepts(Concept... newConcepts) {
        Collections.addAll(concepts, newConcepts);
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public boolean initialize() throws Exception {
        return executeGaugeCommand("--init", language);
    }

    public String getStdOut() throws IOException {
        return lastProcessStdout;
    }

    public File getProjectDir() {
        return projectDir;
    }

    public Specification createSpecification(String name) throws IOException {
        return createSpecification("", name);
    }

    public Specification createSpecification(String subDirPath, String name) throws IOException {
        File specFile = getSpecFile(name, subDirPath);
        if (specFile.exists()) {
            throw new RuntimeException("Failed to create specification with name: " + name + "." + specFile.getAbsolutePath() + ": File already exists");
        }
        Specification specification = new Specification(name);
        specification.saveAs(specFile);
        specifications.add(specification);
        return specification;
    }

    private File getSpecFile(String name, String subDirPath) {
        name = Util.getSpecName(name);
        String dirPath = Util.combinePath(specsDirName, subDirPath);
        if (!new File(projectDir, dirPath).exists()) {
            new File(projectDir, dirPath).mkdir();
        }
        return new File(projectDir, Util.combinePath(dirPath, name) + ".spec");
    }

    public Specification findSpecification(String specName) {
        for (Specification specification : specifications) {
            if (specification.getName().equalsIgnoreCase(specName)) {
                return specification;
            }
        }

        return null;
    }

    public Scenario findScenario(String scenarioName, List<Scenario> scenarios) {
        for (Scenario scenario : scenarios) {
            if (scenario.getName().equalsIgnoreCase(scenarioName)) {
                return scenario;
            }
        }
        return null;
    }


    public Concept createConcept(String name, Table steps) throws Exception {
        String specDirPath = new File(projectDir, specsDirName).getAbsolutePath();
        String conceptsDirName = "concepts";
        File conceptsDir = new File(specDirPath, conceptsDirName);
        if (!conceptsDir.exists()) {
            conceptsDir.mkdir();
        }
        File conceptFile = new File(conceptsDir, "concept_" + System.nanoTime() + ".cpt");
        if (conceptFile.exists()) {
            throw new RuntimeException("Failed to create concept: " + name + "." + conceptFile.getAbsolutePath() + " : File already exists");
        }
        Concept concept = new Concept(name);
        List<String> columnNames = steps.getColumnNames();
        for (TableRow row : steps.getTableRows()) {
            concept.addSteps(row.getCell(columnNames.get(0)));
            if (columnNames.size() == 2) {
                implementStep(row.getCell(columnNames.get(0)), row.getCell(columnNames.get(1)), false);
            }
        }
        concept.saveAs(conceptFile);
        concepts.add(concept);
        return concept;
    }

    public boolean executeSpecFolder(String specFolder) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", specFolder);
    }

    public boolean execute(boolean sorted) throws Exception {
        if (sorted) {
            return executeGaugeCommand("--simple-console", "--verbose", "--sort", "specs/");
        }
        return executeGaugeCommand("--simple-console", "--verbose", "specs/");
    }

    public boolean executeInParallel() throws IOException, InterruptedException {
        return executeGaugeCommand("--parallel", "--verbose", "specs/");
    }

    public boolean executeInParallel(int nStreams) throws IOException, InterruptedException {
        return executeGaugeCommand("--parallel", "-n=" + nStreams, "--verbose", "specs/");
    }

    public boolean validate() throws IOException, InterruptedException {
        return executeGaugeCommand("--validate", "specs/");
    }

    public boolean executeSpec(String specName) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", "specs" + File.separator + Util.getSpecName(specName) + ".spec");
    }

    public boolean executeSpecWithScenarioIndex(String specName, int index) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", "specs" + File.separator + Util.getSpecName(specName) + ".spec:" + index);
    }

    public boolean executeSpecWithRowRange(String specName, String rowRange) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", "--table-rows", rowRange, "specs" + File.separator + Util.getSpecName(specName) + ".spec");
    }

    public boolean executeTagsInSpec(String tags, String specName) throws IOException, InterruptedException {
        return executeGaugeCommand("--simple-console", "--verbose", "--tags", tags, "specs" + File.separator + Util.getSpecName(specName) + ".spec");
    }

    boolean executeRefactor(String oldStep, String newStep) throws IOException, InterruptedException {
        return executeGaugeCommand("--refactor", oldStep, newStep);
    }

    private Process executeGaugeDaemon(Integer apiPort) throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<>();
        command.add(executableName);
        command.add("--daemonize");
        command.add("--api-port");
        command.add(String.valueOf(apiPort));
        ProcessBuilder processBuilder = new ProcessBuilder(command.toArray(new String[command.size()]));
        processBuilder.directory(this.projectDir);
        filterConflictingEnv(processBuilder);
        Process process = processBuilder.start();
        Thread.sleep(3000);
        return process;
    }

    private boolean executeGaugeCommand(String... args) throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<>();
        command.add(executableName);
        Collections.addAll(command, args);
        ProcessBuilder processBuilder = new ProcessBuilder(command.toArray(new String[command.size()]));
        processBuilder.directory(projectDir);
        String gauge_project_root = System.getenv("GAUGE_PROJECT_ROOT");
        String localNugetPath = Paths.get(gauge_project_root, "resources", "LocalNuget").toAbsolutePath().toString();
        processBuilder.environment().put("NUGET_ENDPOINT", localNugetPath);

        filterConflictingEnv(processBuilder);
        Process lastProcess = processBuilder.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(lastProcess.getInputStream()));
        String line;
        String newLine = System.getProperty("line.separator");
        lastProcessStdout = "";
        while ((line = br.readLine()) != null) {
            lastProcessStdout = lastProcessStdout.concat(line).concat(newLine);
        }
        lastProcess.waitFor();
        return lastProcess.exitValue() == 0;
    }

    public void deleteSpec(String specName) {
        getSpecFile(specName, "").delete();
    }

    private void filterConflictingEnv(ProcessBuilder processBuilder) {
        processBuilder.environment().keySet().stream()
                .filter(env -> !env.toUpperCase().equals(PRODUCT_ROOT) && env.toUpperCase().contains(PRODUCT_PREFIX))
                .forEach(env -> processBuilder.environment().put(env, ""));
    }

    public abstract void implementStep(String stepText, String implementation, boolean appendCode) throws Exception;

    public abstract Map<String, String> getLanguageSpecificFiles();

    public abstract String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode);

    public abstract void createHookWithPrint(String hookLevel, String hookType, String implementation) throws Exception;

    public abstract void createHookWithException(String hookLevel, String hookType) throws IOException;

    public abstract void createHooksWithTagsAndPrintMessage(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws IOException;

    public abstract void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException;

    public String getLastProcessStdout() {
        return lastProcessStdout;
    }

    public ArrayList<Specification> getAllSpecifications() {
        return specifications;
    }

    public GaugeService getService() {
        return service;
    }

    public abstract String getDataStoreWriteStatement(TableRow row, List<String> columnNames);

    public abstract String getDataStorePrintValueStatement(TableRow row, List<String> columnNames);
}
