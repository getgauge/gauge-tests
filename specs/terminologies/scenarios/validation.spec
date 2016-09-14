Scenario validation
===================

tags: validation, java, csharp, ruby

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
