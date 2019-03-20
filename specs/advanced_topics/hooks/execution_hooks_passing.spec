Execution Hooks Success
=======================

tags: java, csharp, dotnet, ruby, python, js

Execution hooks can be used to run arbitrary test code as different levels during the test suite execution. They are available at suite,
spec, scenario and step level.

* Initialize a project named "exec_hooks_pass" without example spec
* Create a scenario "Testing Hooks scenario 1" in specification "01 Hooks Spec" with the following steps with implementation 

   |step text          |implementation     |
   |-------------------|-------------------|
   |First scenario step|"inside first step"|

* Create a scenario "Testing Hooks scenario 2" in specification "01 Hooks Spec" with the following steps with implementation 

   |step text           |implementation      |
   |--------------------|--------------------|
   |Second scenario step|"inside second step"|

* Create a scenario "Testing Hooks scenario new" in specification "02 Hooks Spec" with the following steps with implementation 

   |step text              |implementation         |
   |-----------------------|-----------------------|
   |First scenario new step|"inside new first step"|

Suite and Spec Level Hooks
--------------------------

* Create "suite" level hooks with implementations "inside before suite hook" for before and "inside after suite hook" for after hook
* Create "spec" level hooks with implementations "inside before spec hook" for before and "inside after spec hook" for after hook
* Execute the specs in order and ensure success
* Console should contain following lines in order 

   |console output          |
   |------------------------|
   |inside before suite hook|
   |inside before spec hook |
   |inside first step       |
   |inside second step      |
   |inside after spec hook  |
   |inside before spec hook |
   |inside new first step   |
   |inside after spec hook  |
   |inside after suite hook |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |2       |2     |0     |0      |
   |Scenarios      |3       |3     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |2         |2        |0        |0           |

Scenario and step level hooks
-----------------------------

* Create "scenario" level hooks with implementations "inside before scenario hook" for before and "inside after scenario hook" for after hook
* Create "step" level hooks with implementations "inside before step hook" for before and "inside after step hook" for after hook
* Execute the spec "01 Hooks Spec" and ensure success
* Console should contain following lines in order 

   |console output             |
   |---------------------------|
   |inside before scenario hook|
   |inside before step hook    |
   |inside first step          |
   |inside after step hook     |
   |inside after scenario hook |
   |inside before scenario hook|
   |inside before step hook    |
   |inside second step         |
   |inside after step hook     |
   |inside after scenario hook |

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

All level Execution hooks
-------------------------

* Create "spec" level hooks with implementations "inside before spec hook" for before and "inside after spec hook" for after hook
* Create "suite" level hooks with implementations "inside before suite hook" for before and "inside after suite hook" for after hook
* Create "scenario" level hooks with implementations "inside before scenario hook" for before and "inside after scenario hook" for after hook
* Create "step" level hooks with implementations "inside before step hook" for before and "inside after step hook" for after hook
* Execute the spec "02 Hooks Spec" and ensure success
* Console should contain following lines in order 

   |console output             |
   |---------------------------|
   |inside before suite hook   |
   |inside before spec hook    |
   |inside before scenario hook|
   |inside before step hook    |
   |inside after step hook     |
   |inside after scenario hook |
   |inside after spec hook     |
   |inside after suite hook    |

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |1        |0        |0           |
