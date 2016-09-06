Continue on failure in setup
============================
tags: continueOnFailure

* In an empty directory initialize a project named "continueOnFailure" without example spec

Should continue when there is a failure with Continue on failure attribute only in a tear down step
----------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following steps with implementation

     |step text  |implementation      |continue on failure|
     |-----------|--------------------|-------------------|
     |Second step|"inside second step"|false              |
     |Third step |"inside third step" |false              |

* Add the following teardown steps in specification "continueOnFailureSpec"

     |step text      |implementation |continue on failure|
     |---------------|---------------|-------------------|
     |First teardown |throw exception|true               |
     |Second teardown|throw exception|false              |
     |Third teardown |throw exception|true               |

* Execute the current project and ensure failure
* Console should contain following lines in order

     |console output              |
     |----------------------------|
     |inside first step           |
     |inside second step          |
     |inside third step           |
     |Failed Step: First teardown |
     |Failed Step: Second teardown|

* Console should not contain following lines

     |console output             |
     |---------------------------|
     |Failed Step: Third teardown|

* Statics generated should have

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |

* verify statistics in html with

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |0        |1        |0           |

Should not continue when there is a failure before a step with Continue on failure attribute in a tear down step
----------------------------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following steps with implementation

     |step text  |implementation      |continue on failure|
     |-----------|--------------------|-------------------|
     |Second step|"inside second step"|false              |
     |Third step |"inside third step" |false              |
     |fourth step|throw exception     |true               |

* Add the following teardown steps in specification "continueOnFailureSpec"

     |step text      |implementation |continue on failure|
     |---------------|---------------|-------------------|
     |First teardown |throw exception|false              |
     |Second teardown|throw exception|true               |

* Execute the current project and ensure failure
* Console should contain following lines in order

     |console output             |
     |---------------------------|
     |inside first step          |
     |inside second step         |
     |inside third step          |
     |Failed Step: First teardown|

* Console should not contain following lines

     |console output              |
     |----------------------------|
     |Failed Step: Second teardown|

* Statics generated should have

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |

* verify statistics in html with

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |0        |1        |0           |

With failures in teardown steps after step cof fails - the test should stop at the failure with no cof in setup
---------------------------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec4" with the following contexts

     |step text                  |implementation       |continue on failure|
     |---------------------------|---------------------|-------------------|
     |step 11 continue on failure|throw exception      |true               |
     |Normal step9               |"inside Normal step9"|false              |

* Create a scenario "continueOnFailureScenario4" in specification "continueOnFailureSpec4" with the following continue on failure steps

     |step text                  |implementation        |continue on failure|
     |---------------------------|----------------------|-------------------|
     |Normal step10              |"inside Normal step10"|false              |
     |step 12 continue on failure|throw exception       |true               |

* Add the following teardown steps in specification "continueOnFailureSpec4"

     |step text                  |implementation        |continue on failure|
     |---------------------------|----------------------|-------------------|
     |step 13 continue on failure|throw exception       |true               |
     |Normal step11              |"inside Normal step11"|false              |
     |fail step3                 |throw exception       |false              |
     |step 14 continue on failure|throw exception       |true               |
     |Normal step12              |"inside normal step12"|false              |

* Execute the current project and ensure failure

* Console should contain following lines in order

     |console output                          |
     |----------------------------------------|
     |Failed Step: step 11 continue on failure|
     |inside Normal step9                     |
     |inside Normal step10                    |
     |Failed Step: step 12 continue on failure|
     |Failed Step: step 13 continue on failure|
     |inside Normal step11                    |
     |Failed Step: fail step3                 |

* Console should not contain following lines

     |console output                          |
     |----------------------------------------|
     |Failed Step: step 14 continue on failure|
     |inside Normal step12                    |

* Statics generated should have

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |0     |1     |0      |
     |Scenarios      |1       |0     |1     |0      |

* verify statistics in html with

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |0        |1        |0           |
