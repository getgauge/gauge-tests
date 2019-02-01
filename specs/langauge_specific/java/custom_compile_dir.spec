# Compile Directory

tags: java

## Implementation file in Custom Compile Directory

* Initialize a project named "custom_compile_dir" without example spec

* Create a scenario "Custom Compile Dir" in specification "Compilation" with the following steps with implementation 

   |step text |implementation                  |implementation dir|
   |----------|--------------------------------|------------------|
   |Print step|"impl inside custom compile dir"|test/java/impls   |

* Ensure success while executing current project with environment variables 

   |Environment Variable    |Value    |
   |------------------------|---------|
   |gauge_custom_compile_dir|test/java|

* Console should contain following lines in order 

   |console output                |
   |------------------------------|
   |impl inside custom compile dir|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |

## Implementation files in Multiple Custom Compile Directory

* Initialize a project named "custom_compile_dir" without example spec

* Create a scenario "Custom Compile Dir" in specification "Compilation" with the following steps with implementation 

   |step text         |implementation                 |implementation dir|
   |------------------|-------------------------------|------------------|
   |Print step        |"impl inside test/java/impls"  |test/java/impls   |
   |Print Another step|"impl inside newsrc/java/impls"|newsrc/java/impls |

* Ensure success while executing current project with environment variables 

   |Environment Variable    |Value                       |
   |------------------------|----------------------------|
   |gauge_custom_compile_dir|test/java, newsrc/java/impls|

* Console should contain following lines in order 

   |console output               |
   |-----------------------------|
   |impl inside test/java/impls  |
   |impl inside newsrc/java/impls|

* Statistics generated should have 

   |Statistics name|executed|passed|failed|skipped|
   |---------------|--------|------|------|-------|
   |Specifications |1       |1     |0     |0      |
   |Scenarios      |1       |1     |0     |0      |
