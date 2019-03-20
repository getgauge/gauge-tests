Continue on failure
===================

tags: continueOnFailure, java

* Initialize a project named "continueOnFailure" without example spec

* Create a specification "continueOnFailureSpec" with the following contexts 

   |step text |implementation     |
   |----------|-------------------|
   |First step|"inside first step"|

Continue execution on any error/exception in "Continue on Failure" step
-----------------------------------------------------------------------

tags: csharp, dotnet, ruby, python, js

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following steps with implementation 

   |step text  |implementation     |continue on failure|error type|
   |-----------|-------------------|-------------------|----------|
   |Second step|throw exception    |true               |          |
   |Third step |"inside third step"|false              |          |
   |fourth step|throw exception    |true               |          |

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

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |1        |0           |

Stop execution on failure of some other step
--------------------------------------------

tags: csharp, dotnet, ruby, python, js

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following steps with implementation 

   |step text  |implementation     |continue on failure|error type|
   |-----------|-------------------|-------------------|----------|
   |Second step|throw exception    |true               |          |
   |Third step |"inside third step"|false              |          |
   |Fourth step|throw exception    |false              |          |
   |Fifth step |throw exception    |true               |          |
   |Next step  |"inside next step" |false              |          |

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

* Console should contain "Successfully generated html-report to =>"

Continue execution only for mentioned error/exception in "Continue on Failure" step
-----------------------------------------------------------------------------------

tags: js

* Create a scenario "Continue of failure for specified error" in specification "continueOnFailureSpec" with the following steps with implementation 

   |step text  |implementation      |continue on failure|error type            |
   |-----------|--------------------|-------------------|----------------------|
   |Second step|throw exception     |true               |RuntimeException.class|
   |Third step |throw AssertionError|true               |AssertionError.class  |
   |Fourth step|"inside fourth step"|false              |                      |

* Execute the current project and ensure failure
* Console should contain following lines in order 

   |console output          |
   |------------------------|
   |inside first step       |
   |Failed Step: Second step|
   |Failed Step: Third step |
   |inside fourth step      |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

Stop execution on failure due to unspecified errors
---------------------------------------------------

* Create a scenario "Continue on failure only on specified error" in specification "continueOnFailureSpec" with the following steps with implementation 

   |step text  |implementation     |continue on failure|error type                |
   |-----------|-------------------|-------------------|--------------------------|
   |Second step|throw exception    |true               |NullPointerException.class|
   |Third step |"inside third step"|true               |                          |

* Execute the current project and ensure failure
* Console should contain following lines in order 

   |console output          |
   |------------------------|
   |inside first step       |
   |Failed Step: Second step|

* Console should not contain following lines 

   |console output   |
   |-----------------|
   |inside third step|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |
