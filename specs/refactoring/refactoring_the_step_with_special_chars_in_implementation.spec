Refactoring the step with special chars in implementation
=========================================================
Refactoring code with escape character is formatting the code and causing the following issue
-----------------
tags:unimplemented

* In an empty directory initialize a project named "refactor_step_with_special_chars" with the current language

* Create a scenario "Sample scenario" in specification "Basic spec execution2" with the following steps with implementation 
     |step text|implementation|
     |---------|--------------|
     |A step   |"//*[@id=\\"content\\"]/section[1]/section/div[2]/a[1]"    |

Rename step
-----------
tags: refactoring

* Refactor step "A step" to "New step"
* Execute the spec "Basic spec execution2" and ensure success
* Console should not contain following lines 
     |output   |
     |---------|
     |"//*[@id=\"content\"]/section[1]/section/div[2]/a[1]"|
