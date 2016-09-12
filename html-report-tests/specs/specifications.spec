A basic spec has only scenarios
===============================

tags: spec

* In an empty directory initialize a project named "spec_with_passing_scenarios" without example spec

basic spec with one scenario, passing
-------------------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps

     |step text               |implementation                                          |
     |------------------------|--------------------------------------------------------|
     |First step              |"inside first step"                                     |
     |Second step             |"inside second step"                                    |
     |Third step              |"inside third step"                                     |
     |Step with "two" "params"|"inside step with parameters : " + param0 + " " + param1|

* Execute the current project and ensure success

* Verify Console Details <table:resources/specifications/one_scenario_passing/console_statistics.csv><table:resources/specifications/one_scenario_passing/console_output.csv>

* Verify HTML Details <table:resources/specifications/one_scenario_passing/html_statistics.csv><table:resources/specifications/one_scenario_passing/html_scenario.csv>

