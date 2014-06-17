Spec execution
==============

* In an empty directory initialize a project with the current language

Basic spec execution
--------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 
     |step text  |implementation    |
     |-----------|------------------|
     |First step |inside first step |
     |Second step|inside second step|
     |Third step |inside third step |

* Execute the current project and ensure success
* Console should contain "inside first step"
* Console should contain "inside second step"
* Console should contain "inside third step"

Concept execution
-----------------

* Create concept "Sample concept" with following steps 
     |step text       |implementation            |
     |----------------|--------------------------|
     |Concept step one|inside first concept step |
     |Concept step two|inside second concept step|

* Create "Scenario for concept execution" in "Concept execution" with the following steps 
     |step text     |implementation    |
     |--------------|------------------|
     |Sample concept|                  |
     |Fourth step   |inside fourth step|

* Execute the spec "Concept execution" and ensure success
* Console should contain following lines in order 
     |console output            |
     |--------------------------|
     |inside first concept step |
     |inside second concept step|
     |inside fourth step        |





