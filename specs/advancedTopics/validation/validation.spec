Validation
==========

* In an empty directory initialize a project named "validation" with the current language

Validation Failure
------------------

* Create "Scenario with unimplemented steps" in "Validation Spec" with the following steps 

     |step text  |
     |-----------|
     |First Step |
     |Second Step|

* Check for validation errors in the project and ensure failure
* Console should contain following lines in order 

     |Console output                                |
     |----------------------------------------------|
     |Step implementation not found => 'First Step' |
     |Step implementation not found => 'Second Step'|

Validation Success
------------------

* Create "Scenario with unimplemented steps" in "Validation Spec" with the following steps 

     |step text  |implementation|
     |-----------|--------------|
     |First Step |"First step"  |
     |Second Step|"Second step" |

* Check for validation errors in the project and ensure success

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

When a parameter not defined is used in a step it should give a parse error
---------------------------------------------------------------------------

* Create a scenario "undefined dynamic parameter" in specification "undefined_dynamic_parameter_used_in_a_step" with the following steps with implementation 

     |step text                 |implementation     |
     |--------------------------|-------------------|
     |Step with <param> as param|"inside first step"|

* Check for validation errors in the project and ensure failure
* Console should contain following lines in order 

     |output                                         |
     |-----------------------------------------------|
     |[ParseError]                                   |
     |Dynamic parameter <param> could not be resolved|
