Execution Hooks Failing
=======================

When a before execution hook fails the after hook will still execute skipping all other executions at that level.

* In an empty directory initialize a project named "exec_hooks_fail" with the current language
* Create "New scenario" in "01 Hooks Spec" with the following steps 

     |step text  |implementation |
     |-----------|---------------|
     |First step |"inside step 1"|
     |Second step|"inside step 2"|

Test a hook failure at suite level
----------------------------------

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

Test a hook failure at spec level
---------------------------------

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

Test a hook failure at scenario level
-------------------------------------

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

Test a hook failure at step level
---------------------------------

* Create "step" level "before" hook with exception
* Create "step" level "after" hook with implementation "inside after step"
* Execute the spec "01 Hooks Spec" and ensure failure
* Console should contain following lines in order 

     |console output   |
     |-----------------|
     |inside after step|
