Scenario Tagged Expression In Subfolder Execution
=================================================

Scenario Tagged Expression In Subfolder Execution
-------------------------------------------------
* In an empty directory initialize a project named "scen_tagged_sub_folder_expression_exec" with the current language

* Create concept "Sample concept" with following steps 
     |step text       |implementation              |
     |----------------|----------------------------|
     |Concept step one|"inside first concept step" |
     |Concept step two|"inside second concept step"|

* Create "Concept Testing" in "Spec 1" within sub folder "subFolder" with the following steps 
     |scenario steps      |
     |--------------------|
     |Sample concept      |

* Execute the spec folder "specs/subfolder/" and ensure success
