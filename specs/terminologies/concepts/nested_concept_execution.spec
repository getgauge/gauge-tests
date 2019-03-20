Nested Concept Execution
========================

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "nested_concept_exec" without example spec
* Create concept "concept with <param0> and <param1>" with following steps 

   |concept steps                            |
   |-----------------------------------------|
   |nested concept with <param0> and <param1>|
   |simple step with "static"                |

* Create concept "nested concept with <p0> and <p1>" with following steps 

   |concept steps                                 |
   |----------------------------------------------|
   |second level nested concept with <p0> and <p1>|

* Create concept "second level nested concept with <p-0> and <p-1>" with following steps 

   |concept steps                   |
   |--------------------------------|
   |nested step with <p-0> and <p-1>|

* Create step implementations 

   |step text                       |implementation|
   |--------------------------------|--------------|
   |simple step with <parameter0>   |print params  |
   |nested step with <p-0> and <p-1>|print params  |

Nested Concept execution
------------------------

* Create a scenario "Scenario for concept execution" in specification "2 level nested Concept execution" with the following steps with implementation 

   |step text                        |
   |---------------------------------|
   |concept with "first" and "second"|

* Execute the spec "2 level nested Concept execution" and ensure success
* Console should contain following lines in order 

   |console output            |
   |--------------------------|
   |param0=first,param1=second|
   |param0=static             |

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

Nested Concept execution with data table
----------------------------------------

* Create a specification "nested concept execution with data table" with the following datatable 

   |id|name    |phone|
   |--|--------|-----|
   |1 |apoorva |999  |
   |2 |prateek |007  |
   |3 |srikanth|100  |

* Create a scenario "Table scenario" in specification "nested concept execution with data table" with the following steps with implementation 

   |step text                      |
   |-------------------------------|
   |concept with <name> and <phone>|
   |concept with <id> and <name>   |

* Execute the spec "nested concept execution with data table" and ensure success
* Console should contain following lines in order 

   |console output            |
   |--------------------------|
   |param0=apoorva,param1=999 |
   |param0=static             |
   |param0=1,param1=apoorva   |
   |param0=static             |
   |param0=prateek,param1=007 |
   |param0=static             |
   |param0=2,param1=prateek   |
   |param0=static             |
   |param0=srikanth,param1=100|
   |param0=static             |
   |param0=3,param1=srikanth  |
   |param0=static             |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |3       |3     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |1        |0        |0           |
