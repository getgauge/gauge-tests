Step Refactoring through command
================================

tags: refactoring

* In an empty directory initialize a project named "spec_refactoring_thru_cmd" with the current language

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps 
     |step text               |
     |------------------------|
     |First step              |
     |Second step             |
     |Repeated First step     |
     |Step with "two" "params"|

* Create a scenario "Sample scenario2" in specification "Basic spec execution2" with the following steps with implementation 
     |step text                  |implementation      |
     |---------------------------|--------------------|
     |First step                 |"inside first step" |
     |Step with "two" "params"   |"inside first step" |
     |a step with "a" "b" and "c"|"inside first step" |
     |Second step                |"inside second step"|
     |step1                      |"inside step1"      |


Rename step
-----------

* Refactor step "First step" to "New step"
* The step "First step" should no longer be used
* The step "New step" should be used in project
* Execute the spec "Basic spec execution2" and ensure success

Rephrase simple step
--------------------

* Refactor step "Step with <a> <b>" to "Step having <b> and <a>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" and \"two\"" should be used in project
* Execute the spec "Basic spec execution2" and ensure success

Rephrase step having new parameters
-----------------------------------

* Refactor step "Step with <a> <b>" to "Step having <b> <c> and <a>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" \"c\" and \"two\"" should be used in project
* Execute the spec "Basic spec execution2" and ensure success

Rephrase step removing parameters
---------------------------------

* Refactor step "Step with <a> <b>" to "Step having <b> and <c>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" and \"c\"" should be used in project
* Execute the spec "Basic spec execution2" and ensure success

Rephrase step with all new parameters
-------------------------------------

* Refactor step "Step with <a> <b>" to "Step having <d> and <c>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"d\" and \"c\"" should be used in project
* Execute the spec "Basic spec execution2" and ensure success

Refactor a non-Existing step
----------------------------

* Refactor step "hello" to "world"
* The error message <file:resources/refactor_console.txt> should be displayed on console

Rename an unimplemented step
----------------------------

* Refactor step "Repeated First step" to "again Repeated First step"
* The step "Repeated First step" should no longer be used
* The step "again Repeated First step" should be used in project

Rename step to step with \
--------------------------

* Refactor step "step1" to "step with \\ slash"
* The step "step1" should no longer be used
* The step "step with \\ slash" should be used in project
* Execute the spec "Basic spec execution2" and ensure success