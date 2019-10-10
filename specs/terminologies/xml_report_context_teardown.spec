# XML Report for Context and Teardown

tags: xml-report

* Initialize a project named "context_teardown_exec" without example spec

* Add xml report plugin in manifest.json

## Context steps should appear for all scenarios

* Create a specification "basic context execution" with the following contexts 

   |step text     |implementation         |
   |--------------|-----------------------|
   |First context |"inside first context" |
   |Second context|"inside second context"|

* Create a scenario "first scenario" in specification "basic context execution" with the following steps with implementation 

   |step text          |implementation              |
   |-------------------|----------------------------|
   |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "basic context execution" with the following steps with implementation 

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |Second Scenario step|"inside second scenario step"|

* Execute the spec "basic context execution" and ensure success
* Console should contain following lines in order 

   |console output             |
   |---------------------------|
   |inside first context       |
   |inside second context      |
   |inside first scenario step |
   |inside first context       |
   |inside second context      |
   |inside second scenario step|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain "Successfully generated xml-report to =>"

* verify statistics in xml with 

   |totalCount|scenarioCount|failCount|skippedCount|
   |----------|-------------|---------|------------|
   |1         |2            |0        |0           |


## Passing teardown execution

* Create a scenario "first scenario" in specification "basic teardown execution" with the following steps with implementation

   |step text          |implementation              |
   |-------------------|----------------------------|
   |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "basic teardown execution" with the following steps with implementation

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |Second Scenario step|"inside second scenario step"|

* Add the following teardown steps in specification "basic teardown execution"

   |step text      |implementation          |
   |---------------|------------------------|
   |First teardown |"inside first teardown" |
   |Second teardown|"inside second teardown"|

* Execute the spec "basic teardown execution" and ensure success
* Console should contain following lines in order

   |console output             |
   |---------------------------|
   |inside first scenario step |
   |inside first teardown      |
   |inside second teardown     |
   |inside second scenario step|
   |inside first teardown      |
   |inside second teardown     |

* Statistics generated should have

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain "Successfully generated xml-report to =>"

* verify statistics in xml with

   |totalCount|scenarioCount|failCount|skippedCount|
   |----------|-------------|---------|------------|
   |1         |2            |0        |0           |