Simple parameter
================

tags: parameter, java, csharp, dotnet, ruby, python, js

* Initialize a project named "scenarios_with_simple_parameter" without example spec

Inline simple parameters
------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation 

   |step text                 |implementation                                          |
   |--------------------------|--------------------------------------------------------|
   |"2" "params" used in steps|"inside step with parameters : " + param0 + " " + param1|
   |Step with "two" "params"  |"inside step with parameters : " + param0 + " " + param1|

* Execute the current project and ensure success

* Console should contain following lines in order 

   |console output                          |
   |----------------------------------------|
   |inside step with parameters : 2 params  |
   |inside step with parameters : two params|

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
