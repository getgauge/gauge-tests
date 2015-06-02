import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import common.GaugeProjectBuilder;

import java.util.Arrays;
import java.util.List;

import static common.GaugeProject.currentProject;

public class DataStore {
    @Step("Create a scenario <newScenario> in specification <newSpec> with steps to read and write to datastore <table>")
    public void addToDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        Table table = new Table(Arrays.asList("Step text", "implementation"));
        List<List<String>> rows = datastoreValues.getRows();

        List<String> row = rows.get(0);
        table.addRow(Arrays.asList(row.get(0), currentProject.getDataStoreWriteStatement(row)));

        row = rows.get(1);
        table.addRow(Arrays.asList(row.get(0), currentProject.getDataStorePrintValueStatement(row)));

        GaugeProjectBuilder.createScenarioAndSteps(scenario, spec, table, true, true);
    }

    @Step("Create a scenario <readScenario> in specification <newSpec> with step to read from datastore <table>")
    public void readFromDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        Table table = new Table(Arrays.asList("Step text", "implementation"));

        List<List<String>> rows = datastoreValues.getRows();

        // write to datastore
        List<String> row = rows.get(0);
        table.addRow(Arrays.asList(row.get(0), currentProject.getDataStorePrintValueStatement(row)));

        GaugeProjectBuilder.createScenarioAndSteps(scenario, spec, table, true, true);
    }

    @Step("Create a scenario <writeScenario> in specification <newSpec> with step to write to datastore <table>")
    public void writeToDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        Table table = new Table(Arrays.asList("Step text", "implementation"));

        List<List<String>> rows = datastoreValues.getRows();

        List<String> row = rows.get(0);
        table.addRow(Arrays.asList(row.get(0), currentProject.getDataStoreWriteStatement(row)));

        GaugeProjectBuilder.createScenarioAndSteps(scenario, spec, table, true, true);
    }
}
