Refactor Specification
======================
* In an empty directory initialize a project with the current language

* Create "Sample scenario" in "Basic spec execution" with the following steps 
     |step text               |implementation                                          |
     |------------------------|--------------------------------------------------------|
     |First step              |"inside first step"                                     |
     |Second step             |"inside second step"                                    |
     |Repeated First step     |"inside second step"                                    |
     |Step with "two" "params"|

* Create "Sample scenario2" in "Basic spec execution2" with the following steps 
     |step text                  |implementation                                                  |
     |---------------------------|----------------------------------------------------------------|
     |First step                 |"inside first step"                                             |
     |Step with "two" "params"   |
     |a step with "a" "b" and "c"|
     |Second step                |"inside second step"                                            |


Rename step
-----------
* Refactor step "First step" to "New step"
* The step "First step" should no longer be used
* The step "New step" should be used in project

Rephrase simple step
--------------------
* Refactor step "Step with <a> <b>" to "Step having <b> and <a>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" and \"two\"" should be used in project

Rephrase step having new parameters
-----------------------------------
* Refactor step "Step with <a> <b>" to "Step having <b> <c> and <a>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" \"c\" and \"two\"" should be used in project

Rephrase step removing parameters
---------------------------------
* Refactor step "Step with <a> <b>" to "Step having <b> and <c>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" and \"c\"" should be used in project

Rephrase step with all new parameters
-------------------------------------
* Refactor step "Step with <a> <b>" to "Step having <d> and <c>"
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"d\" and \"c\"" should be used in project

Refactor a non-Existing step
----------------------------
* Refactor step "hello" to "world"
* The error message <file:resources/refactor_console.txt> should be displayed on console
