Context Execution
=================

* In an empty directory initialize a project with the current language

Passing context execution
-------------------------

* Create a specification "basic context execution" with the following contexts 
     |step text     |implementation         |
     |--------------|-----------------------|
     |First context |"inside first context" |
     |Second context|"inside second context"|

* Create a scenario "first scenario" in specification "basic context execution" with the following steps with implementation
     |step text          |implementation              |
     |-------------------|----------------------------|
     |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "basic context execution" with the following steps with implementation
     |step text           |implementation               |
     |--------------------|-----------------------------|
     |Second Scenario step|"inside second scenario step"|

* Execute the spec "basic context execution" and ensure success
* Console should contain following lines in order 
     |console output             |
     |---------------------------|
     |inside first context       |
     |inside second context      |
     |inside first scenario step |
     |inside first context       |
     |inside second context      |
     |inside second scenario step|

