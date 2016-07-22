concepts with no parameters
===========================
* In an empty directory initialize a project named "single_scen_exec" with the current language

Should be able to invoke a concept with no parameters
-----------------------------------------------------

* Create concept "Sample concept with no parameters" with following steps 

     |step text       |implementation              |
     |----------------|----------------------------|
     |Concept step one|"inside first concept step" |
     |Concept step two|"inside second concept step"|

* Create "Scenario for concept execution" in "Concept execution" with the following steps 

     |step text                        |implementation      |
     |---------------------------------|--------------------|
     |Sample concept with no parameters|                    |
     |Fourth step                      |"inside fourth step"|

* Execute the spec "Concept execution" and ensure success
* Console should contain following lines in order 

     |console output            |
     |--------------------------|
     |inside first concept step |
     |inside second concept step|
     |inside fourth step        |
