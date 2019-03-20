Context Execution
=================

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "context_exec" without example spec

basic context execution
-----------------------

* Create a specification "basic context execution" with the following contexts 

   |step text     |implementation         |
   |--------------|-----------------------|
   |First context |"inside first context" |
   |Second context|"inside second context"|

* Create a scenario "first scenario" in specification "basic context execution" with the following steps with implementation 

   |step text          |implementation              |
   |-------------------|----------------------------|
   |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "basic context execution" with the following steps with implementation 

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |Second Scenario step|"inside second scenario step"|

* Execute the spec "basic context execution" and ensure success
* Console should contain following lines in order 

   |console output             |
   |---------------------------|
   |inside first context       |
   |inside second context      |
   |inside first scenario step |
   |inside first context       |
   |inside second context      |
   |inside second scenario step|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |1        |0        |0           |

Normal context step - with no cof fails then tear down steps are expected to execute
------------------------------------------------------------------------------------
* Create a specification "basic context execution" with the following contexts 

   |step text     |implementation        |
   |--------------|----------------------|
   |First context |"inside first context"|
   |Second context|throw exception       |

* Create a scenario "first scenario" in specification "basic context execution" with the following steps with implementation 

   |step text          |implementation              |
   |-------------------|----------------------------|
   |First Scenario step|"inside first scenario step"|

* Create a scenario "second scenario" in specification "basic context execution" with the following steps with implementation 

   |step text           |implementation               |
   |--------------------|-----------------------------|
   |Second Scenario step|"inside second scenario step"|

* Add the following teardown steps in specification "basic context execution" 

   |step text   |implementation       |
   |------------|---------------------|
   |Normal step3|"inside normal step3"|
   |Normal step4|"inside normal step4"|

* Execute the spec "basic context execution" and ensure failure
* Console should contain following lines in order 

   |console output             |
   |---------------------------|
   |inside first context       |
   |Failed Step: Second context|
   |inside normal step3        |
   |inside normal step4        |

* Console should not contain following lines 

   |console output               |
   |-----------------------------|
   |"inside first scenario step" |
   |"inside second scenario step"|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |2       |0     |2     |0      |

* Console should contain "Successfully generated html-report to =>"