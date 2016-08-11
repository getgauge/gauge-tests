Nested Concept Failing
======================

* In an empty directory initialize a project named "nested_concept_fail" with the current language
* Create concept "concept1 with <param0> and <param1>" with following steps 

     |concept steps                      |
     |-----------------------------------|
     |Tell <param0> to "concept1"        |
     |concept2 with <param0> and <param1>|
     |Tell <param1> to "concept1"        |

* Create concept "concept2 with <param2> and <param3>" with following steps 

     |concept steps                |
     |-----------------------------|
     |Tell <param2> to "concept2"  |
     |Step that throws an exception|
     |Tell <param3> to "concept2"  |

* Create step implementations 

     |step text                    |implementation |
     |-----------------------------|---------------|
     |Step that throws an exception|throw exception|
     |Tell <param0> to <param1>    |print params   |

Test concept failure Path
-------------------------

* Create "Concept Testing" in "Spec 2" with the following steps 

     |step text                    |
     |-----------------------------|
     |Tell "HI" to "Gauge"         |
     |concept1 with "abc" and "xyz"|
     |Tell "BYE" to "Gauge"        |

* Execute the spec "Spec 2" and ensure failure
* Console should not contain following lines 

     |console output table      |
     |--------------------------|
     |param0=xyz,param1=concept2|
     |param0=BYE,param1=Gauge   |
     |param0=xyz,param1=concept1|

* Console should contain following lines in order 

     |console output table      |
     |--------------------------|
     |param0=HI,param1=Gauge    |
     |param0=abc,param1=concept1|
     |param0=abc,param1=concept2|
