Concept Steps Collection
========================


* In an empty directory initialize a project with the current language
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


Fetch all concept steps including newly created steps after starting the Api
----------------------------------------------------------------------------

* Start Gauge daemon

* Create concept "concept3 <c> and <d>" with following steps 
     |step text|implementation      |
     |---------|--------------------|
     |step 5   |"inside first step" |
     |step 6   |"inside second step"|

* Create concept "concept4" with following steps 
     |step text|
     |---------|
     |step 7   |
     |step 8   |


* Steps from api should have the following 
     |step text |
     |----------|
     |step 1 "a"|
     |step 2    |
     |step 3    |
     |step 4    |
     |step 5    |
     |step 6    |
     |step 7    |
     |step 8    |

* Concepts from api should have the following 
     |step text           |
     |--------------------|
     |concept1 <b>        |
     |concept2            |
     |concept3 <c> and <d>|
     |concept4            |
