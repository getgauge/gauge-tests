Step validation
===============

tags: validation, java, csharp, ruby, python

* In an empty directory initialize a project named "validation" with the current language

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
