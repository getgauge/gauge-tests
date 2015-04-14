Tagged Execution
================

* In an empty directory initialize a project with the current language

Passing tagged execution
------------------------

* Create a scenario "first scenario" in specification "tags_execution" with the following steps with implementation
     |step text          |implementation              |
     |-------------------|----------------------------|
     |First Scenario step|"inside first scenario step"|
* Add tags "tag1" to scenario "first scenario" in specification "tags_execution"

* Create a scenario "second scenario" in specification "tags_execution" with the following steps with implementation
     |step text           |implementation               |
     |--------------------|-----------------------------|
     |Second Scenario step|"inside second scenario step"|
* Add tags "tag1, tag2" to scenario "second scenario" in specification "tags_execution"

* Create a scenario "third scenario" in specification "tags_execution" with the following steps with implementation
     |step text          |implementation              |
     |-------------------|----------------------------|
     |Third Scenario step|"inside third scenario step"|
* Add tags "tag3, tag4" to scenario "second scenario" in specification "tags_execution"

* Execute the tags "tag1, tag2" in spec "tags_execution" and ensure success
* Console should contain following lines in order 
     |console output             |
     |---------------------------|
     |inside second scenario step|
* Console should not contain following lines 
     |console output              |
     |----------------------------|
     |"inside first scenario step"|
     |"inside third scenario step"|

* Execute the tags "tag3, tag4" in spec "tags_execution" and ensure success
* Console should contain following lines in order 
     |console output            |
     |--------------------------|
     |inside third scenario step|
* Console should not contain following lines 
     |console output               |
     |-----------------------------|
     |"inside first scenario step" |
     |"inside second scenario step"|
