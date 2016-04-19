Concept Refactoring
===================

tags: refactoring

* In an empty directory initialize a project named "concept_refactoring" with the current language

* Create concept "concept with <param0> and <param1>" with following steps 
     |concept steps                            |
     |-----------------------------------------|
     |nested concept with <param0> and <param1>|
     |simple step with "static"                |

* Create concept "concept" with following steps 
     |concept steps            |
     |-------------------------|
     |simple step with "static"|


Rename concept
--------------

* Refactor step "concept" to "refactored concept"
* The step "refactored concept" should be used in project
* The step "concept" should no longer be used

Rephrase concept with removal of params
---------------------------------------

* Refactor step "concept with <param0> and <param1>" to "concept with <param0>"
* The step "concept with <param0>" should be used in project
* The step "concept with <param0> and <param1>" should no longer be used

Rephrase concept with addition of params
----------------------------------------

* Refactor step "concept with <param0> and <param1>" to "concept with <param0> and <param1> and <param2>"
* The step "concept with <param0> and <param1> and <param2>" should be used in project
* The step "concept with <param0> and <param1>" should no longer be used

Rephrase concept
----------------

* Refactor step "concept with <param0> and <param1>" to "concept with <param0> and <param2>"
* The step "concept with <param0> and <param2>" should be used in project
* The step "concept with <param0> and <param1>" should no longer be used
