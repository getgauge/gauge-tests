Nested Concept Failure case
=====================
* In an empty directory initialize a project with the current language
* Create concept "concept1 with <param0> and <param1>" with following steps
     |        concept steps                  |
     |Say <param0> to "concept1"             |
     |concept2 with <param0> and <param1>    |
     |Say <param1> to "concept1"             |


* Create concept "concept2 with <param2> and <param3>" with following steps
       |        concept steps                  |
       |---------------------------------------|
       |Say <param2> to "concept2"             |
       |Step that throws an exception          |
       |Say <param3> to "concept2"             |

* Create step implementations
    |step|impl|
    |Step that throws an exception|throw exception|


Test concept failure Path
---------------------------

* Create "Concept Testing" in "Spec 2" with the following steps
    |scenario steps                |
    |------------------------------|
    |Say "HI" to "Gauge"           |
    |concept1 with "abc" and "xyz" |
    |Say "BYE" to "Gauge"          |

* Execute the spec "Spec 2" and ensure failure

* Console should not contain following lines
    |console output table|
    |--------------------|
    |xyz, concept2       |
    |BYE, Gauge          |
    |xyz, concept1       |

* Console should contain following lines in order
    |console output table|
    |--------------------|
    |HI, Gauge           |
    |abc, concept1       |
    |abc, concept2       |

