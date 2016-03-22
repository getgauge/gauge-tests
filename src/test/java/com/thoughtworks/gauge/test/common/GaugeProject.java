package com.thoughtworks.gauge.test.common;


import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.connection.GaugeConnection;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class GaugeProject {

    public static final String PRODUCT_ROOT = "GAUGE_ROOT";
    public static final String PRODUCT_PREFIX = "GAUGE_";
    public static final String PRINT_PARAMS = "print params";
    public static final String THROW_EXCEPTION = "throw exception";
    private static final String EXAMPLE_SPEC = "hello_world";
    public static GaugeProject currentProject;
    private static String executableName = "gauge";
    private static String specsDirName = "specs";
    private static String conceptsDirName = "concepts";
    private ArrayList<Concept> concepts = new ArrayList<Concept>();
    private File projectDir;
    private String language;
    private Process lastProcess = null;
    private ArrayList<Specification> specifications = new ArrayList<Specification>();
    private String lastProcessStdout;
    private GaugeService service;

    protected GaugeProject(File projectDir, String language) {
        this.projectDir = projectDir;
        this.language = language;
        currentProject = this;
    }

    public static GaugeProject getCurrentProject() {
        if (currentProject == null) {
            throw new RuntimeException("Gauge project is not initialized yet");
        }
        return currentProject;
    }

    public static GaugeProject createProject(File projectDir, String language) {
        if (language.equalsIgnoreCase("java")) {
            return new JavaProject(projectDir);
        } else if (language.equalsIgnoreCase("ruby")) {
            return new RubyProject(projectDir);
        } else if (language.equalsIgnoreCase("csharp")) {
            return new CSharpProject(projectDir);
        } else if (language.equalsIgnoreCase("js")) {
            return new JSProject(projectDir);
        } else if (language.equalsIgnoreCase("python")) {
            return new PythonProject(projectDir);
        }

        return new UnknownProject(projectDir, language);
    }

    public void createGaugeService() throws IOException, InterruptedException {
        int freePortForApi = SocketUtils.findFreePortForApi();
        Process process = currentProject.executeGaugeDaemon(freePortForApi);
        GaugeConnection gaugeConnection = initializeGaugeConnection(freePortForApi);
        service = new GaugeService(process, gaugeConnection);
    }

    private static GaugeConnection initializeGaugeConnection(int apiPort) {
        if (apiPort != -1) {
            return new GaugeConnection(apiPort);
        } else {
            return null;
        }
    }

    public void addConcepts(Concept... newConcepts) {
        for (Concept concept : newConcepts) {
            concepts.add(concept);
        }
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public boolean initialize() throws Exception {
        executeGaugeCommand("--init", language);
        return lastProcess.exitValue() == 0;
    }

    public String getStdOut() throws IOException {
        return lastProcessStdout;
    }

    public File getProjectDir() {
        return projectDir;
    }

    public Specification createSpecification(String name) throws IOException {
        File specFile = getSpecFile(name);
        if (specFile.exists()) {
            throw new RuntimeException("Failed to create specification with name: " + name + "." + specFile.getAbsolutePath() + ": File already exists");
        }
        Specification specification = new Specification(name);
        specification.saveAs(specFile);
        specifications.add(specification);
        return specification;
    }

    private File getSpecFile(String name) {
        return new File(projectDir, Util.combinePath(specsDirName, name) + ".spec");
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

    public boolean execute(boolean sorted) throws Exception {
        if (sorted) {
            return executeGaugeCommand("--simple-console", "--verbose", "--sort", "specs/");
        }
        return executeGaugeCommand("--simple-console", "--verbose", "specs/");
    }

    public boolean validate() throws IOException, InterruptedException {
        return executeGaugeCommand("--validate", "specs/");
    }

    public boolean executeSpec(String specName) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", "specs" + File.separator + specName + ".spec");
    }

    public boolean executeSpecWithScenarioIndex(String specName, int index) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", "specs" + File.separator + specName + ".spec:" + index);
    }

    public boolean executeSpecWithRowRange(String specName, String rowRange) throws Exception {
        return executeGaugeCommand("--simple-console", "--verbose", "--table-rows", rowRange, "specs" + File.separator + specName + ".spec");
    }

    public boolean executeTagsInSpec(String tags, String specName) throws IOException, InterruptedException {
        return executeGaugeCommand("--simple-console", "--verbose", "--tags", tags, "specs" + File.separator + specName + ".spec");
    }

    public boolean executeRefactor(String oldStep, String newStep) throws IOException, InterruptedException {
        return executeGaugeCommand("--refactor", oldStep, newStep);
    }

    public Process executeGaugeDaemon(Integer apiPort) throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<String>();
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

    public boolean executeGaugeCommand(String... args) throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<String>();
        command.add(executableName);
        for (String arg : args) {
            command.add(arg);
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command.toArray(new String[command.size()]));
        processBuilder.directory(projectDir);
        String gauge_project_root = System.getenv("GAUGE_PROJECT_ROOT");
        String localNugetPath = Paths.get(gauge_project_root, "resources", "LocalNuget").toAbsolutePath().toString();
        processBuilder.environment().put("NUGET_ENDPOINT", localNugetPath);

        filterConflictingEnv(processBuilder);
        lastProcess = processBuilder.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(lastProcess.getInputStream()));
        String line = "";
        String newLine = System.getProperty("line.separator");
        lastProcessStdout = "";
        while((line = br.readLine()) != null) {
            lastProcessStdout = lastProcessStdout.concat(line).concat(newLine);
        }

        lastProcess.waitFor();
        return lastProcess.exitValue() == 0;
    }

    public void deleteSpec(String specName) throws IOException {
        FileUtils.forceDelete(getSpecFile(specName));
    }

    private void filterConflictingEnv(ProcessBuilder processBuilder) {
        for (String env : processBuilder.environment().keySet()) {
            if (!env.toUpperCase().equals(PRODUCT_ROOT) && env.toUpperCase().contains(PRODUCT_PREFIX)) {
                processBuilder.environment().put(env, "");
            }
        }
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
