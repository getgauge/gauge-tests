Steps Collection
================


* In an empty directory initialize a project with the current language
* Create a specification "Specification 1" with the following contexts 
     |step text|implementation         |
     |---------|-----------------------|
     |context 1|"inside first context" |
     |context 2|"inside second context"|

* Create a specification "Specification 2" with the following contexts 
     |step text|implementation         |
     |---------|-----------------------|
     |context 3|"inside first context" |
     |context 4|"inside second context"|

* Create a scenario "first scenario" in specification "Specification 1" with the following steps with implementation 
     |step text      |implementation               |
     |---------------|-----------------------------|
     |Scenario step 1|"inside first scenario step" |
     |Scenario step 2|"inside second scenario step"|
     |Scenario step 3|"inside second scenario step"|
     |Scenario step 4|"inside second scenario step"|

* Create a scenario "second scenario" in specification "Specification 1" with the following steps 
     |step text      |
     |---------------|
     |Scenario step 5|
     |Scenario step 6|
     |Scenario step 7|

* Create a scenario "first scenario" in specification "Specification 2" with the following steps with implementation 
     |step text       |implementation              |
     |----------------|----------------------------|
     |Scenario step 8 |"inside first scenario step"|
     |Scenario step 9 |"inside first scenario step"|
     |Scenario step 10|"inside first scenario step"|

* Create a scenario "second scenario" in specification "Specification 2" with the following steps with implementation 
     |step text       |implementation               |
     |----------------|-----------------------------|
     |Scenario step 11|"inside second scenario step"|

* Create step implementations 
     |step            |implementation|
     |----------------|--------------|
     |Scenario step 12|print params  |
     |Scenario step 13|print params  |

Fetch all steps present in project
----------------------------------

* Start Gauge daemon

* Steps from api should have the following 
     |step text       |
     |----------------|
     |context 1       |
     |context 2       |
     |context 3       |
     |context 4       |
     |Scenario step 1 |
     |Scenario step 2 |
     |Scenario step 3 |
     |Scenario step 4 |
     |Scenario step 5 |
     |Scenario step 6 |
     |Scenario step 7 |
     |Scenario step 8 |
     |Scenario step 9 |
     |Scenario step 10|
     |Scenario step 11|
     |Scenario step 12|
     |Scenario step 13|

Fetch all steps including newly created steps after starting the Api
--------------------------------------------------------------------

* Start Gauge daemon

* Create a scenario "first scenario" in specification "Specification 4" with the following steps with implementation 
     |step text       |implementation               |
     |----------------|-----------------------------|
     |Scenario step 14|"inside first scenario step" |
     |Scenario step 15|"inside second scenario step"|

* Create a scenario "first scenario" in specification "Specification 5" with the following steps 
     |step text       |
     |----------------|
     |Scenario step 16|
     |Scenario step 17|


* Steps from api should have the following 
     |step text       |
     |----------------|
     |context 1       |
     |context 2       |
     |context 3       |
     |context 4       |
     |Scenario step 1 |
     |Scenario step 2 |
     |Scenario step 3 |
     |Scenario step 4 |
     |Scenario step 5 |
     |Scenario step 6 |
     |Scenario step 7 |
     |Scenario step 8 |
     |Scenario step 9 |
     |Scenario step 10|
     |Scenario step 11|
     |Scenario step 12|
     |Scenario step 13|
     |Scenario step 14|
     |Scenario step 15|
     |Scenario step 16|
     |Scenario step 17|
