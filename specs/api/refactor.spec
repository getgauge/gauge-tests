Refactor Specification
======================

tags: refactoring

* In an empty directory initialize a project with the current language

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps 
     |step text               |
     |------------------------|
     |First step              |
     |Second step             |
     |Repeated First step     |
     |Step with "two" "params"|

* Create a scenario "Sample scenario2" in specification "Basic spec execution2" with the following steps 
     |step text                  |implementation      |
     |---------------------------|--------------------|
     |First step                 |"inside first step" |
     |Step with "two" "params"   |"inside first step" |
     |a step with "a" "b" and "c"|"inside first step" |
     |Second step                |"inside second step"|
* Start Gauge daemon

Rename step
-----------

* Refactor step "First step" to "New step" via api
* The step "First step" should no longer be used
* The step "New step" should be used in project

Rephrase simple step
--------------------

* Refactor step "Step with <a> <b>" to "Step having <b> and <a>" via api
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" and \"two\"" should be used in project

Rephrase step having new parameters
-----------------------------------

* Refactor step "Step with <a> <b>" to "Step having <b> <c> and <a>" via api
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" \"c\" and \"two\"" should be used in project

Rephrase step removing parameters
---------------------------------

* Refactor step "Step with <a> <b>" to "Step having <b> and <c>" via api
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"params\" and \"c\"" should be used in project

Rephrase step with all new parameters
-------------------------------------

* Refactor step "Step with <a> <b>" to "Step having <d> and <c>" via api
* The step "Step with \"two\" \"params\"" should no longer be used
* The step "Step having \"d\" and \"c\"" should be used in project

Refactor a non-Existing step
----------------------------

* Refactor step "hello" to "world" via api
* verify refactoring didn't change files
