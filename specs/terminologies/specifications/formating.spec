Spec Formatting
===============

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "formatting" without example spec

Formatting should not remove steps, scenarios from spec
-------------------------------------------------------

* Create a scenario "formatting should not remove steps scenarios from spec" in specification "spec formatting" with the following steps with implementation 

   |step text                         |implementation         |
   |----------------------------------|-----------------------|
   |Step that takes a table1 <dynamic>|"inside first scenario"|
   |special param <file:abc>          |"another step"         |
   |special param <table:abc>         |                       |

* Create a scenario "another scenario" in specification "spec formatting" with the following steps unimplemented 

   |step text                |
   |-------------------------|
   |special param <file:abc> |
   |special param <table:abc>|

* Check for validation errors in the project and ensure failure

* Format specs and ensure failure

* Console should contain "Dynamic parameter <dynamic> could not be resolved => 'Step that takes a table1 <dynamic>'"

* Console should contain " Dynamic parameter <file:abc> could not be resolved => 'special param <file:abc>'"

* Console should contain " Dynamic parameter <table:abc> could not be resolved => 'special param <table:abc>'"

* Spec "spec formatting" should not be formatted

Formatting merges scenario with same heading
--------------------------------------------

* Create a scenario "formatting merges scenario with same heading" in specification "spec formatting" with the following steps with implementation 

   |step text  |implementation   |
   |-----------|-----------------|
   |"something"|"inside somthing"|

* Create a scenario "in between" in specification "spec formatting" with the following steps with implementation 

   |step text                            |implementation  |
   |-------------------------------------|----------------|
   |"step between two duplicate scenario"|"inside between"|

* Create a scenario "formatting merges scenario with same heading" in specification "spec formatting" with the following steps with implementation 

   |step text  |implementation   |
   |-----------|-----------------|
   |"something"|"inside somthing"|

* Check for validation errors in the project and ensure failure

* Format specs and ensure failure

* Console should contain "Duplicate scenario definition"

* Spec "spec formatting" should not be formatted
