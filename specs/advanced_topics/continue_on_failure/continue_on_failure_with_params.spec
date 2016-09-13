Continue on failure
===================
tags: continueOnFailure, java-only

* In an empty directory initialize a project named "ContinueOnFailure" without example spec

* Create a specification "continueOnFailure" with the following contexts 

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|

Continue on failure for specified error
---------------------------------------
* Create a scenario "Continue of failure for specified error" in specification "continueOnFailure" with the following steps to continue on corresponding failures 

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

Continue on failure for specified errors only
---------------------------------------------

* Create a scenario "Continue on failure only on specified error" in specification "continueOnFailure" with the following steps to continue on corresponding failures 

     |step text  |implementation     |continue on failure|error type  |
     |-----------|-------------------|-------------------|------------|
     |Second step|throw exception    |true               |String.class|
     |Third step |"inside third step"|true               |            |

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


Continue on failure on all types of errors
------------------------------------------
If the option parameter error is not specified along with Continue on failure, it should continue on all types of errors.

* Create a scenario "Continue on failure only on specified error" in specification "continueOnFailure" with the following steps to continue on corresponding failures 

     |step text  |implementation      |continue on failure|error type|
     |-----------|--------------------|-------------------|----------|
     |Second step|throw exception     |true               |          |
     |Third step |throw AssertionError|true               |          |
     |Fourth step|"inside fourth step"|true               |          |

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

