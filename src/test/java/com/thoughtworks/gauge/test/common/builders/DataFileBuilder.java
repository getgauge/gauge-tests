package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.test.common.GaugeProject;

public class DataFileBuilder {
    private String csvFile;
    private String txtFile;
    private String subDirPath;

    public void build()
    {
        if(csvFile!=null)
            GaugeProject.currentProject.createCsv(csvFile,subDirPath);

        if(txtFile!=null)
            GaugeProject.currentProject.createTxt(txtFile,subDirPath);
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
