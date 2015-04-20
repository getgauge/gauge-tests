package common;

import java.io.File;
import java.io.IOException;
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
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String implementation) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {

    }

    @Override
    public boolean isStepPresentInImpl(String oldStep, Integer paramCount, String implText) {
        return false;
    }
}
