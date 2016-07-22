Spec with even one scenarios failing is considered as failed
============================================================

* In an empty directory initialize a project named "spec_with_failing_scenarios" with the current language

Passing execution of a spec with single failing scenario
--------------------------------------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 

     |step text  |implementation      |
     |-----------|--------------------|
     |First step |"inside first step" |
     |Second step|"inside second step"|
     |Third step |throw exception     |

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output         |
     |-----------------------|
     |inside first step      |
     |inside second step     |
     |Failed Step: Third step|
     