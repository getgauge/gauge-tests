Validation
==========

* In an empty directory initialize a project with the current language
* Create "Scenario with unimplemented steps" in "Validation Spec" with the following steps 
     |step text  |
     |-----------|
     |First Step |
     |Second Step|

Execution failure in case of validation errors
----------------------------------------------

* Execute the spec "Validation Spec" and ensure failure
* Console should contain following lines in order 
     |Console output                            |
     |------------------------------------------|
     |Step implementation not found. First Step |
     |Step implementation not found. Second Step|

Validation Failure
------------------

* Check for validation errors in the project and ensure failure
* Console should contain following lines in order 
     |Console output                            |
     |------------------------------------------|
     |Step implementation not found. First Step |
     |Step implementation not found. Second Step|

Validation Success
------------------

* Create step implementations 
     |step       |implementation|
     |-----------|--------------|
     |First Step |"First step"  |
     |Second Step|"Second step" |
* Check for validation errors in the project and ensure success
