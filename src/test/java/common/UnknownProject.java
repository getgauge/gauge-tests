package common;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.Map;

public class UnknownProject extends GaugeProject {
    public UnknownProject(File projectDir, String language) {
        super(projectDir, language);
    }

    @Override
    public void implementStep(String stepText, String implementation) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        throw new NotImplementedException();
    }
}
