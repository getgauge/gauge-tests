Spec Validation Errors occuring during execution
================================================

tags: validation, java

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

   |console output                                                                          |
   |----------------------------------------------------------------------------------------|
   |Step implementation not found => 'First unimplemented step'                             |
   |Step implementation not found => 'Second unimplemented step'                            |
   |Add the following missing implementations to fix `Step implementation not found` errors.|
   |@Step("First unimplemented step")                                                       |
   |public void firstUnimplementedStep(){                                                   |
   |}                                                                                       |
   |@Step("Second unimplemented step")                                                      |
   |public void secondUnimplementedStep(){                                                  |
   |}                                                                                       |
   |inside first step                                                                       |
   |inside second step                                                                      |

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

   |console output                                                                          |
   |----------------------------------------------------------------------------------------|
   |Step implementation not found => 'First context'                                        |
   |Add the following missing implementations to fix `Step implementation not found` errors.|
   |@Step("First context")                                                                  |
   |public void firstContext(){                                                             |
   |}                                                                                       |


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

Spec execution with unimplemented step in context step, scenario and tear down
------------------------------------------------------------------------------

* Create a specification "Basic spec execution" with the following unimplemented contexts 

   |step text                       |
   |--------------------------------|
   |First unimplemented context step|

* Create a scenario "Sample scenario2" in specification "Basic spec execution" with the following steps with implementation 

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps unimplemented 

   |step text                     |
   |------------------------------|
   |An unimplemented scenario step|

* Add the following unimplemented teardown steps in specification "Basic spec execution" 

   |step text                   |
   |----------------------------|
   |tear down unimplemented step|


* Execute the spec "Basic spec execution" and ensure success
* Console should contain following lines in order 

   |console output                                                                          |
   |----------------------------------------------------------------------------------------|
   |Step implementation not found => 'First unimplemented context step'                     |
   |Step implementation not found => 'An unimplemented scenario step'                       |
   |Step implementation not found => 'tear down unimplemented step'                         |
   |Add the following missing implementations to fix `Step implementation not found` errors.|
   |@Step("First unimplemented context step")                                               |
   |public void firstUnimplementedContextStep(){                                            |
   |}                                                                                       |
   |@Step("An unimplemented scenario step")                                                 |
   |public void anUnimplementedScenarioStep(){                                              |
   |}                                                                                       |
   |@Step("tear down unimplemented step")                                                   |
   |public void tearDownUnimplementedStep(){                                                |
   |}                                                                                       |

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

