Step Value
==========
tags: api
* In an empty directory initialize a project named "step_value" with the current language
* Create a scenario "first scenario" in specification "Specification 1" with the following steps with implementation 

     |step text          |implementation              |
     |-------------------|----------------------------|
     |Scenario step 1 <a>|"inside first scenario step"|
* Create a scenario "second scenario" in specification "Specification 1" with the following steps 

     |step text          |
     |-------------------|
     |Scenario step 2 <b>|
* Create a scenario "first scenario" in specification "Specification 2" with the following steps with implementation 

     |step text                  |implementation              |
     |---------------------------|----------------------------|
     |Scenario step 3 <c> and <d>|"inside first scenario step"|
* Create a scenario "second scenario" in specification "Specification 2" with the following steps with implementation 

     |step text      |implementation               |
     |---------------|-----------------------------|
     |Scenario step 4|"inside second scenario step"|
* Create step implementations 

     |step               |implementation|
     |-------------------|--------------|
     |Scenario step 5 "e"|print params  |
Fetch step Values
-----------------
* Start Gauge daemon
* fetch step values for the following 

     |step text                  |
     |---------------------------|
     |Scenario step 1 <a>        |
     |Scenario step 2 <b>        |
     |Scenario step 3 <c> and <d>|
     |Scenario step 4            |
     |Scenario step 5 "e"        |
     |Scenario step 6 "e"        |
* Verify all the step values are present 

     |step text                |step annotation text       |parameters|
     |-------------------------|---------------------------|----------|
     |Scenario step 1 {}       |Scenario step 1 <a>        |a         |
     |Scenario step 2 {}       |Scenario step 2 <b>        |b         |
     |Scenario step 3 {} and {}|Scenario step 3 <c> and <d>|c,d       |
     |Scenario step 4          |Scenario step 4            |          |
     |Scenario step 5 {}       |Scenario step 5 <e>        |e         |
     |Scenario step 6 {}       |Scenario step 6 <e>        |e         |
