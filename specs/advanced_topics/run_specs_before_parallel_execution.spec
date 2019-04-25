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

* Add tags "tagOne" to specification "specification six"
* Create a scenario "six scenario" in specification "specification six" with the following steps with implementation

   |step text          |implementation              |
   |-------------------|----------------------------|
   |sixth Scenario step|"inside six scenario step"|

## Execute specs before parallel execution

* Execute specs with tags "parallelizable" in "2" parallel streams and other specs serially

* Console should contain "Executing 2 specs in serial."

* Console should contain "Executing in 2 parallel streams."

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

* Execute specs with tags "tagOne | non parallelizable" in "1" parallel streams and other specs serially

* Console should contain "Executing 4 specs in serial."

* Console should contain "Executing in 1 parallel streams."

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