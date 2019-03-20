Parallel Execution
==================

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "parallel_exec" without example spec
* Create a scenario "Scenario 11" in specification "Spec 1" with the following steps with implementation 

   |step text        |implementation               |
   |-----------------|-----------------------------|
   |First step spec 1|"inside first step in spec 1"|

* Create a scenario "Scenario 21" in specification "Spec 2" with the following steps with implementation 

   |step text        |implementation               |
   |-----------------|-----------------------------|
   |First step spec 2|"inside first step in spec 2"|

* Create a scenario "Scenario 31" in specification "Spec 3" with the following steps with implementation 

   |step text        |implementation               |
   |-----------------|-----------------------------|
   |First step spec 3|"inside first step in spec 3"|

* Create a scenario "Scenario 41" in specification "Spec 4" with the following steps with implementation 

   |step text        |implementation               |
   |-----------------|-----------------------------|
   |First step spec 4|"inside first step in spec 4"|

* Create a scenario "Scenario 51" in specification "Spec 5" with the following steps with implementation 

   |step text        |implementation               |
   |-----------------|-----------------------------|
   |First step spec 5|"inside first step in spec 5"|

* Create a scenario "Scenario 61" in specification "Spec 6" with the following steps with implementation 

   |step text        |implementation               |
   |-----------------|-----------------------------|
   |First step spec 6|"inside first step in spec 6"|

Execute specs parallelly
------------------------

* Execute the current project in parallel and ensure success

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |6       |6     |0     |0      |
   |Scenarios      |6       |6     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |6         |6        |0        |0           |

Execute specs parallelly in n streams
-------------------------------------

* Execute the current project in parallel in "2" streams and ensure success
* Console should contain "Executing in 2 parallel streams"

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |6       |6     |0     |0      |
   |Scenarios      |6       |6     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |6         |6        |0        |0           |
