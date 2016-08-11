package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Util;
import org.assertj.core.util.Strings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DataFileBuilder {
    private String csvFile;
    private String txtFile;
    private String subDirPath;
    private List<String> content;

    public void build() throws IOException {
        File file = null;
        if(csvFile!=null)
            file = GaugeProject.currentProject.createCsv(csvFile, subDirPath);

        if(txtFile!=null)
            file = GaugeProject.currentProject.createTxt(txtFile,subDirPath);

        Util.writeToFile(file.getAbsolutePath(), (content==null||content.isEmpty())? this.toString() : String.join("\n", content));
    }

    public DataFileBuilder withSubDirPath(String subDirPath){
        this.subDirPath = subDirPath;
        return this;
    }

    public DataFileBuilder withCsvFile(String csvFile){
        this.csvFile = csvFile;
        return this;
    }

    public DataFileBuilder withTextFile(String txtFile){
        this.txtFile = txtFile;
        return this;
    }

    public DataFileBuilder withContent(List<String> content) {
        this.content = content;
        return this;
    }
}
