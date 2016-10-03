Refactoring with special characters
===================================

tags: refactoring, java, csharp, ruby, python

Rephrase step with special and keyword parameters
-------------------------------------------------

* In an empty directory initialize a project named "refactoring_numeric_keyword_parameter" without example spec
* Create a scenario "scenario refactoring numeric and keyword parameter" in specification "spec refactoring numeric parameter" with the following steps with implementation 

     |step text                                    |implementation|
     |---------------------------------------------|--------------|
     |Step with "three" "params" "withspecialchars"|"inside step" |

* Refactor step "Step with \"two\" \"params\" \"withspecialchars\"" to "Step with \"123\" \"new\" \"|\""
* Execute the spec "spec refactoring numeric parameter" and ensure success

Refactor the step with special chars in implementation
------------------------------------------------------

* In an empty directory initialize a project named "refactor_step_with_special_chars" without example spec
* Create a scenario "Sample scenario" in specification "refactor the step with special chars" with the following steps with implementation 

     |step text|implementation                                         |
     |---------|-------------------------------------------------------|
     |A step   |"//*[@id=\\"content\\"]/section[1]/section/div[2]/a[1]"|

* Refactor step "A step" to "New step"
* Execute the spec "refactor the step with special chars" and ensure success
* Console should not contain following lines 

     |output                                            |
     |--------------------------------------------------|
     |"//*[@id="conten"]/section[1]/section/div[2]/a[1]"|
