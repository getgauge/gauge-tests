Invalid Duplicate concept definition
====================================

tags: unimplemented

Invalid duplicate concept definition
------------------------------------

* In an empty directory initialize a project named "invalid_duplicate_concept_definition" with the current language
* Create concept "concept" with following steps 

     |concept steps            |
     |-------------------------|
     |simple step with "static"|

* Create step implementations 

     |step text                |implementation|
     |-------------------------|--------------|
     |simple step with "static"|print params  |

* Create a specification "spec1" with the following contexts 

     |step text    |implementation        |
     |-------------|----------------------|
     |First context|"inside first context"|

* Create a specification "spec2" with the following contexts 

     |step text    |implementation        |
     |-------------|----------------------|
     |First context|"inside first context"|

* Create "scenario_invalid_duplicate_concept_definition" in "spec1" with the following steps 

     |step text|
     |---------|
     |concept  |

* Create "scenario_invalid_duplicate_concept_definition" in "spec2" with the following steps 

     |step text|
     |---------|
     |concept  |

* Execute the specs and ensure success 

     |spec names|
     |----------|
     |spec1     |
     |spec2     |

* Console should not contain following lines 

     |output      |
     |------------|
     |[ParseError]|
