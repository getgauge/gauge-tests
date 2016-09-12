HTML Reports with basic specs
=============================

tags: spec

* In an empty directory initialize a project named "spec_with_passing_scenarios" without example spec

Spec passes when all scenarios pass
-----------------------------------

* Create "Sample scenario" in "Basic spec execution" with the following steps 

     |step text          |implementation     |
     |-------------------|-------------------|
     |First Scenario step|"inside first step"|

* Create a scenario "second scenario" in specification "Basic spec execution" with the following steps with implementation 

     |step text           |implementation      |
     |--------------------|--------------------|
     |Second Scenario step|"inside second step"|

* Create a scenario "third scenario" in specification "Basic spec execution" with the following steps with implementation 

     |step text          |implementation     |
     |-------------------|-------------------|
     |Third Scenario step|"inside third step"|

* Execute the current project and ensure success

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |1        |0        |0           |

Spec fails even if one scenario fails - irrespective of passing and skipped scenarios
-------------------------------------------------------------------------------------

* Create "Failing scenario" in "Basic spec execution" with the following steps 

     |step text  |implementation      |
     |-----------|--------------------|
     |First step |"inside first step" |
     |Second step|"inside second step"|
     |Third step |throw exception     |

* Create a scenario "skipped scenario" in specification "Basic spec execution" with the following steps unimplemented 

     |step text|
     |---------|
     |some step|

* Create a scenario "passing scenario" in specification "Basic spec execution" with the following steps with implementation 

     |step text          |implementation     |
     |-------------------|-------------------|
     |Third Scenario step|"inside third step"|

* Execute the current project and ensure failure

* verify statistics in html with 

     |totalCount|passCount|failCount|skippedCount|
     |----------|---------|---------|------------|
     |1         |1        |0        |0           |
