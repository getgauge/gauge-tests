Repeat last run
===============

tags: execution, rerun, java, csharp, dotnet, ruby, python, js

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

Repeat last run with rerun failed specs after correcting failure
----------------------------------------------------------------
* Create a scenario "Sample scenario" in specification "Basic_spec_execution" with the following steps with implementation

   |step text  |implementation     |
   |-----------|-------------------|
   |First step |"inside first step"|
   |Second step|throw exception    |

* Create a scenario "Sample scenario2" in specification "Basic_spec_execution2" with the following steps with implementation

   |step text  |implementation      |
   |-----------|--------------------|
   |Third step|"inside third step"|

* Execute the current project and ensure failure

* Statistics generated should have

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |2       |1     |1     |0      |
   |Scenarios      |2       |1     |1     |0      |

* verify statistics in html with

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |2         |1        |1        |0           |

* Rerun failed scenarios and ensure failure
* Console should contain following lines in order

   |console output    |
   |------------------|
   |inside first step |

* Statistics generated should have

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

* verify statistics in html with

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |1        |0           |

* Update scenario "Sample scenario" in specification "Basic_spec_execution" with the following steps unimplemented

   |step text  |
   |-----------|
   |First step |

* Repeat last run and ensure "SUCCESS"
* Console should contain following lines in order

   |console output    |
   |------------------|
   |inside first step |
* Statistics generated should have

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |

Repeat last run with log level debuging
---------------------------------------

* Create a scenario "Sample scenario" in specification "Basic_spec_execution" with the following steps with implementation

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|

* Create a scenario "Sample scenario1" in specification "Basic_spec_execution1" with the following steps with implementation

   |step text |implementation     |
   |----------|-------------------|
   |Third step|"inside third step"|

* Execute the current project and ensure success


* Statistics generated should have

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |2       |2     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* Repeat last run with log level debug and ensure success

* Statistics generated should have

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |2       |2     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain following lines in order

   |console output                          |
   |----------------------------------------|
   |Plugin html-report is already installed.|
   |inside first step                       |
   |inside second step                      |
   |inside third step                       |

Repeat last run with spec directory
-----------------------------------

* Create a scenario "Sample scenario" in specification "Basic_spec_execution" with the following steps with implementation

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|
* Execute the current project and ensure success

* Repeat last run with specific directory

* Console should contain "Invalid Command. Usage: gauge run --repeat"

Rerun specs with failed flags
-----------------------------

* Create a scenario "Sample scenario" in specification "Basic_spec_execution" with the following steps with implementation

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|

* Execute the current project and ensure success

* Execute current project with failed and repeat flags

* Console should contain "Invalid Command. Usage: gauge run --repeat"