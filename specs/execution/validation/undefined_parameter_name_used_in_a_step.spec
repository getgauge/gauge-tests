Undefined parameter name used in a step
=======================================

tags: unimplemented

When a parameter not defined is used in a step it should give a parse
---------------------------------------------------------------------
* In an empty directory initialize a project named "undefined_parameter_name_used_in_a_step" with the current language

* Create a scenario "undefined parameter name used in a step" in specification "spec_undefined_parameter_name_used_in_a_step" with the following steps with implementation 
     |step text                 |implementation     |
     |--------------------------|-------------------|
     |Step with <param> as param|"inside first step"|

* Check for validation errors in the project and ensure failure

