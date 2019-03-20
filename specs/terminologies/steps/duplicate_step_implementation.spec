Duplicate step implementation
=============================

tags: step, java, csharp, dotnet, ruby, python, js

Duplicate step implementation should atleast give a warning
-----------------------------------------------------------

* Initialize a project named "duplicate_step_implementation" without example spec

* Create a scenario "scenaro1" in specification "duplicate step implementation" with the following steps with implementation 

   |step text       |implementation              |
   |----------------|----------------------------|
   |Concept step one|"inside first concept step" |
   |Concept step one|"inside second concept step"|

* Execute the spec "duplicate step implementation" and ensure success

* Console should contain "Duplicate step implementation"

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |0       |0     |0     |1      |
   |Scenarios      |0       |0     |0     |1      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |0        |1           |
