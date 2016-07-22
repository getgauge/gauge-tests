Scenario execution
=====================

* In an empty directory initialize a project named "scenarios_execution" without example spec

Execute one scenario by specifying line number in a spec with multiple scenarios
---------------------------------------------------

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

* Statics generated should have
     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |1       |1     |0     |0      |
     