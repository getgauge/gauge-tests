package common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RubyProject extends GaugeProject {
    public RubyProject(File projectDir) {
        super(projectDir, "ruby");
    }

    @Override
    public void implementStep(String stepText, String implementation) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = String.format("Steps%d%s", System.nanoTime(), dateFormat.format(new Date()));
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
        rubyCode.append("|\n").append(getStepImplemetation(stepValue, implementation, paramTypes)).append("\nend");
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
    public String getStepImplemetation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes) {
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
        } else {
            builder.append("puts ").append(implementation).append("\n");
        }
        return builder.toString();
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "step_implementations").getAbsolutePath();
    }
}
