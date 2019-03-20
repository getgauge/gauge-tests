Duplicate concept definition
============================

tags: java, csharp, dotnet, ruby, python

Duplicate concept definition
----------------------------

* Initialize a project named "invalid_duplicate_concept_definition" without example spec
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

* Create a scenario "scenario_invalid_duplicate_concept_definition" in specification "spec1" with the following steps with implementation 

   |step text|
   |---------|
   |concept  |

* Execute the current project and ensure failure
* Console should contain "2" duplicate concept definition list "concept"
