Concept Validation Errors occuring during execution
===========================================

tags: validation, java, csharp, ruby, python

* In an empty directory initialize a project named "concept_exec_with_validation_err" with the current language

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

     |console output                     |
     |-----------------------------------|
     |Circular reference found in concept|

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

     |console output                     |
     |-----------------------------------|
     |Circular reference found in concept|


Concept should not contain scenario heading
-------------------------------------------

* Create concept "concept with scenario heading" with following steps 

     |concept steps      |Impl   |Type   |
     |-------------------|-------|-------|
     |Step1              |step   |step   |
     |Step2              |step   |step   |
     |## Scenario Heading|comment|comment|
     |Scenario step 1    |step   |step   |

* Execute the current project and ensure failure
* Console should contain "Scenario Heading is not allowed in concept file"
