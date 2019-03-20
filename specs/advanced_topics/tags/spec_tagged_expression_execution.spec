Spec Tagged Expression Execution
================================

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "spec_tagged_expression_exec" without example spec
* Add tags "tag1" to specification "tags_expression_execution"
* Create a scenario "second scenario" in specification "tags_expression_execution" with the following steps with implementation 

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |Second Scenario step|"inside second scenario step"|

* Add tags "tag1, tag2" to scenario "second scenario" in specification "tags_expression_execution"
* Create a scenario "third scenario" in specification "tags_expression_execution" with the following steps with implementation 

   |step text          |implementation              |
   |-------------------|----------------------------|
   |Third Scenario step|"inside third scenario step"|

* Add tags "tag3, tag4" to scenario "third scenario" in specification "tags_expression_execution"
* Create a scenario "fourth scenario" in specification "tags_expression_execution" with the following steps with implementation 

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |fourth Scenario step|"inside fourth scenario step"|

* Add tags "tag3, tag2" to scenario "fourth scenario" in specification "tags_expression_execution"

Tagged expression
-----------------

* Execute the tags "!tag1" in spec "tags_expression_execution" and ensure success
* Console should not contain following lines 

   |console output             |
   |---------------------------|
   |inside second scenario step|
   |inside third scenario step |
   |inside fourth scenario step|

* Execute the tags "tag1 && tag2" in spec "tags_expression_execution" and ensure success
* Console should contain following lines in order 

   |console output             |
   |---------------------------|
   |inside second scenario step|
   |inside fourth scenario step|

* Console should not contain following lines 

   |console output            |
   |--------------------------|
   |inside third scenario step|

* Execute the tags "tag1 && (tag2|tag4)" in spec "tags_expression_execution" and ensure success
* Console should contain following lines in order 

   |console output             |
   |---------------------------|
   |inside second scenario step|
   |inside third scenario step |
   |inside fourth scenario step|

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
