Teardown Execution
==================
* In an empty directory initialize a project named "teardown_exec" with the current language

Passing teardown execution
--------------------------

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

* Statics generated should have

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |2       |2     |0     |0      |

* verify statistics in html with

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |1        |0        |0           |

Unimplemented teardown execution
--------------------------------

* Create a scenario "first scenario" in specification "basic teardown execution" with the following steps with implementation 

     |step text          |implementation              |
     |-------------------|----------------------------|
     |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "basic teardown execution" with the following steps with implementation 

     |step text           |implementation               |
     |--------------------|-----------------------------|
     |Second Scenario step|"inside second scenario step"|

* Add the following unimplemented teardown steps in specification "basic teardown execution" 

     |step text      |
     |---------------|
     |First teardown |
     |Second teardown|

* Execute the spec "basic teardown execution" and ensure failure
* Console should contain following lines in order 

     |console output                                    |
     |--------------------------------------------------|
     |Step implementation not found => 'First teardown' |
     |Step implementation not found => 'Second teardown'|


* Statics generated should have

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |0       |0     |0     |1      |
     |Scenarios      |0       |0     |0     |2      |

* verify statistics in html with

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |0        |0        |1           |

Failed teardown execution
-------------------------

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
     |First teardown |throw exception         |
     |Second teardown|"inside second teardown"|

* Execute the spec "basic teardown execution" and ensure failure
* Console should not contain following lines 

     |console output        |
     |----------------------|
     |inside second teardown|

* Console should contain following lines in order 

     |console output             |
     |---------------------------|
     |inside first scenario step |
     |Failed Step: First teardown|
     |inside second scenario step|
     |Failed Step: First teardown|


* Statics generated should have

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |2       |0     |2     |0      |

* verify statistics in html with

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |0        |1        |0           |
