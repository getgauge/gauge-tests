Validation
==========

* In an empty directory initialize a project with the current language

Execution failure in case of validation errors
----------------------------------------------

* Create "Scenario with unimplemented steps" in "Validation Spec" with the following steps 
     |step text     |
     |--------------|
     |Sample concept|
     |Fourth step   |
* Execute the spec "Validation Spec" and ensure failure
* Console should contain "Step implementation not found" "2" times
* Console should contain following lines in order 
     |Console output                               |
     |---------------------------------------------|
     |Step implementation not found. Sample concept|
     |Step implementation not found. Fourth step   |

