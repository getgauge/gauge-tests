Repeat last run
===============

tags: execution, rerun, java, csharp, ruby, python, js

* Initialize a project named "spec_exec" without example spec

Repeat last run with a failure
------------------------------
* Create a scenario "Sample scenario" in specification "Basic_spec_execution" with the following steps with implementation

   |step text  |implementation     |
   |-----------|-------------------|
   |First step |"inside first step"|
   |Second step|throw exception    |
   |Third step |"inside third step"|

* Create a scenario "Sample scenario1" in specification "Basic_spec_execution1" with the following steps with implementation 

   |step text  |implementation      |
   |-----------|--------------------|
   |Fourth step|"inside fourth step"|

* Create a scenario "Sample scenario2" in specification "Basic_spec_execution2" with the following steps with implementation 

   |step text |implementation     |
   |----------|-------------------|
   |Fifth step|"inside fifth step"|

* Execute the current project and ensure failure

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |3       |2     |1     |0      |
   |Scenarios      |3       |2     |1     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |3         |2        |1        |0           |

* Repeat last run and ensure "FAILURE"
* Console should contain following lines in order 

   |console output    |
   |------------------|
   |inside first step |
   |inside fourth step|
   |inside fifth step |

* Console should not contain following lines 

   |console output   |
   |-----------------|
   |inside third step|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |3       |2     |1     |0      |
   |Scenarios      |3       |2     |1     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |3         |2        |1        |0           |

Repeat last successful run
---------------------------
* Create a scenario "Sample scenario" in specification "Basic_spec_execution" with the following steps with implementation

   |step text  |implementation     |
   |-----------|-------------------|
   |First step |"inside first step"|
   |Third step |"inside third step"|

* Create a scenario "Sample scenario1" in specification "Basic_spec_execution1" with the following steps with implementation

   |step text  |implementation      |
   |-----------|--------------------|
   |Fourth step|"inside fourth step"|

* Create a scenario "Sample scenario2" in specification "Basic_spec_execution2" with the following steps with implementation

   |step text |implementation     |
   |----------|-------------------|
   |Fifth step|"inside fifth step"|

* Execute the current project and ensure success

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

* Repeat last run and ensure "SUCCESS"
* Console should contain following lines in order

   |console output    |
   |------------------|
   |inside first step |
   |inside third step|
   |inside fourth step|
   |inside fifth step |

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