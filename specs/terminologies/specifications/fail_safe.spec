Spec run with fail safe mode
============================

tags: spec, java, csharp, dotnet, ruby, python, js

* Initialize a project named "fail_safe_scenarios" without example spec

Spec run using fail safe does not fail if irrespective of the passing and skipped scenarios in it
--------------------------------------------------------------------------------------------------

* Create a scenario "Sample scenario" in specification "Basic spec execution" with the following steps with implementation

   |step text  |implementation      |
   |-----------|--------------------|
   |First step |"inside first step" |
   |Second step|"inside second step"|
   |Third step |throw exception     |

* Create a scenario "second scenario" in specification "Basic spec execution" with the following steps unimplemented

   |step text|
   |---------|
   |some step|

* Create a scenario "third scenario" in specification "Basic spec execution" with the following steps with implementation

   |step text          |implementation     |
   |-------------------|-------------------|
   |Third Scenario step|"inside third step"|

* Execute the current project and ensure failure
* Execute the current project in fail-safe mode and ensure success
