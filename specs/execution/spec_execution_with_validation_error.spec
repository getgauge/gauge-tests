Spec Execution With Validation Error
====================================

* In an empty directory initialize a project named "spec_exec_with_validation_err" with the current language

Spec execution with unimplemented step in scenarios
---------------------------------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps 
     |step text                |
     |-------------------------|
     |First unimplemented step |
     |Second unimplemented step|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps with implementation 
     |step text  |implementation      |
     |-----------|--------------------|
     |First step |"inside first step" |
     |Second step|"inside second step"|

* Execute the spec "Basic spec execution" and ensure failure

* Console should contain following lines in order 
     |console output                                          |
     |--------------------------------------------------------|
     |Step implementation not found. First unimplemented step |
     |Step implementation not found. Second unimplemented step|
     |inside first step                                       |
     |inside second step                                      |

* Console should contain "Specifications:\t1 executed\t1 passed\t0 failed\t0 skipped"

* Console should contain "Scenarios:\t1 executed\t1 passed\t0 failed\t1 skipped"

Spec execution with unimplemented step in context step
------------------------------------------------------
* Create a specification "Basic spec execution" with the following unimplemented contexts 
     |step text    |
     |-------------|
     |First context|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps with implementation 
     |step text  |implementation      |
     |-----------|--------------------|
     |First step |"inside first step" |
     |Second step|"inside second step"|

* Execute the spec "Basic spec execution" and ensure failure

* Console should contain following lines in order 
     |console output                              |
     |--------------------------------------------|
     |Step implementation not found. First context|

* Console should contain "Specifications:\t0 executed\t0 passed\t0 failed\t1 skipped"

* Console should contain "Scenarios:\t0 executed\t0 passed\t0 failed\t1 skipped"


Spec execution with unimplemented step in context step and scenario
-------------------------------------------------------------------
* Create a specification "Basic spec execution" with the following unimplemented contexts 
     |step text    |
     |-------------|
     |First context|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps with implementation 
     |step text  |implementation      |
     |-----------|--------------------|
     |First step |"inside first step" |
     |Second step|"inside second step"|

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps 
     |step text                |
     |-------------------------|
     |Second unimplemented step|

* Execute the spec "Basic spec execution" and ensure failure

* Console should contain following lines in order 
     |console output                                          |
     |--------------------------------------------------------|
     |Step implementation not found. First context            |
     |Step implementation not found. Second unimplemented step|

* Console should contain "Specifications:\t0 executed\t0 passed\t0 failed\t1 skipped"

* Console should contain "Scenarios:\t0 executed\t0 passed\t0 failed\t2 skipped"
