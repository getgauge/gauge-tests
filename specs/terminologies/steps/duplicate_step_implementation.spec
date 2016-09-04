Duplicate step implementation
=============================

tags: step

Duplicate step implementation should atleast give a warning
-----------------------------------------------------------

* In an empty directory initialize a project named "duplicate_step_implementation" with the current language

* Create a scenario "scenaro1" in specification "duplicate step implementation" with the following steps with implementation 

     |step text       |implementation              |
     |----------------|----------------------------|
     |Concept step one|"inside first concept step" |
     |Concept step one|"inside second concept step"|

* Execute the spec "duplicate step implementation" and ensure failure

* Console should contain "Duplicate step implementation"

* Statics generated should have

     |Statistics name|executed|passed|failed|skipped|
     |---------------|--------|------|------|-------|
     |Specifications |0       |0     |0     |1      |
     |Scenarios      |0       |0     |0     |1      |
