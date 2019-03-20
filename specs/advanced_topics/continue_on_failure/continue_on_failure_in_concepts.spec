Continue on failure in concepts
===============================

tags: continueOnFailure, java, csharp, dotnet, ruby, python, js

* Initialize a project named "continueOnFailure" without example spec

Continue on failure in a concept step
-------------------------------------

* Create concept "concept with continue on failure steps" with following steps 

   |concept steps                |
   |-----------------------------|
   |say hello                    |
   |Step that throws an exception|
   |say hello again              |

* Create step implementations 

   |step text                    |implementation  |continue on failure|
   |-----------------------------|----------------|-------------------|
   |say hello                    |"hello world"   |false              |
   |Step that throws an exception|throw exception |true               |
   |say hello again              |"hello universe"|false              |

* Create a scenario "continue on failure even the failure in concept" in specification "Spec with concepts" with the following steps 

   |step text                             |
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

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

* Console should contain "Successfully generated html-report to =>"

Continue on failure in a nested concept step
--------------------------------------------

* Create concept "concept level 1" with following steps 

   |concept steps                              |
   |-------------------------------------------|
   |say "concept level 1"                      |
   |Step that throws an exception and continues|
   |concept level 2                            |
   |say "concept level 1 again"                |

* Create concept "concept level 2" with following steps 

   |concept steps                              |
   |-------------------------------------------|
   |say "concept level 2"                      |
   |Step that throws an exception and continues|
   |say "concept level 2 second time"          |
   |concept level 3                            |
   |say "concept level 2 third time"           |

* Create concept "concept level 3" with following steps 

   |concept steps                |
   |-----------------------------|
   |say "concept level 3"        |
   |Step that throws an exception|
   |say "concept level 3 again"  |

* Create step implementations 

   |step text                                  |implementation |continue on failure|
   |-------------------------------------------|---------------|-------------------|
   |say <param0>                               |print params   |false              |
   |Step that throws an exception and continues|throw exception|true               |
   |Step that throws an exception              |throw exception|false              |

* Create a scenario "continue on failure even the failure in concept" in specification "Spec with nested concepts" with the following steps 

   |step text          |
   |-------------------|
   |say "hello world"  |
   |concept level 1    |
   |say "goodbye world"|

* Execute the spec "Spec with nested concepts" and ensure failure

* Console should contain following lines in order 

   |console output table                                    |
   |--------------------------------------------------------|
   |hello world                                             |
   |concept level 1                                         |
   |concept level 2                                         |
   |Failed Step: Step that throws an exception and continues|
   |concept level 2 second time                             |
   |concept level 3                                         |
   |Failed Step: Step that throws an exception              |

* Console should not contain following lines 

   |console output table      |
   |--------------------------|
   |concept level 3 again     |
   |concept level 2 third time|
   |concept level 1 again     |
   |goodbye world             |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

* Console should contain "Successfully generated html-report to =>"
