Tagged hooks scenario level
===========================

tags: tagged_hooks, execution_hooks, java, csharp, dotnet, ruby, python, js

* Initialize a project named "tagged_hook_scen" without example spec
* Create a scenario "First scenario" in specification "First Spec" with the following steps with implementation 

   |step text                            |implementation                         |
   |-------------------------------------|---------------------------------------|
   |first spec first scenario first step |"first spec first scenario first step" |
   |first spec first scenario second step|"first spec first scenario second step"|

* Create a scenario "Second scenario" in specification "First Spec" with the following steps with implementation 

   |step text                             |implementation                          |
   |--------------------------------------|----------------------------------------|
   |first spec second scenario first step |"first spec second scenario first step" |
   |first spec second scenario second step|"first spec second scenario second step"|

* Create a scenario "First scenario" in specification "Second Spec" with the following steps with implementation 

   |step text                             |implementation                          |
   |--------------------------------------|----------------------------------------|
   |second spec first scenario first step |"second spec first scenario first step" |
   |second spec first scenario second step|"second spec first scenario second step"|

* Create a scenario "First scenario" in specification "Third Spec" with the following steps with implementation 

   |step text                            |implementation                         |
   |-------------------------------------|---------------------------------------|
   |third spec first scenario first step |"third spec first scenario first step" |
   |third spec first scenario second step|"third spec first scenario second step"|

* Add tags "tag1,tag2,tag3" to specification "First Spec"
* Add tags "tag4" to scenario "First scenario" in specification "First Spec"
* Add tags "tag3,tag6,tag4" to specification "Second Spec"
* Add tags "tag6,tag4,tag2" to specification "Third Spec"

AND aggregation of hooks
------------------------

* Create "scenario" level "before" hook with implementation "inside before scenario hook" with "AND" aggregated tags 

   |tags|
   |----|
   |tag1|
   |tag2|
   |tag4|

* Create "scenario" level "after" hook with implementation "inside after scenario hook1" with "AND" aggregated tags 

   |tags|
   |----|
   |tag1|
   |tag2|

* Create "scenario" level "after" hook with implementation "inside after scenario hook2" with "AND" aggregated tags 

   |tags|
   |----|
   |tag6|
   |tag4|

* Execute the current project and ensure success
* Console should contain following lines in order 

   |Console output                        |
   |--------------------------------------|
   |inside before scenario hook           |
   |first spec first scenario first step  |
   |first spec first scenario second step |
   |inside after scenario hook1           |
   |first spec second scenario first step |
   |first spec second scenario second step|
   |inside after scenario hook1           |
   |second spec first scenario first step |
   |second spec first scenario second step|
   |inside after scenario hook2           |
   |third spec first scenario first step  |
   |third spec first scenario second step |
   |inside after scenario hook2           |

* Console should contain "inside before scenario hook" "1" times
* Console should contain "inside after scenario hook1" "2" times
* Console should contain "inside after scenario hook2" "2" times

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

* Create "scenario" level "before" hook with implementation "inside before scenario hook" with "OR" aggregated tags 

   |tags|
   |----|
   |tag6|
   |tag4|

* Create "scenario" level "after" hook with implementation "inside after scenario hook" with "OR" aggregated tags 

   |tags|
   |----|
   |tag6|

* Execute the current project and ensure success
* Console should contain following lines in order 

   |Console output                        |
   |--------------------------------------|
   |inside before scenario hook           |
   |first spec first scenario first step  |
   |first spec first scenario second step |
   |first spec second scenario first step |
   |first spec second scenario second step|
   |inside before scenario hook           |
   |second spec first scenario first step |
   |second spec first scenario second step|
   |inside after scenario hook            |
   |inside before scenario hook           |
   |third spec first scenario first step  |
   |third spec first scenario second step |
   |inside after scenario hook            |

* Console should contain "inside before scenario hook" "3" times
* Console should contain "inside after scenario hook" "2" times

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
