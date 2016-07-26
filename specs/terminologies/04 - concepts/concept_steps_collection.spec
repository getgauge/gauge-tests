Concept Steps Collection
========================

tags: api

* In an empty directory initialize a project named "concept_steps_collection" with the current language
* Create concept "concept1 <b>" with following steps 

     |step text |implementation      |
     |----------|--------------------|
     |step 1 "a"|"inside first step" |
     |step 2    |"inside second step"|

* Create concept "concept2" with following steps 

     |step text|
     |---------|
     |step 3   |
     |step 4   |

Fetch all concept steps present in project
------------------------------------------

* Start Gauge daemon
* Steps from api should have the following 

     |step text |
     |----------|
     |step 1 "a"|
     |step 2    |
     |step 3    |
     |step 4    |

* Concepts from api should have the following 

     |step text   |
     |------------|
     |concept1 <a>|
     |concept2    |
