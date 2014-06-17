package common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RubyProject extends GaugeProject {
    public RubyProject(File projectDir) {
        super(projectDir, "ruby");
    }

    @Override
    public void implementStep(String stepText, String implementation) throws Exception {
        implementation = String.format("puts %s\n", implementation);
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
        }
        rubyCode.append("|\n").append(implementation).append("\nend");
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

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "step_implementations").getAbsolutePath();
    }
}
