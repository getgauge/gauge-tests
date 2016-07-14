Undefined dynamic parameter used in a step
==========================================

When a parameter not defined is used in a step it should give a parse error
---------------------------------------------------------------------------

* In an empty directory initialize a project named "undefined_parameter_name_used_in_a_step" with the current language
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
     