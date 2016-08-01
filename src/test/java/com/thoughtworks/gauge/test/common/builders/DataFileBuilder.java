package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Util;

import java.io.File;
import java.io.IOException;

public class DataFileBuilder {
    private String csvFile;
    private String txtFile;
    private String subDirPath;

    public void build() throws IOException {
        File file = null;
        if(csvFile!=null)
            file = GaugeProject.currentProject.createCsv(csvFile, subDirPath);


        if(txtFile!=null)
            file = GaugeProject.currentProject.createTxt(txtFile,subDirPath);

        Util.writeToFile(file.getAbsolutePath(), this.toString());
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

}
