Project initialization
======================

tags: java, csharp, ruby, python

A gauge project follows a simple structure. A Gauge project can be initialized by running
    gauge --init <language>
where the <language> can be any of the supported languages. Currently gauge supports the following languages.

Initializing a new project
--------------------------

* Initialize a project named "init_proj" with the example specs
* The following file structure should be created 

     |name                          |type|
     |------------------------------|----|
     |manifest.json                 |file|
     |specs                         |dir |
     |specs/example.spec            |file|
     |env                           |dir |
     |env/default                   |dir |
     |env/default/default.properties|file|

* Verify language specific files are created

Initializing unknown language project
-------------------------------------

* In an empty directory, use default initialization of a project named "init_proj_unknown" in language "unknown"
* Console should contain following lines in order

     |console output                                                       |
     |---------------------------------------------------------------------|
     |Compatible langauge plugin unknown is not installed.                 |
     |Failed to install plugin 'unknown'.                                  |
     |Reason: Invalid plugin. Could not download unknown-install.json file.|
* Directory "init_proj_unknown" should be empty
