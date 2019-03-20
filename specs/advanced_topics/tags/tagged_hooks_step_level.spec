Tagged hooks step level
=======================

tags: tagged_hooks, execution_hooks, java, csharp, dotnet, ruby, python, js

* Initialize a project named "tagged_hook_step" without example spec
* Create a scenario "First scenario" in specification "First Spec" with the following steps with implementation 

   |step text                            |implementation                         |
   |-------------------------------------|---------------------------------------|
   |first spec first scenario first step |"first spec first scenario first step" |
   |first spec first scenario second step|"first spec first scenario second step"|

* Create a scenario "First scenario" in specification "Second Spec" with the following steps with implementation 

   |step text                             |implementation                          |
   |--------------------------------------|----------------------------------------|
   |second spec first scenario first step |"second spec first scenario first step" |
   |second spec first scenario second step|"second spec first scenario second step"|

* Create a scenario "Second scenario" in specification "Second Spec" with the following steps with implementation 

   |step text                              |implementation                           |
   |---------------------------------------|-----------------------------------------|
   |second spec second scenario first step |"second spec second scenario first step" |
   |second spec second scenario second step|"second spec second scenario second step"|

* Create a scenario "First scenario" in specification "Third Spec" with the following steps with implementation 

   |step text                            |implementation                         |
   |-------------------------------------|---------------------------------------|
   |third spec first scenario first step |"third spec first scenario first step" |
   |third spec first scenario second step|"third spec first scenario second step"|

* Add tags "tag1,tag2,tag3" to specification "First Spec"
* Add tags "tag3,tag6,tag4" to specification "Second Spec"
* Add tags "tag2" to scenario "First scenario" in specification "Second Spec"
* Add tags "tag6,tag4,tag2,tag5" to specification "Third Spec"

AND aggregation of hooks
------------------------

* Create "step" level "before" hook with implementation "inside before step hook1" with "AND" aggregated tags 

   |tags|
   |----|
   |tag3|
   |tag6|
   |tag4|
   |tag2|

* Create "step" level "after" hook with implementation "inside after step hook" with "AND" aggregated tags 

   |tags|
   |----|
   |tag1|
   |tag3|
* Create "step" level "before" hook with implementation "inside before step hook2" with "AND" aggregated tags 

   |tags|
   |----|
   |tag5|

* Execute the current project and ensure success
* Console should contain following lines in order 

   |Console output                         |
   |---------------------------------------|
   |first spec first scenario first step   |
   |inside after step hook                 |
   |first spec first scenario second step  |
   |inside after step hook                 |
   |inside before step hook1               |
   |second spec first scenario first step  |
   |inside before step hook1               |
   |second spec first scenario second step |
   |second spec second scenario first step |
   |second spec second scenario second step|
   |inside before step hook2               |
   |third spec first scenario first step   |
   |inside before step hook2               |
   |third spec first scenario second step  |

* Console should contain "inside after step hook" "2" times
* Console should contain "inside before step hook1" "2" times
* Console should contain "inside before step hook2" "2" times

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |3       |3     |0     |0      |
   |Scenarios      |4       |4     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |3         |3        |0        |0           |

OR aggregation of hooks
-----------------------

* Create "step" level "before" hook with implementation "inside before step hook" with "OR" aggregated tags 

   |tags|
   |----|
   |tag5|
   |tag1|

* Create "step" level "after" hook with implementation "inside after step hook" with "OR" aggregated tags 

   |tags|
   |----|
   |tag3|

* Execute the current project and ensure success
* Console should contain following lines in order 

   |Console output                         |
   |---------------------------------------|
   |inside before step hook                |
   |first spec first scenario first step   |
   |inside after step hook                 |
   |inside before step hook                |
   |first spec first scenario second step  |
   |inside after step hook                 |
   |second spec first scenario first step  |
   |inside after step hook                 |
   |second spec first scenario second step |
   |inside after step hook                 |
   |second spec second scenario first step |
   |inside after step hook                 |
   |second spec second scenario second step|
   |inside after step hook                 |
   |inside before step hook                |
   |third spec first scenario first step   |
   |inside before step hook                |
   |third spec first scenario second step  |

* Console should contain "inside before step hook" "4" times
* Console should contain "inside after step hook" "6" times

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |3       |3     |0     |0      |
   |Scenarios      |4       |4     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |3         |3        |0        |0           |
