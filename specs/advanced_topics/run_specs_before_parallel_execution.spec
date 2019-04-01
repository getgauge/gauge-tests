# Run specs before parallel execution

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "specs_before_tagged_exec" without example spec
* Add tags "parallelizable" to specification "specification one"
* Create a scenario "one scenario" in specification "specification one" with the following steps with implementation 

   |step text          |implementation              |
   |-------------------|----------------------------|
   |first Scenario step|"inside first scenario step"|

* Add tags "parallelizable" to specification "specification two"
* Create a scenario "two scenario" in specification "specification two" with the following steps with implementation 

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |second Scenario step|"inside second scenario step"|

* Add tags "parallelizable" to specification "specification three"
* Create a scenario "three scenario" in specification "specification three" with the following steps with implementation 

   |step text          |implementation              |
   |-------------------|----------------------------|
   |third Scenario step|"inside third scenario step"|

* Add tags "parallelizable" to specification "specification four"
* Create a scenario "four scenario" in specification "specification four" with the following steps with implementation 

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |fourth Scenario step|"inside fourth scenario step"|

* Add tags "non parallelizable" to specification "specification five"
* Create a scenario "five scenario" in specification "specification five" with the following steps with implementation 

   |step text          |implementation              |
   |-------------------|----------------------------|
   |fifth Scenario step|"inside fifth scenario step"|



## Execute specs before parallel execution

* Execute specs with tags "parallelizable" in "2" parallel streams and other specs serially

* Console should contain "Executing 1 specs in serial."

* Console should contain "Executing in 2 parallel streams."

* Statistics generated should have

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |5       |5     |0     |0      |
   |Scenarios      |5       |5     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |5         |5        |0        |0           |
