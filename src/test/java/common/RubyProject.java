package common;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static common.Util.getUniqueName;

public class RubyProject extends GaugeProject {
    public RubyProject(File projectDir) {
        super(projectDir, "ruby");
    }

    @Override
    public void implementStep(String stepText, String implementation, boolean appendCode) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = getUniqueName();
        StringBuilder rubyCode = new StringBuilder();
        rubyCode.append("step '").append(stepValue.value).append("' do |");
        for (int i = 0; i < stepValue.paramCount; i++) {
            if (i + 1 == stepValue.paramCount) {
                rubyCode.append("param").append(i);
            } else {
                rubyCode.append("param").append(i).append(", ");
            }
            paramTypes.add("string");
        }
        rubyCode.append("|\n").append(getStepImplementation(stepValue, implementation, paramTypes, appendCode)).append("\nend");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), fileName + ".rb"), rubyCode.toString());
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("step_implementations", "dir");
        map.put("step_implementations/step_implementation.rb", "file");
        map.put("env/default/ruby.properties", "file");
        return map;
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode) {
        StringBuilder builder = new StringBuilder();
        if (implementation.toLowerCase().equals(PRINT_PARAMS)) {
            builder.append("puts ");
            for (int i = 0; i < stepValue.paramCount; i++) {
                if (paramTypes.get(i).toLowerCase().equals("string")) {
                    builder.append("\"param").append(i).append("=\"+").append("param").append(i);
                    if (i != stepValue.paramCount - 1) {
                        builder.append("+\",\"+");
                    }
                }
            }
            builder.append("\n");
        }
        else if(implementation.toLowerCase().equals(THROW_EXCEPTION)){
            builder.append("raise \" exception raised \" \n \n");
        }
        else {
            if (appendCode){
                builder.append(implementation);
            } else {
                builder.append("puts ").append(implementation).append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String printString) throws IOException {
        StringBuilder rubyFileText = new StringBuilder();
        rubyFileText.append(String.format("%s_%s do \n puts \"%s\"\nend", hookType, hookLevel, printString));
        rubyFileText.append("\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), getUniqueName() + ".rb"), rubyFileText.toString());
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        StringBuilder rubyFileText = new StringBuilder();
        rubyFileText.append(String.format("%s_%s do \n raise \"exception was raised\"\nend", hookType, hookLevel));
        rubyFileText.append("\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), getUniqueName() + ".rb"), rubyFileText.toString());
    }

    @Override
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {
        boolean exitStatus = currentProject.executeRefactor(oldStep, newStep);
        if (!exitStatus){
            System.out.println(currentProject.getLastProcessStdout());
            System.out.println(currentProject.getLastProcessStderr());
        }
    }

    @Override
    public String getDataStoreWriteStatement(List<String> row) {
        String dataStoreType = row.get(3);
        String key = row.get(1);
        String value = row.get(2);
        return "Gauge::DataStoreFactory." + dataStoreType.toLowerCase() + "_datastore.put(\"" + key + "\", \"" + value + "\")";
    }

    @Override
    public String getDataStorePrintValueStatement(List<String> row) {
        String dataStoreType = row.get(3);
        String key = row.get(1);
        return "puts Gauge::DataStoreFactory." + dataStoreType.toLowerCase() + "_datastore.get(\"" + key + "\")";
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "step_implementations").getAbsolutePath();
    }
}
