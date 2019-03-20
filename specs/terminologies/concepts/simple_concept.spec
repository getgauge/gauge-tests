Concepts with no parameters
===========================

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "single_scen_exec" without example spec

Invoke a concept with no parameters
-----------------------------------

* Create concept "Sample concept with no parameters" with following steps 

   |step text       |implementation              |
   |----------------|----------------------------|
   |Concept step one|"inside first concept step" |
   |Concept step two|"inside second concept step"|

* Create a scenario "Scenario for concept execution" in specification "Concept execution" with the following steps with implementation 

   |step text                        |implementation      |
   |---------------------------------|--------------------|
   |Sample concept with no parameters|                    |
   |Fourth step                      |"inside fourth step"|

* Execute the spec "Concept execution" and ensure success
* Console should contain following lines in order 

   |console output            |
   |--------------------------|
   |inside first concept step |
   |inside second concept step|
   |inside fourth step        |

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
