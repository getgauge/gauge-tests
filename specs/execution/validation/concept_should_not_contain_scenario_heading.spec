Concept should not contain scenario heading
===========================================

Concept should not contain scenario heading
-------------------------------------------

* In an empty directory initialize a project named "concept_should_not_contain_scenario_heading" with the current language
* Create concept "concept with scenario heading" with following steps 

     |concept steps      |Impl   |Type   |
     |-------------------|-------|-------|
     |Step1              |step   |step   |
     |Step2              |step   |step   |
     |## Scenario Heading|comment|comment|
     |Scenario step 1    |step   |step   |

* Execute the current project and ensure failure
* Console should contain "Scenario Heading is not allowed in concept file"
