Spec with scenarios
===================

tags: spec, java, csharp, dotnet, ruby, python, js

* Initialize a project named "spec_with_passing_scenarios" without example spec

Basic spec with one scenario, passing
-------------------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text               |implementation                                          |
   |------------------------|--------------------------------------------------------|
   |First step              |"inside first step"                                     |
   |Second step             |"inside second step"                                    |
   |Third step              |"inside third step"                                     |
   |Step with "two" "params"|"inside step with parameters : " + param0 + " " + param1|

* Execute the current project and ensure success

* Verify Console Details <table:resources/terminologies/specifications/basic_steps/one_scenario_passing/console_statistics.csv><table:resources/terminologies/specifications/basic_steps/one_scenario_passing/console_output.csv>

* Verify HTML Details <table:resources/terminologies/specifications/basic_steps/one_scenario_passing/html_statistics.csv><table:resources/terminologies/specifications/basic_steps/one_scenario_passing/html_scenario.csv>

Basic spec with multiple scenarios, passing
-------------------------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text          |implementation     |
   |-------------------|-------------------|
   |First Scenario step|"inside first step"|

* Create a scenario "second scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text           |implementation      |
   |--------------------|--------------------|
   |Second Scenario step|"inside second step"|

* Create a scenario "third scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text          |implementation     |
   |-------------------|-------------------|
   |Third Scenario step|"inside third step"|

* Execute the current project and ensure success

* Verify Console Details <table:resources/terminologies/specifications/basic_steps/multiple_scenarios_passing/console_statistics.csv><table:resources/terminologies/specifications/basic_steps/scenario_failing_skipped/console_output.csv>

* Verify HTML Details <table:resources/terminologies/specifications/basic_steps/multiple_scenarios_passing/html_statistics.csv><table:resources/terminologies/specifications/basic_steps/scenario_failing_skipped/html_scenario.csv>

Spec fails if it has even one scenario failing irrespective of the passing and skipped scenarios in it
------------------------------------------------------------------------------------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|
   |Third step |throw exception     |

* Create a scenario "second scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text|
   |---------|
   |some step|

* Create a scenario "third scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text          |implementation     |
   |-------------------|-------------------|
   |Third Scenario step|"inside third step"|

* Execute the current project and ensure failure

* Verify Console Details <table:resources/terminologies/specifications/basic_steps/scenario_failing_skipped/console_statistics.csv><table:resources/terminologies/specifications/basic_steps/multiple_scenarios_passing/console_output.csv>

* Verify HTML Details <table:resources/terminologies/specifications/basic_steps/scenario_failing_skipped/html_statistics.csv><table:resources/terminologies/specifications/basic_steps/multiple_scenarios_passing/html_scenario.csv>

Spec should be skipped if it has all scenarios with unimplemented steps
-----------------------------------------------------------------------

* Create a scenario "first scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text |
   |----------|
   |some step1|

* Create a scenario "second scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text |
   |----------|
   |some step2|

* Execute the current project and ensure success

* Console should contain following lines in order 

   |console output                               |
   |---------------------------------------------|
   |Step implementation not found => 'some step1'|
   |Step implementation not found => 'some step2'|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |0       |0     |0     |1      |
   |Scenarios      |0       |0     |0     |2      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |0        |1           |
