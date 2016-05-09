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

     |console output                                              |
     |------------------------------------------------------------|
     |Step implementation not found => 'First unimplemented step' |
     |Step implementation not found => 'Second unimplemented step'|
     |inside first step                                           |
     |inside second step                                          |
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

     |console output                                  |
     |------------------------------------------------|
     |Step implementation not found => 'First context'|
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

     |console output                                              |
     |------------------------------------------------------------|
     |Step implementation not found => 'First context'            |
     |Step implementation not found => 'Second unimplemented step'|
* Console should contain "Specifications:\t0 executed\t0 passed\t0 failed\t1 skipped"
* Console should contain "Scenarios:\t0 executed\t0 passed\t0 failed\t2 skipped"
Spec with no heading
--------------------
* Create a scenario "Sample scenario" in specification "" with the following steps 

     |step text                |
     |-------------------------|
     |First unimplemented step |
     |Second unimplemented step|
* Execute the spec "" and ensure failure
* Console should contain "Spec heading should have at least one character"
Scenario with no heading
------------------------
* Create a scenario "" in specification "Scenario with no heading" with the following steps 

     |step text                |
     |-------------------------|
     |First unimplemented step |
     |Second unimplemented step|
* Execute the spec "Scenario with no heading" and ensure failure
* Console should contain "Scenario heading should have at least one character"
Skip spec if all scenarios are skipped
--------------------------------------
* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps 

     |step text                |
     |-------------------------|
     |First unimplemented step |
     |Second unimplemented step|
* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps 

     |step text                |
     |-------------------------|
     |Third unimplemented step |
     |Fourth unimplemented step|
* Execute the spec "Basic spec execution" and ensure failure
* Console should contain following lines in order 

     |console output                                              |
     |------------------------------------------------------------|
     |Step implementation not found => 'First unimplemented step' |
     |Step implementation not found => 'Second unimplemented step'|
     |Step implementation not found => 'Third unimplemented step' |
     |Step implementation not found => 'Fourth unimplemented step'|
* Console should contain "Specifications:\t0 executed\t0 passed\t0 failed\t1 skipped"
* Console should contain "Scenarios:\t0 executed\t0 passed\t0 failed\t2 skipped"
Duplicate Concept Defination
----------------------------
* Create concept "Concept Heading" with following steps 

     |step text  |implementation      |
     |-----------|--------------------|
     |First step |"inside first step" |
     |Second step|"inside second step"|
* Create concept "Concept Heading" with following steps 

     |step text  |implementation      |
     |-----------|--------------------|
     |First step]|"inside first step" |
     |Second step|"inside second step"|
* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

     |step text |implementation     |
     |----------|-------------------|
     |Third step|"inside third step"|
* Execute the spec "Basic spec execution" and ensure failure
* Console should contain following lines in order 

     |console output                                         |
     |-------------------------------------------------------|
     |Duplicate concept definition found => 'Concept Heading'|
Concept Calling itself
----------------------
* Create concept "Concept Heading" with following steps 

     |step text      |
     |---------------|
     |Concept Heading|
* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

     |step text  |implementation      |
     |-----------|--------------------|
     |Second step|"inside second step"|
* Execute the spec "Basic spec execution" and ensure failure
* Console should contain following lines in order 

     |console output                                                              |
     |----------------------------------------------------------------------------|
     |Cyclic dependancy found. Step is calling concept again. => 'Concept Heading'|
Cyclic Concept Dependency
-------------------------
* Create concept "Concept Heading" with following steps 

     |step text        |
     |-----------------|
     |Concept Heading 1|
* Create concept "Concept Heading 1" with following steps 

     |step text      |
     |---------------|
     |Concept Heading|
* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps 

     |step text      |
     |---------------|
     |Concept Heading|
* Execute the spec "Basic spec execution" and ensure failure
* Console should contain following lines in order 

     |console output                                                              |
     |----------------------------------------------------------------------------|
     |Cyclic dependancy found. Step is calling concept again. => 'Concept Heading'|
