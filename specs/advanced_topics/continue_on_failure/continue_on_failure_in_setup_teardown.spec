Continue on failure in setup / teardown
=======================================

tags: continueOnFailure, java, csharp, dotnet, ruby, python, js

* Initialize a project named "continueOnFailure" without example spec

Continue on failure in setup and teardown
-----------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts 

   |step text                 |implementation     |continue on failure|
   |--------------------------|-------------------|-------------------|
   |step 1 continue on failure|throw exception    |true               |
   |hello step                |"inside hello step"|false              |

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following steps with implementation 

   |step text                 |implementation       |continue on failure|
   |--------------------------|---------------------|-------------------|
   |step 2 continue on failure|throw exception      |true               |
   |normal step1              |"inside normal step1"|false              |
   |step 3 continue on failure|throw exception      |true               |
   |normal step2              |"inside normal step2"|false              |

* Add the following teardown steps in specification "continueOnFailureSpec" 

   |step text                 |
   |--------------------------|
   |step 2 continue on failure|
   |normal step1              |
   |step 3 continue on failure|
   |normal step2              |

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

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

* Console should contain "Successfully generated html-report to =>"

Execute teardown if some other step fails in setup
--------------------------------------------------

* Create a specification "continueOnFailureSpec2" with the following contexts 

   |step text                 |implementation     |continue on failure|
   |--------------------------|-------------------|-------------------|
   |hello step                |"inside hello step"|false              |
   |step 4 continue on failure|throw exception    |true               |
   |fail step                 |throw exception    |false              |

* Create a scenario "continueOnFailureScenario2" in specification "continueOnFailureSpec2" with the following steps with implementation 

   |step text  |implementation     |continue on failure|
   |-----------|-------------------|-------------------|
   |Second step|throw exception    |true               |
   |Third step |"inside third step"|false              |
   |fourth step|throw exception    |true               |

* Add the following teardown steps in specification "continueOnFailureSpec2" 

   |step text                 |implementation       |continue on failure|
   |--------------------------|---------------------|-------------------|
   |step 5 continue on failure|throw exception      |true               |
   |normal step3              |"inside normal step3"|false              |
   |step 6 continue on failure|throw exception      |true               |
   |normal step4              |"inside normal step4"|false              |

* Execute the current project and ensure failure

* Console should contain following lines in order 

   |console output                         |
   |---------------------------------------|
   |inside hello step                      |
   |Failed Step: step 4 continue on failure|
   |Failed Step: fail step                 |
   |Failed Step: step 5 continue on failure|
   |inside normal step3                    |
   |Failed Step: step 6 continue on failure|
   |inside normal step4                    |

* Console should not contain following lines 

   |console output          |
   |------------------------|
   |Failed Step: Second step|
   |inside third step       |
   |Failed Step: fourth step|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

* Console should contain "Successfully generated html-report to =>"