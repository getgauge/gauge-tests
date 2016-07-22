Spec passes when all scenarios in it pass
=========================

* In an empty directory initialize a project named "spec_with_passing_scenarios" with the current language

Passing execution of a spec with single scenario
--------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps

     |step text               |implementation                                          |
     |------------------------|--------------------------------------------------------|
     |First step              |"inside first step"                                     |
     |Second step             |"inside second step"                                    |
     |Third step              |"inside third step"                                     |
     |Step with "two" "params"|"inside step with parameters : " + param0 + " " + param1|

* Execute the current project and ensure success
* Console should contain "inside first step"
* Console should contain "inside second step"
* Console should contain "inside third step"
* Console should contain "inside step with parameters : two params"

Passing execution of a spec with multiple scenarios
------------------------------------

* Create a specification "specWithMultipleScenarios" with the following contexts

     |step text    |implementation        |
     |-------------|----------------------|
     |First context|"inside first context"|

* Create a scenario "first scenario" in specification "specWithMultipleScenarios" with the following steps with implementation

     |step text          |implementation              |
     |-------------------|----------------------------|
     |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "specWithMultipleScenarios" with the following steps with implementation

     |step text           |implementation               |
     |--------------------|-----------------------------|
     |Second Scenario step|"inside second scenario step"|

* Execute the spec "specWithMultipleScenarios" with scenario at "6" and ensure success

* Console should contain following lines in order

     |console output            |
     |--------------------------|
     |inside first context      |
     |inside first scenario step|

* Console should not contain following lines

     |console output             |
     |---------------------------|
     |inside second scenario step|
