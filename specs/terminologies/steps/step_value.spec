Step Value
==========

tags: api, java, csharp, ruby, python, js

* Initialize a project named "step_value" without example spec

* Create a csv file "abc"

* Create a txt file "def"

* Create a scenario "first scenario" in specification "Specification 1" with the following steps with implementation 

   |step text              |implementation              |
   |-----------------------|----------------------------|
   |step with no parameters|"inside first scenario step"|

* Create a scenario "second scenario" in specification "Specification 1" with the following steps unimplemented 

   |step text                     |
   |------------------------------|
   |step with static parameter "a"|

* Create a scenario "first scenario" in specification "Specification 2" with the following steps with implementation 

   |step text                      |implementation              |
   |-------------------------------|----------------------------|
   |step with dynamic parameter <b>|"inside first scenario step"|

* Create a scenario "second scenario" in specification "Specification 2" with the following steps with implementation 

   |step text                            |implementation               |
   |-------------------------------------|-----------------------------|
   |step with multiple parameters "c" <d>|"inside second scenario step"|

* Create step implementations 

   |step text                              |implementation|
   |---------------------------------------|--------------|
   |step with csv parameter <table:abc.csv>|print params  |

* Create step implementations 

   |step text                             |implementation|
   |--------------------------------------|--------------|
   |step with txt parameter <file:def.txt>|print params  |

Fetch step Values
-----------------

* Start Gauge daemon
* fetch step values for the following 

   |step text                                    |
   |---------------------------------------------|
   |step with no parameters                      |
   |step with static parameter "a"               |
   |step with dynamic parameter <b>              |
   |step with multiple parameters "c" <d>        |
   |step with csv parameter <table:specs/abc.csv>|
   |step with txt parameter <table:specs/def.txt>|

* Verify all the step values are present 

   |step annotation text                         |step text                          |parameters         |
   |---------------------------------------------|-----------------------------------|-------------------|
   |step with no parameters                      |step with no parameters            |                   |
   |step with static parameter "a"               |step with static parameter {}      |a                  |
   |step with dynamic parameter <b>              |step with dynamic parameter {}     |b                  |
   |step with multiple parameters "c" <d>        |step with multiple parameters {} {}|c,d                |
   |step with csv parameter <table:specs/abc.csv>|step with csv parameter {}         |table:specs/abc.csv|
   |step with txt parameter <table:specs/abc.csv>|step with txt parameter {}         |table:specs/def.txt|
