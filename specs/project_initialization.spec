Project initialization
====================

A gauge project follows a simple structure. A Gauge project can be initialized by running

    gauge --init <language>

where the <language> can be any of the supported languages. Currently gauge supports the following languages.


Initializing a new project
--------------------

* In an empty directory initialize a project with the current language
* The following file structure should be created 
     |name                          |type|
     |------------------------------|----|
     |manifest.json                 |file|
     |specs                         |dir |
     |specs/hello_world.spec        |file|
     |env                           |dir |
     |env/default                   |dir |
     |env/default/default.properties|file|

* Verify language specific files are created


Initializing unknown language project
--------------------

* In an empty directory initialize a "unknown" project
* Console should contain "Failed to initialize. unknown is not a supported language"
