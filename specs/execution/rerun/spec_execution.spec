Rerun Failed Tests
==================

tags: execution, rerun

* In an empty directory initialize a project named "spec_exec" with the current language

Rerun failed scenarios
----------------------

* Create "Sample scenario" in "Basic_spec_execution" with the following steps

     |step text  |implementation     |
     |-----------|-------------------|
     |First step |"inside first step"|
     |Second step|throw exception    |
* Create "Sample scenario1" in "Basic_spec_execution1" with the following steps 

     |step text |implementation     |
     |----------|-------------------|
     |Third step|"inside third step"|
* Create "Sample scenario2" in "Basic_spec_execution2" with the following steps 

     |step text  |implementation      |
     |-----------|--------------------|
     |Fourth step|"inside fourth step"|
     |Fifth step |throw exception     |
* Execute the current project and ensure failure
* Rerun failed scenarios and ensure failure
* Console should contain following lines in order 

     |console output    |
     |------------------|
     |inside first step |
     |inside fourth step|
* Console should not contain following lines 

     |console output   |
     |-----------------|
     |inside third step|
