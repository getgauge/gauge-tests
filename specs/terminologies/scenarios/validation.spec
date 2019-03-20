Scenario validation
===================

tags: validation, java, csharp, dotnet, ruby, python, js

* Initialize a project named "validation" without example spec

Validation Failure
------------------

* Create a scenario "Scenario with unimplemented steps" in specification "Validation Spec" with the following steps with implementation 

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

* Create a scenario "Scenario with unimplemented steps" in specification "Validation Spec" with the following steps with implementation 

   |step text  |implementation|
   |-----------|--------------|
   |First Step |"First step"  |
   |Second Step|"Second step" |

* Check for validation errors in the project and ensure success
