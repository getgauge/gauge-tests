Step should not be empty
========================

* In an empty directory initialize a project named "step_should_not_be_empty" with the current language

step should not be empty
------------------------

* Create a scenario "step should not be empty" in specification "spec step_should_not_be_empty" with the following steps 

     |step text|implementation     |
     |---------|-------------------|
     |         |"inside first step"|

* Check for validation errors in the project and ensure failure

* Console should contain following lines in order 

     |output                  |
     |------------------------|
     |[ParseError]            |
     |Step should not be blank|
