Continue on Failure of a Step
=============================

tags: continueOnFailure

* In an empty directory initialize a project named "continueOnFailure" without example spec
* Create a specification "continueOnFailureSpec" with the following contexts 

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|

Continue execution after a failed Step marked as continue
---------------------------------------------------------

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following continue on failure steps 

     |step text  |implementation     |continue|
     |-----------|-------------------|--------|
     |Second step|throw exception    |true    |
     |Third step |"inside third step"|false   |
     |fourth step|throw exception    |true    |

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output          |
     |------------------------|
     |inside first step       |
     |Failed Step: Second step|
     |inside third step       |
     |Failed Step: fourth step|

Stop execution after a failed step even there are other continueOnFailure steps
-------------------------------------------------------------------------------

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following continue on failure steps 

     |step text  |implementation     |continue|
     |-----------|-------------------|--------|
     |Second step|throw exception    |true    |
     |Third step |"inside third step"|false   |
     |Fourth step|throw exception    |false   |
     |Fifth step |throw exception    |true    |
     |Next step  |"inside next step" |false   |

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output          |
     |------------------------|
     |inside first step       |
     |Failed Step: Second step|
     |inside third step       |
     |Failed Step: Fourth step|

* Console should not contain following lines 

     |console output         |
     |-----------------------|
     |Failed Step: Fifth step|
     |inside next step       |
