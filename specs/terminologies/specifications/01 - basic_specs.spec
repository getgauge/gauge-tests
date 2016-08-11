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

* Console should contain following lines in order 

     |console output                          |
     |----------------------------------------|
     |inside first step                       |
     |inside second step                      |
     |inside third step                       |
     |inside step with parameters : two params|

* Statics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |1       |1     |0     |0      |

basic spec with multiple scenarios, passing
-------------------------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 

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

* Console should contain following lines in order 

     |console output    |
     |------------------|
     |inside first step |
     |inside second step|
     |inside third step |

* Statics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |3       |3     |0     |0      |

Spec fails if it has even one scenario failing irrespective of the passing and skipped scenarios in it
------------------------------------------------------------------------------------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 

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

* Console should contain following lines in order 

     |console output         |
     |-----------------------|
     |inside first step      |
     |inside second step     |
     |Failed Step: Third step|

* Statics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |2       |1     |1     |1      |

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

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output                               |
     |---------------------------------------------|
     |Step implementation not found => 'some step1'|
     |Step implementation not found => 'some step2'|

* Statics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |0       |0     |0     |1      |
     |Scenarios      |0       |0     |0     |2      |

