Continue on failure with concepts
=================================

tags: continueOnFailure

* In an empty directory initialize a project named "continue_on_failure_with_concepts" with the current language
* Create concept "concept with continue on failure steps" with following steps 

     |concept steps                |
     |-----------------------------|
     |say hello                    |
     |Step that throws an exception|
     |say hello again              |

Test concept to continue on failure
-----------------------------------
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

Test concept to not continue on failure when failing step in not marked as continue
-----------------------------------------------------------------------------------
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
