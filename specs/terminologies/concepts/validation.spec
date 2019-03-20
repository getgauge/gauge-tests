Concept Validation
==================

tags: validation, java, csharp, dotnet, ruby, python, js

* Initialize a project named "concept_exec_with_validation_err" without example spec

Duplicate concept definition
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

Concept calling itself
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

Cyclic concept dependency
-------------------------

* Create concept "Concept Heading" with following steps 

   |step text        |
   |-----------------|
   |Concept Heading 1|

* Create concept "Concept Heading 1" with following steps 

   |step text      |
   |---------------|
   |Concept Heading|

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text      |
   |---------------|
   |Concept Heading|

* Execute the spec "Basic spec execution" and ensure failure
* Console should contain following lines in order 

   |console output                     |
   |-----------------------------------|
   |Circular reference found in concept|


Scenario in concepts
--------------------

* Create concept "concept with scenarios" with following steps 

   |concept steps      |Impl   |Type   |
   |-------------------|-------|-------|
   |Step1              |step   |step   |
   |Step2              |step   |step   |
   |## Scenario Heading|comment|comment|
   |Scenario step 1    |step   |step   |

* Execute the current project and ensure failure
* Console should contain "Scenario Heading is not allowed in concept file"
