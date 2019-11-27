# Custom Screenshot

## Screenshot using custom logic

tags: java, csharp, dotnet, ruby, python, js

* Initialize a project named "custom_screenshot" without example spec

* Configure project to take custom screenshot and return "some_screenshot.png" as screenshot file

* Create a specification "custom_screenshot" with the following contexts 

   |step text |implementation     |
   |----------|-------------------|
   |hello step|"inside hello step"|

* Create a scenario "generate custom screenshot" in specification "custom_screenshot" with the following steps with implementation 

   |step text  |implementation |
   |-----------|---------------|
   |Second step|throw exception|

* Execute the current project and ensure failure

* Generated html report should have "some_screenshot.png" file in spec "custom_screenshot" for

   |step text  |
   |-----------|
   |Second step|

## Capture screenshot using custom screenshot grabber during test execution

tags: java, ruby, python, js

* Initialize a project named "custom_screenshot" without example spec

* Configure project to take custom screenshot and return "some_screenshot.png" as screenshot file

* Create a specification "custom_screenshot" with the following contexts 

   |step text |implementation     |
   |----------|-------------------|
   |hello step|"inside hello step"|

* Create a scenario "generate custom screenshot" in specification "custom_screenshot" with the following steps with implementation 

   |step text  |implementation    |
   |-----------|------------------|
   |Second step|capture screenshot|

* Execute the current project and ensure success

* Generated html report should have "some_screenshot.png" file in spec "custom_screenshot" for

   |step text  |
   |-----------|
   |Second step|