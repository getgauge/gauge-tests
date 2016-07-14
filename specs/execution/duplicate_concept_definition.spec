Duplicate concept definition
=============================

Duplicate concept definition
-----------------------------

* In an empty directory initialize a project named "invalid_duplicate_concept_definition" with the current language
* Create concept "concept" with following steps 

     |concept steps            |
     |-------------------------|
     |simple step with "static"|

* Create step implementations 

     |step text                |implementation|
     |-------------------------|--------------|
     |simple step with "static"|print params  |

* Create concept "concept" with following steps 

     |concept steps            |
     |-------------------------|
     |simple step with "static"|

* Create a specification "spec1" with the following contexts 

     |step text    |implementation        |
     |-------------|----------------------|
     |First context|"inside first context"|

* Create "scenario_invalid_duplicate_concept_definition" in "spec1" with the following steps 

     |step text|
     |---------|
     |concept  |

* Execute the current project and ensure failure
* Console should not contain following lines 

     |output                                         |
     |-----------------------------------------------|
     |[ParseError] Duplicate concept definition found|
