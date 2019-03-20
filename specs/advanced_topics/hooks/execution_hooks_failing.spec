# Execution Hooks Failure

tags: java, csharp, dotnet, ruby, python, js

When a before execution hook fails the after hook will still execute skipping all other executions at that level.

* Initialize a project named "exec_hooks_fail" without example spec
* Create a scenario "New scenario" in specification "01 Hooks Spec" with the following steps with implementation 

   |step text  |implementation |
   |-----------|---------------|
   |First step |"inside step 1"|
   |Second step|"inside step 2"|

## Hook failure at suite level

* Create "suite" level "before" hook with exception
* Create "scenario" level "before" hook with implementation "inside before scenario"
* Create "suite" level "after" hook with implementation "inside after suite"
* Execute the spec "01 Hooks Spec" and ensure failure
* Console should not contain following lines 

   |console output        |
   |----------------------|
   |inside before scenario|
   |inside step 1         |
   |inside step 2         |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |0       |0     |0     |0      |
   |Scenarios      |0       |0     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |0         |0        |0        |0           |

## Hook failure at spec level

* Create "suite" level "before" hook with implementation "inside before suite"
* Create "spec" level "before" hook with exception
* Create "spec" level "after" hook with implementation "inside after spec"
* Create "suite" level "after" hook with implementation "inside after suite"
* Execute the spec "01 Hooks Spec" and ensure failure
* Console should contain following lines in order 

   |console output     |
   |-------------------|
   |inside before suite|
   |inside after spec  |
   |inside after suite |

* Console should not contain following lines 

   |console output|
   |--------------|
   |inside step 1 |
   |inside step 2 |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |0       |0     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |1        |0           |

## Hook failure at scenario level

* Create "scenario" level "before" hook with exception
* Create "scenario" level "after" hook with implementation "inside after scenario"
* Execute the spec "01 Hooks Spec" and ensure failure
* Console should contain following lines in order 

   |console output       |
   |---------------------|
   |inside after scenario|

* Console should not contain following lines 

   |console output|
   |--------------|
   |inside step 1 |
   |inside step 2 |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |1        |0           |

* Generated html report should have screenshot in spec "01 Hooks Spec" for element "SCENARIO"

   |Element Name|Type     |Error In|
   |------------|---------|--------|
   |New scenario|scenario |before  |
   |New scenario|scenario |after   |

## Hook failure at step level

* Create "step" level "before" hook with exception
* Create "step" level "after" hook with implementation "inside after step"
* Execute the spec "01 Hooks Spec" and ensure failure
* Console should contain following lines in order 

   |console output   |
   |-----------------|
   |inside after step|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |0     |1     |0      |
   |Scenarios      |1       |0     |1     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |0        |1        |0           |