package common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RubyProject extends GaugeProject {
    public RubyProject(File projectDir) {
        super(projectDir, "ruby");
    }

    @Override
    public void implementStep(String stepText, String implementation) {

    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("step_implementations", "dir");
        map.put("step_implementations/step_implementation.rb", "file");
        map.put("env/default/ruby.properties", "file");
        return map;
    }
}
