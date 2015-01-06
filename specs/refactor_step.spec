Refactor Specification
======================
* In an empty directory initialize a project with the current language

* Create "Sample scenario" in "Basic spec execution" with the following steps
     |step text               |implementation                                          |
     |------------------------|--------------------------------------------------------|
     |First step              |"inside first step"                                     |
     |Second step             |"inside second step"                                    |
     |Repeated First step     |"inside second step"                                    |
     |Step with "two" "params"|"inside step with parameters : " + param0 + " " + param1|


Rename step
-----------
* Refactor step "First step" to "New step"
* Verify that project should not contain "First step"
* Verify that project contains "New step"

Rephrase simple step
--------------------
* Refactor step "Step with <a> <b>" to "Step having <b> and <a>"
* Verify that project should not contain "Step with \"two\" \"params\""
* Verify that project contains "Step having \"params\" and \"two\""

Rephrase step having new parameters
-----------------------------------
* Refactor step "Step with <a> <b>" to "Step having <b> <c> and <a>"
* Verify that project should not contain "Step with \"two\" \"params\""
* Verify that project contains "Step having \"params\" \"\" and \"two\""

Rephrase step removing parameters
---------------------------------
* Refactor step "Step with <a> <b>" to "Step having <b> and <c>"
* Verify that project should not contain "Step with \"two\" \"params\""
* Verify that project contains "Step having \" params \" and \"\""

Rephrase step with all new parameters
-------------------------------------
* Refactor step "Step with <a> <b>" to "Step having <d> and <c>"
* Verify that project should not contain "Step with \"two\" \"params\""
* Verify that project contains "Step having \"\" and \"\""

Refactor a non-Existing step
----------------------------
* Refactor step "hello" to "world"
* Console should contain "Failed to create refactoring request: Step implementation not found: hello"
