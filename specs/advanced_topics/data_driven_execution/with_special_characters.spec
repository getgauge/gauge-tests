Specification Heading
=====================

* Initialize a project named "datatable_spl_chars" without example spec

Simple Datatable With special characters
----------------------------------------

* Create a specification "special chars data table" with the following datatable

   |special_chars                   |
   |--------------------------------|
   |!@#%^&*()__-++~`"':;?/<>.,\|$   |

* Create a scenario "datatable scenario" in specification "special chars data table" with the following steps with implementation

   |step text                |implementation|
   |-------------------------|--------------|
   |print <special_chars>    |print params  |

* Execute the spec "special chars data table" with row range "1" and ensure success
* Console should contain following lines in order

   |console output                        |
   |--------------------------------------|
   |param0=!@#%^&*()__-++~`"':;?/<>.,|$   |
