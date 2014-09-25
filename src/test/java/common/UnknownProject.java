package common;

import java.io.File;
import java.util.List;
import java.util.Map;

public class UnknownProject extends GaugeProject {
    public UnknownProject(File projectDir, String language) {
        super(projectDir, language);
    }

    @Override
    public void implementStep(String stepText, String implementation) throws Exception {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getStepImplemetation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void createHook(String hookLevel, String hookType, String implementation) {
        throw new RuntimeException("Not implemented");
    }
}
