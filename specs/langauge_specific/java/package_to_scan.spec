# Package to scan

tags: java


* Initialize a project named "Package to scan" without example spec

* Create a scenario "package one" in specification "Package to scan" with the following steps with implementation

   |step text |implementation                  |implementation dir               | package_name         |
   |----------|--------------------------------|---------------------------------|----------------------|
   |Print step 1|"impl inside first package"| src/test/java/impls/first_package  | impls.first_package  |

* Create a scenario "package two" in specification "Package to scan" with the following steps with implementation

   |step text |implementation                  |implementation dir               | package_name         |
   |----------|--------------------------------|---------------------------------|----------------------|
   |Print step 2|"impl inside second package"| src/test/java/impls/second_package| impls.second_package |


## Setting single value for package_to_scane env

* Ensure success while executing current project with environment variables

   |Environment Variable    |Value    |
   |------------------------|---------|
   |package_to_scan|impls.first_package|

* Console should contain following lines in order

   |console output         |
   |-----------------------|
   |impl inside first package|

* Console should not contain following lines

   |console output         |
   |-----------------------|
   |impl inside second package|

* Console should contain "Step implementation not found => 'Print step 2'"

* Statistics generated should have

    |Statistics name|executed|passed|failed|skipped|
    |---------------|--------|------|------|-------|
    |Specifications |1       |1     |0     |0      |
    |Scenarios      |1       |1     |0     |1      |

## Setting multiple values for package_to_scane env

* Ensure success while executing current project with environment variables

   |Environment Variable    |Value    |
   |------------------------|---------|
   |package_to_scan|impls.first_package,impls.second_package|

* Console should contain "impl inside first package"
* Console should contain "impl inside second package"


* Statistics generated should have

    |Statistics name|executed|passed|failed|skipped|
    |---------------|--------|------|------|-------|
    |Specifications |1       |1     |0     |0      |
    |Scenarios      |2       |2     |0     |0      |