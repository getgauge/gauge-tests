Single Scenario Execution
=========================

* In an empty directory initialize a project named "single_scen_exec" with the current language

Passing execution of single scenario
------------------------------------

* Create a specification "newSpec" with the following contexts

     |step text    |implementation        |
     |-------------|----------------------|
     |First context|"inside first context"|

* Create a scenario "first scenario" in specification "newSpec" with the following steps with implementation 

     |step text          |implementation              |
     |-------------------|----------------------------|
     |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "newSpec" with the following steps with implementation

     |step text           |implementation               |
     |--------------------|-----------------------------|
     |Second Scenario step|"inside second scenario step"|

* Execute the spec "newSpec" with scenario at "6" and ensure success

* Console should contain following lines in order

     |console output            |
     |--------------------------|
     |inside first context      |
     |inside first scenario step|

* Console should not contain following lines

     |console output             |
     |---------------------------|
     |inside second scenario step|
