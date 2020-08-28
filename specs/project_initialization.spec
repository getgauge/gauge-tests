Project initialization
======================

A gauge project follows a simple structure. A Gauge project can be initialized by running
    gauge init <language>
where the <language> can be any of the supported languages. Currently gauge supports the following languages.

Initializing a new project
--------------------------

tags: java, csharp, dotnet, ruby, python, js

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

Initializing a new project without existing gitignore
-----------------------------------------------------

tags: java, ruby, python, js

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
   |.gitignore                    |file|

* Verify language specific files are created

* Verify language specific .gitignore is created

Initializing unknown language project
-------------------------------------

tags: java, csharp, dotnet, ruby, python, js

* In an empty directory, use default initialization of a project named "init_proj_unknown" in language "unknown"
* Console should contain following lines in order

   |console output                                                           |
   |-------------------------------------------------------------------------|
   |Failed to initialize project. Cannot find Gauge template 'unknown'     |

* Directory "init_proj_unknown" should be empty
