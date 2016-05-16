concept execution
=================

* In an empty directory initialize a project named "concept execution" with the current language

* Create concept "sample_concept" with following steps

     |concept steps|implementation|
     |-------------|--------------|
     |Tell         |print params  |

Resolve concepts when executing from nested directories in specs
----------------------------------------------------------------

* Create "resolve concept" in "concept" within sub folder "subFolder" with the following steps

     |scenario steps|
     |--------------|
     |sample_concept|

* Execute the spec "concept" from folder "specs/subfolder/" and ensure success
