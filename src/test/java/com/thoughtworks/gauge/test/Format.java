package com.thoughtworks.gauge.test;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Specification;
import com.thoughtworks.gauge.test.common.Util;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Format {
    @Step("Format specs and ensure failure")
    public void formatSpecs() throws Exception {
        assertThat(GaugeProject.getCurrentProject().formatSpecFolder("specs"))
                .withFailMessage("Formatting passed, expected a parse failure.")
                .isFalse();
    }

    @Step("Spec <spec formatting> should not be formatted")
    public void checkSpecIsNotChanged(String specName) throws IOException {
        Specification specification = GaugeProject.getCurrentProject().findSpecification(specName);
        String expectedContent = specification.toString();
        String actualContent = Util.read(specification.getSpecFile().getAbsolutePath());
        assertThat(expectedContent).isEqualTo(actualContent);
    }
}
