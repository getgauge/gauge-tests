package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.builders.SpecificationBuilder;

public class Specification {
    @Step("Create a specification <spec name> with the following datatable <table>")
    public void createSpecWithDataTable(String specName, Table datatable) throws Exception {
        new SpecificationBuilder()
                .withSpecName(specName)
                .withDataTable(datatable)
                .buildAndAddToProject(false);
    }

    @Step("Update a specification <spec name> with the following datatable <table>")
    public void updateSpecification(String specName, Table datatable) throws Exception {
        new SpecificationBuilder()
                .withSpecName(specName)
                .withDataTable(datatable)
                .buildAndAddToProject(false);
    }
}