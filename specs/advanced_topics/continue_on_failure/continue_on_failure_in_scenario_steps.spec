Continue on Failure
===================
tags: continueOnFailure

* In an empty directory initialize a project named "continueOnFailure" without example spec

Should continue when there is a failure with Continue on failure attribute in a scenario step
---------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts 

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following continue on failure steps 

     |step text  |implementation     |continue on failure|
     |-----------|-------------------|-------------------|
     |Second step|throw exception    |true               |
     |Third step |"inside third step"|false              |
     |fourth step|throw exception    |true               |

* Execute the current project and ensure failure
* Console should contain following lines in order 

     |console output          |
     |------------------------|
     |inside first step       |
     |Failed Step: Second step|
     |inside third step       |
     |Failed Step: fourth step|

* Statistics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |0        |1        |0           |

Should not continue when there is a failure before a step with Continue on failure attribute in a scenario step
---------------------------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts 

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|


* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following continue on failure steps 

     |step text  |implementation     |continue on failure|
     |-----------|-------------------|-------------------|
     |Second step|throw exception    |true               |
     |Third step |"inside third step"|false              |
     |Fourth step|throw exception    |false              |
     |Fifth step |throw exception    |true               |
     |Next step  |"inside next step" |false              |

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

* Statistics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |0        |1        |0           |