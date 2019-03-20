Datatable row range execution
=============================

tags: Data-table, execution, java, csharp, dotnet, ruby, python, js

* Initialize a project named "datatable_row_exec" without example spec
* Create a specification "row range data table execution" with the following datatable 

   |id|name   |phone|
   |--|-------|-----|
   |1 |vishnu |999  |
   |2 |prateek|007  |
   |3 |nava   |100  |

Datatable execution with row number
-----------------------------------

* Create a scenario "datatable scenario" in specification "row range data table execution" with the following steps with implementation 

   |step text                |implementation|
   |-------------------------|--------------|
   |print <id> <name> <phone>|print params  |

* Execute the spec "row range data table execution" with row range "2" and ensure success
* Console should contain following lines in order 

   |console output                    |
   |----------------------------------|
   |param0=2,param1=prateek,param2=007|

* Console should not contain following lines 

   |console output                   |
   |---------------------------------|
   |param0=1,param1=vishnu,param2=999|
   |param0=3,param1=nava,param2=100  |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

Datatable execution with row range
----------------------------------

* Create a scenario "datatable scenario" in specification "row range data table execution" with the following steps with implementation 

   |step text                |implementation|
   |-------------------------|--------------|
   |print <id> <name> <phone>|print params  |

* Execute the spec "row range data table execution" with row range "2-3" and ensure success
* Console should contain following lines in order 

   |console output                    |
   |----------------------------------|
   |param0=2,param1=prateek,param2=007|
   |param0=3,param1=nava,param2=100   |

* Console should not contain following lines 

   |console output                   |
   |---------------------------------|
   |param0=1,param1=vishnu,param2=999|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain "Successfully generated html-report to =>"