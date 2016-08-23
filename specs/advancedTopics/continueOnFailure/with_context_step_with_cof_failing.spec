Continue on failure in setup
============================
tags: continueOnFailure

* In an empty directory initialize a project named "continueOnFailure" without example spec

With failures only in steps with cof across setup, scenario and teardown - the test should execute all steps
----------------------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts 

     |step text                 |implementation     |continue on failure|
     |--------------------------|-------------------|-------------------|
     |step 1 continue on failure|throw exception    |true               |
     |hello step                |"inside hello step"|false              |

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following continue on failure steps 

     |step text                 |implementation       |continue on failure|
     |--------------------------|---------------------|-------------------|
     |step 2 continue on failure|throw exception      |true               |
     |Normal step1              |"inside normal step1"|false              |
     |step 3 continue on failure|throw exception      |true               |
     |Normal step2              |"inside normal step2"|false              |

* Add the following teardown steps in specification "continueOnFailureSpec" 

     |step text                 |
     |--------------------------|
     |step 2 continue on failure|
     |Normal step1              |
     |step 3 continue on failure|
     |Normal step2              |

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output                         |
     |---------------------------------------|
     |Failed Step: step 1 continue on failure|
     |inside hello step                      |
     |Failed Step: step 2 continue on failure|
     |inside normal step1                    |
     |Failed Step: step 3 continue on failure|
     |inside normal step2                    |
     |Failed Step: step 2 continue on failure|
     |inside normal step1                    |
     |Failed Step: step 3 continue on failure|
     |inside normal step2                    |

* Statics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |

With failures in setup after step cof fails - the test should stop at the failure with no cof in setup
------------------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec2" with the following contexts 

     |step text                 |implementation     |continue on failure|
     |--------------------------|-------------------|-------------------|
     |hello step                |"inside hello step"|false              |
     |step 4 continue on failure|throw exception    |true               |
     |fail step                 |throw exception    |false              |

* Create a scenario "continueOnFailureScenario2" in specification "continueOnFailureSpec2" with the following continue on failure steps 

     |step text  |implementation     |continue on failure|
     |-----------|-------------------|-------------------|
     |Second step|throw exception    |true               |
     |Third step |"inside third step"|false              |
     |fourth step|throw exception    |true               |

* Add the following teardown steps in specification "continueOnFailureSpec2" 

     |step text                 |implementation       |continue on failure|
     |--------------------------|---------------------|-------------------|
     |step 5 continue on failure|throw exception      |true               |
     |Normal step3              |"inside normal step3"|false              |
     |step 6 continue on failure|throw exception      |true               |
     |Normal step4              |"inside normal step4"|false              |

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output                         |
     |---------------------------------------|
     |inside hello step                      |
     |Failed Step: step 4 continue on failure|
     |Failed Step: fail step                 |

* Console should not contain following lines 

     |console output                         |
     |---------------------------------------|
     |Failed Step: Second step               |
     |inside third step                      |
     |Failed Step: fourth step               |
     |Failed Step: step 5 continue on failure|
     |inside Normal step3                    |
     |Failed Step: step 6 continue on failure|
     |inside Normal step4                    |

* Statics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |

With failures in scenario after step cof fails - the test should stop at the failure with no cof in setup
---------------------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec3" with the following contexts 

     |step text                 |implementation       |continue on failure|
     |--------------------------|---------------------|-------------------|
     |Normal step5              |"inside Normal step5"|false              |
     |step 7 continue on failure|throw exception      |true               |

* Create a scenario "continueOnFailureScenario3" in specification "continueOnFailureSpec3" with the following continue on failure steps 

     |step text                 |implementation       |continue on failure|
     |--------------------------|---------------------|-------------------|
     |step 8 continue on failure|throw exception      |true               |
     |fail step2                |throw exception      |false              |
     |Normal step6              |"inside Normal step6"|false              |

* Add the following teardown steps in specification "continueOnFailureSpec3" 

     |step text                  |implementation       |continue on failure|
     |---------------------------|---------------------|-------------------|
     |step 9 continue on failure |throw exception      |true               |
     |Normal step7               |"inside normal step7"|false              |
     |step 10 continue on failure|throw exception      |true               |
     |Normal step8               |"inside normal step8"|false              |

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output                         |
     |---------------------------------------|
     |Failed Step: step 7 continue on failure|
     |inside Normal step5                    |
     |Failed Step: step 8 continue on failure|
     |Failed Step: fail step2                |

* Console should not contain following lines 

     |console output                          |
     |----------------------------------------|
     |inside Normal step6                     |
     |Failed Step: step 9 continue on failure |
     |inside Normal step7                     |
     |Failed Step: step 10 continue on failure|
     |inside Normal step8                     |

* Statics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |


Should not continue when there is a failure with no cof before any Continue on step failures
--------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec5" with the following contexts 

     |step text  |implementation     |continue on failure|
     |-----------|-------------------|-------------------|
     |Second step|throw exception    |false              |
     |Third step |"inside third step"|true               |

* Create a scenario "continueOnFailureScenario5" in specification "continueOnFailureSpec5" with the following continue on failure steps 

     |step text  |implementation |continue on failure|
     |-----------|---------------|-------------------|
     |fourth step|throw exception|true               |

* Execute the current project and ensure failure

* Console should contain following lines in order 

     |console output          |
     |------------------------|
     |Failed Step: Second step|

* Console should not contain following lines 

     |console output          |
     |------------------------|
     |Failed Step: Third step |
     |Failed Step: fourth step|
