concept execution
=================

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "concept_execution" without example spec

* Create concept "sample_concept" with following steps 

   |concept steps|implementation|
   |-------------|--------------|
   |Tell         |print params  |

Resolve concepts when executing from nested directories in specs
----------------------------------------------------------------

* Create "resolve concept" in "concept" within sub folder "specs/subfolder" with the following steps 

   |step text     |
   |--------------|
   |sample_concept|

* Execute the spec "concept" from folder "specs/subfolder/" and ensure success

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
