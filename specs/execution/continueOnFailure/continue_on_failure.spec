Continue on Failure
===================
tags: continueOnFailure

* In an empty directory initialize a project named "continueOnFailure" without example spec

Should continue when there is a failure with Continue on failure attribute in a context step
--------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts

     |step text |implementation |continue|
     |----------|---------------|--------|
     |First step|throw exception|true    |

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
     |Failed Step: First step |
     |Failed Step: Second step|
     |Failed Step: Third step |

* Console should not contain following lines

     |console output          |
     |------------------------|
     |Failed Step: fourth step|

Should not continue when there is a failure before a step with Continue on failure attribute in a context step
--------------------------------------------------------------------------------------------------------------

* Create a specification "continueOnFailureSpec" with the following contexts

     |step text  |implementation     |continue|
     |-----------|-------------------|--------|
     |First step |throw exception    |true    |
     |Second step|throw exception    |false   |
     |Third step |"inside third step"|true    |

* Create a scenario "continueOnFailureScenario" in specification "continueOnFailureSpec" with the following continue on failure steps

     |step text  |implementation |continue|
     |-----------|---------------|--------|
     |fourth step|throw exception|true    |

* Execute the current project and ensure failure

* Console should contain following lines in order

     |console output          |
     |------------------------|
     |Failed Step: First step |
     |Failed Step: Second step|

* Console should not contain following lines

     |console output          |
     |------------------------|
     |Failed Step: Third step |
     |Failed Step: fourth step|

Should continue when there is a failure with Continue on failure attribute in a scenario step
---------------------------------------------------------------------------------------------
* Create a specification "continueOnFailureSpec" with the following contexts

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|

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

Should not continue when there is a failure before a step with Continue on failure attribute in a scenario step
---------------------------------------------------------------------------------------------------------------
* Create a specification "continueOnFailureSpec" with the following contexts

     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|


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

Should continue when there is a failure with Continue on failure attribute in a tear down step
----------------------------------------------------------------------------------------------

Should not continue when there is a failure before a step with Continue on failure attribute in a tear down step
----------------------------------------------------------------------------------------------------------------

Should continue when there is a failure with Continue on failure attribute in a concept step
--------------------------------------------------------------------------------------------
* Create concept "concept with continue on failure steps" with following steps

     |concept steps                |
     |-----------------------------|
     |say hello                    |
     |Step that throws an exception|
     |say hello again              |

* Create step implementations

     |step                         |implementation  |continue|
     |-----------------------------|----------------|--------|
     |say hello                    |"hello world"   |false   |
     |Step that throws an exception|throw exception |true    |
     |say hello again              |"hello universe"|false   |

* Create "continue on failure even the failure in concept" in "Spec with concepts" with the following steps

     |scenario steps                        |
     |--------------------------------------|
     |say hello                             |
     |concept with continue on failure steps|
     |say hello                             |

* Execute the spec "Spec with concepts" and ensure failure

* Console should contain following lines in order

     |console output table                      |
     |------------------------------------------|
     |hello world                               |
     |hello world                               |
     |Failed Step: Step that throws an exception|
     |hello universe                            |
     |hello world                               |


Should not continue when there is a failure before a step with Continue on failure attribute in a concept step
--------------------------------------------------------------------------------------------------------------
* Create concept "concept with continue on failure steps" with following steps

     |concept steps                |
     |-----------------------------|
     |say hello                    |
     |Step that throws an exception|
     |say hello again              |

* Create step implementations

     |step                         |implementation  |continue|
     |-----------------------------|----------------|--------|
     |say hello                    |"hello world"   |false   |
     |Step that throws an exception|throw exception |false   |
     |say hello again              |"hello universe"|false   |

* Create "continue on failure even the failure in concept" in "Spec with concepts" with the following steps

     |scenario steps                        |
     |--------------------------------------|
     |say hello                             |
     |concept with continue on failure steps|
     |say hello                             |

* Execute the spec "Spec with concepts" and ensure failure

* Console should contain following lines in order

     |console output table                      |
     |------------------------------------------|
     |hello world                               |
     |hello world                               |
     |Failed Step: Step that throws an exception|

* Console should not contain following lines

     |console output table|
     |--------------------|
     |hello universe      |
