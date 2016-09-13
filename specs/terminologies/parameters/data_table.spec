with table as parameter
=======================

tags: parameter, java, csharp, ruby

* In an empty directory initialize a project named "scenarios_with_table_parameter" without example spec

steps with inline table parameter
---------------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 

     |step text               |implementation                           |inlineTableHeaders|row1   |
     |------------------------|-----------------------------------------|------------------|-------|
     |First step <inlineTable>|"inside step with parameters : " + param0|header            |one,two|

* Execute the current project and ensure success

* Console should contain following lines in order 

     |console output                        |
     |--------------------------------------|
     |inside step with parameters : |header||
     ||one   |                              |
     ||two   |                              |

* Statistics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |1       |1     |0     |0      |

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |1        |0        |0           |

steps with csv table parameter
------------------------------

* Create a csv file "abc" with "header\none\ntwo"

* Create "Sample scenario" in "Basic spec execution" with the following steps 

     |step text                       |implementation                           |
     |--------------------------------|-----------------------------------------|
     |First step <table:specs/abc.csv>|"inside step with parameters : " + param0|

* Execute the current project and ensure success

* Console should contain following lines in order 

     |console output                        |
     |--------------------------------------|
     |inside step with parameters : |header||
     ||one   |                              |
     ||two   |                              |

* Statistics generated should have 

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |1       |1     |0     |0      |
     |Scenarios      |1       |1     |0     |0      |

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |1        |0        |0           |