Datatable Driven Parallel Execution
===================================

tags: java, csharp, dotnet, ruby, python, js, parallel_data_table

* Initialize a project named "parallel_data_driven_spec" without example spec

* Create a specification "data table driven parallel execution" with the following datatable 

   |id|name   |phone|
   |--|-------|-----|
   |1 |vishnu |999  |
   |2 |prateek|007  |
   |3 |nava   |100  |

Execute specs parallelly for each datatable
-------------------------------------------
* Create a scenario "datatable scenario" in specification "data table driven parallel execution" with the following steps with implementation 

   |step text                |implementation|
   |-------------------------|--------------|
   |print <id> <name> <phone>|print params  |

* Execute the current project in parallel in "4" streams and ensure success

* Console should contain "Executing in 3 parallel streams"

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

Execute specs parallely in n streams
------------------------------------
* Create a scenario "datatable scenario" in specification "data table driven parallel execution" with the following steps with implementation 

   |step text                |implementation|
   |-------------------------|--------------|
   |print <id> <name> <phone>|print params  |

* Execute the current project in parallel in "2" streams and ensure success

* Console should contain "Executing in 2 parallel streams"

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

Execute specs sequentially if datatable row values not used
-----------------------------------------------------------
* Create a scenario "datatable scenario" in specification "data table driven parallel execution" with the following steps with implementation 

   |step text            |implementation|
   |---------------------|--------------|
   |This is the only step|print params  |

* Execute the current project in parallel in "4" streams and ensure success

* Console should not contain following lines 

   |console output                 |
   |-------------------------------|
   |Executing in 3 parallel streams|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

Datatable driven parallel execution should execute before spec hooks per row
----------------------------------------------------------------------------
* Create "spec" level "before" hook with implementation "inside before spec"

* Create "spec" level "after" hook with implementation "inside after spec"

* Create a scenario "datatable scenario" in specification "data table driven parallel execution" with the following steps with implementation 

   |step text                |implementation|
   |-------------------------|--------------|
   |print <id> <name> <phone>|print params  |

* Execute the current project in parallel in "4" streams and ensure success

* Console should contain "Executing in 3 parallel streams"

* Console should contain "inside before spec" "3" times

* Console should contain "inside after spec" "3" times

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |3       |3     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

Datatable driven parallel execution with hook failure
-----------------------------------------------------
* Create "spec" level "before" hook with exception

* Create a scenario "datatable scenario" in specification "data table driven parallel execution" with the following steps with implementation 

   |step text                |implementation|
   |-------------------------|--------------|
   |First step               |print params  |
   |print <id> <name> <phone>|print params  |

* Execute the current project in parallel in "4" streams and ensure failure

* Console should contain "Executing in 3 parallel streams"

* Console should not contain following lines 

   |console output|
   |--------------|
   |First step    |

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
