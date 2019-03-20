Table parameter
===============

tags: parameter, java, csharp, dotnet, ruby, python, js

* Initialize a project named "scenarios_with_table_parameter" without example spec

Inline table parameter
----------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text               |implementation                           |inlineTableHeaders|row1   |
   |------------------------|-----------------------------------------|------------------|-------|
   |First step <inlineTable>|"inside step with parameters : " + param0|header            |one,two|

* Execute the current project and ensure success

* Console should contain following lines in order 

   |console output               |
   |-----------------------------|
   |inside step with parameters :|
   |                             |
   |                             |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |1        |0        |0           |

External table parameter (csv)
------------------------------

* Create a csv file "abc" with "header\none\ntwo"

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text                       |implementation                           |
   |--------------------------------|-----------------------------------------|
   |First step <table:specs/abc.csv>|"inside step with parameters : " + param0|

* Execute the current project and ensure success

* Console should contain following lines in order 

   |console output               |
   |-----------------------------|
   |inside step with parameters :|
   |                             |
   |                             |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |1        |0        |0           |
