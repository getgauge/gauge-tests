package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.StepImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RubyProject extends GaugeProject {
    private static final String DEFAULT_AGGREGATION = "AND";
    private static final Object BUNDLE_INSTALL_LOCK = new Object();

    public RubyProject(String projName) throws IOException {
        super("ruby", projName);
    }

    private StringBuilder createStepTemplate(ArrayList<String> stepTexts) {
        StringBuilder step = new StringBuilder();
        if (stepTexts.size() == 1) {
            return step.append("step '").append(stepTexts.get(0)).append("'");
        } else {
            StringBuilder commaSeparated = new StringBuilder();
            for (int i = 0; i < stepTexts.size(); i++) {
                commaSeparated.append("\"").append(stepTexts.get(i)).append("\"");
                if (i != stepTexts.size() - 1) commaSeparated.append(",");
            }
            return step.append("step ").append(commaSeparated);
        }
    }

    @Override
    public boolean initialize(boolean remoteTemplate) throws Exception {
        if (!remoteTemplate) {
            String gauge_project_root = System.getenv("GAUGE_PROJECT_ROOT");
            Path templatePath = Paths.get(gauge_project_root, "resources", "LocalTemplates", "ruby");
            Path lock = templatePath.resolve(".buildlock");
            synchronized (BUNDLE_INSTALL_LOCK) {
                if (!lock.toFile().exists()) {
                    Files.createFile(lock);
                    ExecutorService pool = Executors.newCachedThreadPool();
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder(Util.isWindows() ? "bundle.bat" : "bundle", "install");
                        processBuilder.directory(templatePath.toFile());
                        processBuilder.environment().remove("CLASSPATH");
                        Process process = processBuilder.start();

                        CompletableFuture<String> stdout = CompletableFuture.supplyAsync(() -> readSafely(process.getInputStream()), pool);
                        CompletableFuture<String> stderr = CompletableFuture.supplyAsync(() -> readSafely(process.getErrorStream()), pool);

                        if (!process.waitFor(5, TimeUnit.MINUTES)) {
                            process.descendants().forEach(handle -> { if (handle.isAlive()) handle.destroyForcibly(); });
                            if (process.isAlive()) process.destroyForcibly();
                        }

                        lastProcessStdout = stdout.get();
                        lastProcessStderr = stderr.get();

                        if (process.exitValue() != 0) {
                            return false;
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        lock.toFile().delete();
                        return false;
                    } catch (Exception ex) {
                        lastProcessStderr = ExceptionUtils.getStackTrace(ex);
                        lock.toFile().delete();
                        return false;
                    } finally {
                        pool.shutdownNow();
                    }
                }
            }
        }
        return super.initialize(remoteTemplate);
    }

    private static String readSafely(InputStream stream) {
        try (InputStream is = stream) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void implementStep(StepImpl stepImpl) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor stepValueExtractor = new StepValueExtractor();
        StepValueExtractor.StepValue stepValue = stepValueExtractor.getFor(stepImpl.getFirstStepText());

        String fileName = Util.getUniqueName();
        StringBuilder rubyCode = new StringBuilder();
        rubyCode.append(createStepTemplate(stepValueExtractor.getValueFor(stepImpl.getAllStepTexts())));
        if (stepImpl.isContinueOnFailure()) {
            rubyCode.append(", :continue_on_failure => true");
        }
        rubyCode.append(" do |");
        for (int i = 0; i < stepValue.paramCount; i++) {
            if (i + 1 == stepValue.paramCount) {
                rubyCode.append("param").append(i);
            } else {
                rubyCode.append("param").append(i).append(", ");
            }
            paramTypes.add("string");
        }
        rubyCode.append("|\n").append(getStepImplementation(stepValue, stepImpl.getImplementation(), paramTypes, stepImpl.isValidStatement())).append("\nend");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), fileName + ".rb"), rubyCode.toString());
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("step_implementations", "dir");
        map.put(Util.combinePath("step_implementations", "step_implementation.rb"), "file");
        map.put(Util.combinePath("env", "default", "ruby.properties"), "file");
        return map;
    }

    @Override
    public List<String> getLanguageSpecificGitIgnoreText() {
        return new ArrayList<String>() {{
            add("# Gauge - metadata dir\n.gauge");
            add("# Gauge - log files dir\nlogs");
            add("# Gauge - reports generated by reporting plugins\nreports\n\n");
            add("# Gauge - ruby dependencies dir\nvendor\n");
        }};
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode) {
        StringBuilder builder = new StringBuilder();
        if (implementation.equalsIgnoreCase(PRINT_PARAMS)) {
            builder.append("puts ");
            for (int i = 0; i < stepValue.paramCount; i++) {
                builder.append("\"param").append(i).append("=\"+").append("param").append(i).append(".to_s");
                if (i != stepValue.paramCount - 1) {
                    builder.append("+\",\"+");
                }
            }
            builder.append("\n");
        } else if (implementation.equalsIgnoreCase(THROW_EXCEPTION)) {
            builder.append("raise \" exception raised \" \n \n");
        } else if (implementation.equalsIgnoreCase(CAPTURE_SCREENSHOT)) {
            builder.append("Gauge.capture\n \n");
        } else {
            if (appendCode) {
                builder.append(implementation);
            } else {
                builder.append("puts ").append(implementation).append(".to_s").append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String printString) throws IOException {
        createHook(hookLevel, hookType, printString, DEFAULT_AGGREGATION, new ArrayList<String>());
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        StringBuilder rubyFileText = new StringBuilder();
        rubyFileText.append(String.format("%s_%s do \n raise \"exception was raised\"\nend", hookType, hookLevel));
        rubyFileText.append("\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), Util.getUniqueName() + ".rb"), rubyFileText.toString());
    }

    @Override
    public void createHooksWithTagsAndPrintMessage(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws IOException {
        createHook(hookLevel, hookType, printString, aggregation, tags.getColumnValues("tags"));
    }

    public void createHook(String hookLevel, String hookType, String printString, String aggregation, List<String> tags) throws IOException {
        StringBuilder rubyFileText = new StringBuilder();
        rubyFileText.append(String.format("%s_%s(%s) do \n puts \"%s\"\nend", hookType, hookLevel, getOptions(aggregation, tags), printString));
        rubyFileText.append("\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), Util.getUniqueName() + ".rb"), rubyFileText.toString());
    }

    @Override
    public String getDataStoreWriteStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell("datastore type");
        String key = row.getCell("key");
        String value = row.getCell("value");
        return "Gauge::DataStoreFactory." + dataStoreType.toLowerCase() + "_datastore.put(\"" + key + "\", \"" + value + "\")";
    }

    private String getOptions(String aggregation, List<String> tags) {
        String tagsText = Util.joinList(Util.quotifyValues(tags));
        return String.format("{tags: [%s], operator: '%s'}", tagsText, aggregation);
    }

    @Override
    public String getDataStorePrintValueStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell("datastore type");
        String key = row.getCell("key");
        return "puts Gauge::DataStoreFactory." + dataStoreType.toLowerCase() + "_datastore.get(\"" + key + "\")";
    }

    @Override
    public void configureCustomScreengrabber(String screenshotFile) throws IOException {
        StringBuilder rubyFileText = new StringBuilder();
        rubyFileText.append("Gauge.configure {|c| c.custom_screenshot_writer =  -> { return \"" + screenshotFile + "\" }}");
        rubyFileText.append("\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), Util.getUniqueName() + ".rb"), rubyFileText.toString());
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "step_implementations").getAbsolutePath();
    }
}
