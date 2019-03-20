Spec Validation Errors occuring during execution
================================================

tags: validation, java, csharp, dotnet, ruby, python, js

* Initialize a project named "spec_exec_with_validation_err" without example spec

Spec execution with unimplemented step in scenarios
---------------------------------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text                |
   |-------------------------|
   |First unimplemented step |
   |Second unimplemented step|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps with implementation 

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|

* Execute the spec "Basic spec execution" and ensure success
* Console should contain following lines in order 

   |console output                                              |
   |------------------------------------------------------------|
   |Step implementation not found => 'First unimplemented step' |
   |Step implementation not found => 'Second unimplemented step'|
   |inside first step                                           |
   |inside second step                                          |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |1      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |1        |0        |0           |

Spec execution with unimplemented step in context step
------------------------------------------------------

* Create a specification "Basic spec execution" with the following unimplemented contexts 

   |step text    |
   |-------------|
   |First context|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps with implementation 

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|

* Execute the spec "Basic spec execution" and ensure success
* Console should contain following lines in order 

   |console output                                  |
   |------------------------------------------------|
   |Step implementation not found => 'First context'|

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

Spec execution with unimplemented step in context step and scenario
-------------------------------------------------------------------

* Create a specification "Basic spec execution" with the following unimplemented contexts 

   |step text    |
   |-------------|
   |First context|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps with implementation 

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text                |
   |-------------------------|
   |Second unimplemented step|

* Execute the spec "Basic spec execution" and ensure success
* Console should contain following lines in order 

   |console output                                              |
   |------------------------------------------------------------|
   |Step implementation not found => 'First context'            |
   |Step implementation not found => 'Second unimplemented step'|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |0       |0     |0     |1      |
   |Scenarios      |0       |0     |0     |2      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |0        |1           |

Spec with no heading
--------------------

* Create a scenario "Sample scenario" in specification "" with the following steps unimplemented 

   |step text                |
   |-------------------------|
   |First unimplemented step |
   |Second unimplemented step|

* Execute the spec "" and ensure failure
* Console should contain "Spec heading should have at least one character"

Scenario with no heading
------------------------

* Create a scenario "" in specification "Scenario with no heading" with the following steps unimplemented 

   |step text                |
   |-------------------------|
   |First unimplemented step |
   |Second unimplemented step|

* Execute the spec "Scenario with no heading" and ensure failure
* Console should contain "Scenario heading should have at least one character"

Skip spec if all scenarios are skipped
--------------------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text                |
   |-------------------------|
   |First unimplemented step |
   |Second unimplemented step|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps unimplemented 

   |step text                |
   |-------------------------|
   |Third unimplemented step |
   |Fourth unimplemented step|

* Execute the spec "Basic spec execution" and ensure success
* Console should contain following lines in order 

   |console output                                              |
   |------------------------------------------------------------|
   |Step implementation not found => 'First unimplemented step' |
   |Step implementation not found => 'Second unimplemented step'|
   |Step implementation not found => 'Third unimplemented step' |
   |Step implementation not found => 'Fourth unimplemented step'|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |0       |0     |0     |1      |
   |Scenarios      |0       |0     |0     |2      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |0        |1           |
