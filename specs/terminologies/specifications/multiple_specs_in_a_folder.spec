Execute specs in a folder
=========================

tags: spec, java, csharp, dotnet, ruby, python, js

* Initialize a project named "scenarios_execution" without example spec

Execute multiple specs from a folder
------------------------------------

* Create "scenario 1" in "Spec 1" within sub folder "specs1" with the following steps 

   |step text   |implementation      |
   |------------|--------------------|
   |First step1 |"inside first step" |
   |Second step1|"inside second step"|

* Create "scenario 2" in "Spec 2" within sub folder "specs1" with the following steps 

   |step text   |implementation      |
   |------------|--------------------|
   |First step2 |"inside first step" |
   |Second step2|"inside second step"|

* Create "scenario 3" in "Spec 3" within sub folder "specs1" with the following steps 

   |step text   |implementation      |
   |------------|--------------------|
   |First step3 |"inside first step" |
   |Second step3|"inside second step"|


* Execute the spec folder "specs1/" and ensure success

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |3       |3     |0     |0      |
   |Scenarios      |3       |3     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |3         |3        |0        |0           |

Execute a spec from a folder which is not specs
-----------------------------------------------

* Create "scenario 1" in "Spec 1" within sub folder "specs1" with the following steps 

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|

* Execute the spec folder "specs1/" and ensure success

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

Execute a spec in a sub folder
------------------------------

* Create concept "Sample concept" with following steps 

   |step text       |implementation              |
   |----------------|----------------------------|
   |Concept step one|"inside first concept step" |
   |Concept step two|"inside second concept step"|

* Create "Concept Testing" in "Spec 1" within sub folder "specs/subFolder" with the following steps 

   |step text     |
   |--------------|
   |Sample concept|

* Execute the spec folder "specs/subFolder/" and ensure success

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
