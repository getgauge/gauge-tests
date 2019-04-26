Add abillity to retry scenario
==============================
tags: java

* Initialize a project named "retry_scenaio" without example spec
* Create a scenario "failing scenario" in specification "retry_on_failure_spec" with the following steps with implementation

   |step text            |implementation              |
   |---------------------|----------------------------|
   |Failing Scenario step|  failing implementation    |

* Add tags "tag1" to scenario "failing scenario" in specification "retry_on_failure_spec"
* Create a scenario "passing scenario" in specification "retry_on_failure_spec" with the following steps with implementation

   |step text             |implementation               |
   |----------------------|-----------------------------|
   |Passing Scenario step |"inside passing scenario step"|
* Add tags "tag2" to scenario "passing scenario" in specification "retry_on_failure_spec"

* Create a scenario "third test scenario" in specification "retry_on_failure_spec" with the following steps with implementation

   |step text             |implementation               |
   |----------------------|-----------------------------|
   |third Scenario step |"inside third test scenario step"|
* Add tags "tag3" to scenario "third test scenario" in specification "retry_on_failure_spec"


Should retry failing scenario
---------------------------------

* Execute spec "retry_on_failure_spec" with following flags ensure failure
    |flag|values|
    |max-retries-count|3|
    |retry-only|tag1|

* Console should contain following output for "3" times

   |console output             |
   |---------------------------|
   |Failed Step: Failing Scenario step|

* Execute spec "retry_on_failure_spec" with following flags ensure failure
    |flag|values|
    |max-retries-count|3|
    |retry-only|tag1\|tag2|

* Console should contain following output for "3" times

   |console output             |
   |---------------------------|
   |Failed Step: Failing Scenario step|

* Execute spec "retry_on_failure_spec" with following flags ensure failure
    |flag|values|
    |max-retries-count|3|
    |retry-only|tag3|

* Console should contain following output for "1" times

   |console output             |
   |---------------------------|
   |inside third test scenario step|