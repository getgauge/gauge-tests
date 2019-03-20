Scenario execution count depends on Data Table value usage
==========================================================

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "Data_Driven_execution" without example spec

Scenarios with Unused data table value
--------------------------------------
* Create a specification "SimpleSpec_UnsedDataTableValue" with the following datatable 

   |one |two |three|
   |----|----|-----|
   |val1|val2|val31|
   |val1|val2|val32|
   |val1|val2|val33|

* Create concept "Concept <param>" with following steps 

   |step text                 |implementation|
   |--------------------------|--------------|
   |Step without param        |"simple"      |
   |scenario step with <param>|"param"       |

Simple spec with unused data table values
* Create a scenario "ConceptNotUsingDataTableValue1" in specification "SimpleSpec_UnsedDataTableValue" with the following steps 

   |step text         |
   |------------------|
   |Step without param|
   |Concept "3"       |

* Create a scenario "ScenariosNotUsingDataTableValue2" in specification "SimpleSpec_UnsedDataTableValue" with the following steps 

   |step text         |
   |------------------|
   |Step without param|

* Execute the current project and ensure success
* Console should contain "ConceptNotUsingDataTableValue1" "1" times
* Console should contain "ScenariosNotUsingDataTableValue2" "1" times

Scenarios with context and teardown having Unused data table value
------------------------------------------------------------------
* Create a specification "ContextSpec_UnusedDataTableValue" with the following datatable 

   |one |two |three|
   |----|----|-----|
   |val1|val2|val31|
   |val1|val2|val32|
   |val1|val2|val33|

* Create concept "Concept <param>" with following steps 

   |step text                 |implementation|
   |--------------------------|--------------|
   |Step without param        |"simple"      |
   |scenario step with <param>|"param"       |

* Create a specification "ContextSpec_UnusedDataTableValue" with the following contexts 

   |step text         |
   |------------------|
   |Step without param|
   |Concept "3"       |

* Add the following teardown steps in specification "ContextSpec_UnusedDataTableValue" 

   |step text         |
   |------------------|
   |Step without param|
   |Concept "3"       |

* Create a scenario "ScenariosNotUsingDataTableValue3" in specification "ContextSpec_UnusedDataTableValue" with the following steps 

   |step text         |
   |------------------|
   |Step without param|

* Execute the current project and ensure success
* Console should contain "ScenariosNotUsingDataTableValue3" "1" times

Scenarios with Using data table values
--------------------------------------
* Create a specification "SimpleSpec_UsedDataTableValue" with the following datatable 

   |one |two |three|
   |----|----|-----|
   |val1|val2|val31|
   |val1|val2|val32|
   |val1|val2|val33|

* Create concept "Concept <param>" with following steps 

   |step text                 |implementation|
   |--------------------------|--------------|
   |Step with <param>         |"simple"      |
   |Step without param        |"simple"      |
   |scenario step with <param>|"something"   |

* Create a scenario "ConceptUsingDataTableValue1" in specification "SimpleSpec_UsedDataTableValue" with the following steps 

   |step text         |
   |------------------|
   |Step without param|
   |Concept <three>   |

* Create a scenario "ScenariosUsingDataTableValue2" in specification "SimpleSpec_UsedDataTableValue" with the following steps 

   |step text        |
   |-----------------|
   |Step with <three>|

* Execute the current project and ensure success
* Console should contain "ConceptUsingDataTableValue1" "3" times
* Console should contain "ScenariosUsingDataTableValue2" "3" times

Scenarios with context Using data table value
---------------------------------------------
* Create concept "Concept <param>" with following steps 

   |step text                 |implementation|
   |--------------------------|--------------|
   |Step without param        |"simple"      |
   |scenario step with <param>|"param"       |

Spec with unused data table values in context and tear down
* Create a specification "ContextSpec_UsedDataTableValue" with the following datatable 

   |one |two |three|
   |----|----|-----|
   |val1|val2|val31|
   |val1|val2|val32|
   |val1|val2|val33|

* Create a specification "ContextSpec_UsedDataTableValue" with the following contexts 

   |step text         |
   |------------------|
   |Step without param|
   |Concept <one>     |

* Create a scenario "Scenarios3" in specification "ContextSpec_UsedDataTableValue" with the following steps 

   |step text         |
   |------------------|
   |Step without param|

* Execute the current project and ensure success
* Console should contain "Scenarios3" "3" times

Scenarios with teardown Using data table value
----------------------------------------------
* Create concept "Concept <param>" with following steps 

   |step text                 |implementation|
   |--------------------------|--------------|
   |Step without param        |"simple"      |
   |scenario step with <param>|"param"       |

Spec with unused data table values in context and tear down
* Create a specification "ContextSpec_UsedDataTableValue" with the following datatable 

   |one |two |three|
   |----|----|-----|
   |val1|val2|val31|
   |val1|val2|val32|
   |val1|val2|val33|

* Add the following teardown steps in specification "ContextSpec_UsedDataTableValue" 

   |step text         |
   |------------------|
   |Step without param|
   |Concept <three>   |

* Create a scenario "Scenarios3" in specification "ContextSpec_UsedDataTableValue" with the following steps 

   |step text         |
   |------------------|
   |Step without param|

* Execute the current project and ensure success
* Console should contain "Scenarios3" "3" times
