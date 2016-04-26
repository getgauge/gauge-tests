Should allow to refactor parameter in tear down
===============================================

tags: refactoring, unimplemented

* In an empty directory initialize a project named "should_allow_to_refactor_parameter_in_tear_down" with the current language

* Create a scenario "scenario should_allow_to_refactor_parameter_in_tear_down" in specification "spec_should_allow_to_refactor_parameter_in_tear_down" with the following steps with implementation 
     |step text |implementation     |
     |----------|-------------------|
     |First step|"inside first step"|

* Add the following teardown steps in specification "spec_should_allow_to_refactor_parameter_in_tear_down" 
     |step text      |implementation          |
     |---------------|------------------------|
     |First teardown |"inside first teardown" |
     |Second teardown|"inside second teardown"|

Spec should allow to refactor parameter in tear down
----------------------------------------------------
* Refactor step "First teardown" to "First <b>teardown"
* The step "First <b>teardown" should be used in project
* The step "First teardown" should no longer be used

