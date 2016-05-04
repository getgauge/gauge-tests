Formatting
==========
* In an empty directory initialize a project named "formatting" with the current language
     
formatting merges scenario with same heading
--------------------------------------------
* Create a scenario "formatting merges scenario with same heading" in specification "spec formatting" with the following steps
    |step text  |implementation   |
    |-----------|-----------------|
    |"something"|"inside somthing"|

* Create a scenario "in between" in specification "spec formatting" with the following steps
    |step text  |implementation   |
    |-----------|-----------------|
    |"step between two duplicate scenario"|"inside between"|

* Create a scenario "formatting merges scenario with same heading" in specification "spec formatting" with the following steps
    |step text  |implementation   |
    |-----------|-----------------|
    |"something"|"inside somthing"|

* Check for validation errors in the project and ensure failure

* Format specs and ensure failure

* Console should contain "Parse error: Duplicate scenario definition"

* Spec "spec formatting" should not be formatted