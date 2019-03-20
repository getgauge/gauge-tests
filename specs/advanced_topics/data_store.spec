Data Store
==========

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "data_store" without example spec

Scenario data store refreshes after every scenario run
------------------------------------------------------

* Create a specification "newSpec" with the following contexts 

   |step text    |implementation        |
   |-------------|----------------------|
   |First context|"inside first context"|

* Create a scenario "newScenario" in specification "newSpec" with steps to read and write to datastore 

   |step text                   |key       |value                         |datastore type|
   |----------------------------|----------|------------------------------|--------------|
   |Store in scenario datastore |gauge_test|Some temporary datastore value|Scenario      |
   |Read from scenario datastore|gauge_test|                              |Scenario      |

* Execute the current project and ensure success
* Console should contain "Some temporary datastore value"

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

Spec data store persists data between scenario runs
---------------------------------------------------

* Create a specification "newSpec" with the following contexts 

   |step text    |implementation        |
   |-------------|----------------------|
   |First context|"inside first context"|

* Add tags "hadjka" to specification "newspec"
* Create a scenario "writeScenario" in specification "newSpec" with step to write to datastore 

   |step text              |key       |value                         |datastore type|
   |-----------------------|----------|------------------------------|--------------|
   |Store in spec datastore|gauge_test|Some temporary datastore value|Spec          |

* Create a scenario "readScenario" in specification "newSpec" with step to read from datastore 

   |step text          |key       |value|datastore type|
   |-------------------|----------|-----|--------------|
   |Read spec datastore|gauge_test|     |Spec          |

* Execute the current project and ensure success
* Console should contain "Some temporary datastore value"

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |1         |1        |0        |0           |

Suite data store persists data between spec runs
------------------------------------------------

* Create a specification "first write spec" with the following contexts 

   |step text    |implementation        |
   |-------------|----------------------|
   |First context|"inside first context"|

* Create a scenario "write scenario" in specification "first write spec" with step to write to datastore 

   |step text               |key       |value                         |datastore type|
   |------------------------|----------|------------------------------|--------------|
   |Store in suite datastore|gauge_test|Some temporary datastore value|Suite         |

* Create a specification "second read spec" with the following contexts 

   |step text     |implementation         |
   |--------------|-----------------------|
   |Second context|"inside second context"|

* Create a scenario "read Scenario" in specification "second read spec" with step to read from datastore 

   |step text           |key       |value|datastore type|
   |--------------------|----------|-----|--------------|
   |Read suite datastore|gauge_test|     |Suite         |

* Execute the following specs in order and ensure success 

   |Spec name       |
   |----------------|
   |first write spec|
   |second read spec|

* Console should contain "Some temporary datastore value"

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |2       |2     |0     |0      |
   |Scenarios      |2       |2     |0     |0      |

* Console should contain "Successfully generated html-report to =>"

* verify statistics in html with 

   |totalCount|passCount|failCount|skippedCount|
   |----------|---------|---------|------------|
   |2         |2        |0        |0           |
