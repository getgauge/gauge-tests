Parallel Execution
==================

* In an empty directory initialize a project named "parallel_exec" with the current language
* Create "Scenario 11" in "Spec 1" with the following steps 
     |step text |implementation               |
     |----------|-----------------------------|
     |First step|"inside first step in spec 1"|
* Create "Scenario 21" in "Spec 2" with the following steps 
     |step text |implementation               |
     |----------|-----------------------------|
     |First step|"inside first step in spec 2"|
* Create "Scenario 31" in "Spec 3" with the following steps 
     |step text |implementation               |
     |----------|-----------------------------|
     |First step|"inside first step in spec 3"|
* Create "Scenario 41" in "Spec 4" with the following steps 
     |step text |implementation               |
     |----------|-----------------------------|
     |First step|"inside first step in spec 4"|
* Create "Scenario 51" in "Spec 5" with the following steps 
     |step text |implementation               |
     |----------|-----------------------------|
     |First step|"inside first step in spec 5"|
* Create "Scenario 61" in "Spec 6" with the following steps 
     |step text |implementation               |
     |----------|-----------------------------|
     |First step|"inside first step in spec 6"|

Execute specs successfully
--------------------------
* Execute the current project in parallel and ensure success

Execute specs successfully in n streams
---------------------------------------
* Execute the current project in parallel in "2" streams and ensure success
* Console should contain "Executing in 2 parallel streams"
