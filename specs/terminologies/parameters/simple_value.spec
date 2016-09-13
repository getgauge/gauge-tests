with simple value parameter
===========================

tags: parameter, java, csharp, ruby

* In an empty directory initialize a project named "scenarios_with_simple_parameter" without example spec

steps with no parameters
------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 

     |step text  |implementation      |
     |-----------|--------------------|
     |First step |"inside first step" |
     |Second step|"inside second step"|
     |Third step |"inside third step" |

* Execute the current project and ensure success

* Console should contain following lines in order 

     |console output    |
     |------------------|
     |inside first step |
     |inside second step|
     |inside third step |

* Statistics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |1       |1     |0     |0      |

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |1        |0        |0           |

steps with simple parameters
----------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 

     |step text                 |implementation                                          |
     |--------------------------|--------------------------------------------------------|
     |"2" "params" used in steps|"inside step with parameters : " + param0 + " " + param1|
     |Step with "two" "params"  |"inside step with parameters : " + param0 + " " + param1|

* Execute the current project and ensure success

* Console should contain following lines in order 

     |console output                          |
     |----------------------------------------|
     |inside step with parameters : 2 params  |
     |inside step with parameters : two params|

* Statistics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |1       |1     |0     |0      |

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |1        |0        |0           |