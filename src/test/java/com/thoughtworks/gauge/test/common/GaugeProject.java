package com.thoughtworks.gauge.test.common;


import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.connection.GaugeConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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
    private String lastProcessStderr;

    protected GaugeProject(String language, String projName) throws IOException {
        this.language = language;
        currentProject = this;

        this.projectDir = Files.createTempDirectory(projName + "_").toFile();
        registerShutDownHook();
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

    public Specification createSpecification(String specsDirName, String name) throws IOException {
        String specsDir = (specsDirName == null || specsDirName.isEmpty()) ? this.specsDirName : specsDirName;
        File specFile = getSpecFile(name, specsDir);
        if (specFile.exists()) {
            throw new RuntimeException("Failed to create specification with name: " + name + "." + specFile.getAbsolutePath() + ": File already exists");
        }
        Specification specification = new Specification(name);
        specification.saveAs(specFile);
        specifications.add(specification);
        return specification;
    }

    private File getSpecFile(String name, String dirPath) {
        name = Util.getSpecName(name);
        if (!new File(projectDir, dirPath).exists()) {
            new File(projectDir, dirPath).mkdirs();
        }
        return new File(projectDir, Util.combinePath(dirPath, name) + ".spec");
    }

    private File getSpecFile(String name) {
        return getSpecFile(name,"");
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
            concept.addItem(row.getCell(columnNames.get(0)), row.getCell("Type"));
            if (columnNames.size() == 2) {
                implementStep(row.getCell(columnNames.get(0)), row.getCell(columnNames.get(1)), false, false);
            }
        }
        concept.saveAs(conceptFile);
        concepts.add(concept);
        return concept;
    }

    public boolean executeSpecFolder(String specFolder) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", specFolder);
    }

    public boolean executeSpecFromFolder(String spec, String specFolder) throws Exception {
        File oldProjectDir = this.projectDir;
        this.projectDir = new File(oldProjectDir, specFolder);
        boolean exitCode = executeGaugeCommand("--simple-console", "--verbose", spec);
        this.projectDir = oldProjectDir;
        return exitCode;
    }

    public boolean formatSpecFolder(String specFolder) throws Exception {
        return executeGaugeCommand("--format", specFolder);
    }

    public ExecutionSummary execute(boolean sorted) throws Exception {
        String[] args = sorted ? new String[]{"--simple-console", "--verbose", "--sort", "specs/"} : new String[]{"--simple-console", "--verbose", "specs/"};
        return execute(args);
    }

    private ExecutionSummary execute(String[] args) throws Exception {
        boolean success = executeGaugeCommand(args);
        return new ExecutionSummary(String.join(" ", args), success, lastProcessStdout, lastProcessStderr);
    }

    public ExecutionSummary executeInParallel() throws Exception {
        return execute(new String[]{"--parallel", "--verbose", "specs/"});
    }

    public ExecutionSummary executeInParallel(int nStreams) throws Exception {
        return execute(new String[]{"--parallel", "-n=" + nStreams, "--verbose", "specs/"});
    }

    public ExecutionSummary validate() throws Exception {
        return execute(new String[]{"--validate", "specs/"});
    }

    public ExecutionSummary executeSpec(List<String> specNames) throws Exception {
        List<String> args = new ArrayList<>();
        args.add("--simple-console");
        args.add("--verbose");
        for (String specName : specNames) {
            args.add("specs" + File.separator + Util.getSpecName(specName) + ".spec");
        }
        return execute(args.toArray(new String[0]));
    }

    public ExecutionSummary executeSpec(String specName) throws Exception {
        return execute(new String[]{"--simple-console", "--verbose", "specs" + File.separator + Util.getSpecName(specName) + ".spec"});
    }

    public ExecutionSummary rerun() throws Exception {
        return execute(new String[]{"--simple-console", "--verbose", "--failed"});
    }

    public ExecutionSummary executeSpecWithScenarioLineNumber(String specName, int lineNumber) throws Exception {
        return execute(new String []{"--simple-console", "--verbose", "specs" + File.separator + Util.getSpecName(specName) + ".spec:" + lineNumber});
    }

    public ExecutionSummary executeSpecWithRowRange(String specName, String rowRange) throws Exception {
        return execute(new String[]{"--simple-console", "--verbose", "--table-rows", rowRange, "specs" + File.separator + Util.getSpecName(specName) + ".spec"});
    }

    public ExecutionSummary executeTagsInSpec(String tags, String specName) throws Exception {
        return execute(new String[]{"--simple-console", "--verbose", "--tags", tags, "specs" + File.separator + Util.getSpecName(specName) + ".spec"});
    }

    ExecutionSummary executeRefactor(String oldStep, String newStep) throws Exception {
        return execute(new String[]{"--refactor", oldStep, newStep, "specs"});
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
        lastProcessStderr = "";
        br = new BufferedReader(new InputStreamReader(lastProcess.getErrorStream()));
        while ((line = br.readLine()) != null) {
            lastProcessStderr = lastProcessStderr.concat(line).concat(newLine);
        }
        lastProcess.waitFor();
        return lastProcess.exitValue() == 0;
    }

    public void deleteSpec(String specName) {
        getSpecFile(specName).delete();
    }

    private void filterConflictingEnv(ProcessBuilder processBuilder) {
        processBuilder.environment().keySet().stream()
                .filter(env -> !env.toUpperCase().equals(PRODUCT_ROOT) && env.toUpperCase().contains(PRODUCT_PREFIX))
                .forEach(env -> processBuilder.environment().put(env, ""));
    }

    private void registerShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Path directory = Paths.get(projectDir.getAbsolutePath());
                try {
                    Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } catch (IOException e) {
                    // ignore
                }
            }
        });
    }

    public void refactorStep(String oldStep, String newStep) throws Exception {
        ExecutionSummary result = currentProject.executeRefactor(oldStep, newStep);
        if (!result.getSuccess()) {
            System.out.println(currentProject.getLastProcessStdout());
        }
    }

    public abstract void implementStep(String stepText, String implementation, boolean continueOnFailure, boolean appendCode) throws Exception;

    public abstract Map<String, String> getLanguageSpecificFiles();

    public abstract String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode);

    public abstract void createHookWithPrint(String hookLevel, String hookType, String implementation) throws Exception;

    public abstract void createHookWithException(String hookLevel, String hookType) throws IOException;

    public abstract void createHooksWithTagsAndPrintMessage(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws IOException;

    public String getLastProcessStdout() {
        return lastProcessStdout;
    }
    public String getLastProcessStderr() {
        return lastProcessStderr;
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
