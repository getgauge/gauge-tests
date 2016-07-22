Concept Failing
===============

* In an empty directory initialize a project named "concept_failing" with the current language
* Create concept "concept with <param0> and <param1>" with following steps 

     |concept steps                |
     |-----------------------------|
     |Tell <param0> to "Gauge"     |
     |Step that throws an exception|
     |Tell <param1> to "Gauge"     |

* Create step implementations 

     |step                         |implementation |
     |-----------------------------|---------------|
     |Step that throws an exception|throw exception|
     |Tell <param0> to <param1>    |print params   |

Test concept failure Path
-------------------------

* Create "Concept Testing" in "Spec 1" with the following steps 

     |scenario steps              |
     |----------------------------|
     |concept with "abc" and "xyz"|
     |Tell "HI" to "Gauge"        |

* Execute the spec "Spec 1" and ensure failure
* Console should not contain following lines 

     |console output table   |
     |-----------------------|
     |param0=xyz,param1=Gauge|
     |param0=HI,param1=Gauge |

* Console should contain following lines in order 

     |console output table   |
     |-----------------------|
     |param0=abc,param1=Gauge|
