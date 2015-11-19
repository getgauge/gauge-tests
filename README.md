# GAUGE testing GAUGE!
[![Gauge Badge](getgauge.io/Gauge_Badge.svg)](http://getgauge.io)

Tests for Gauge Command-Line Utility using Gauge

### Steps to get started
- [Install Gauge](http://getgauge.io/download.html)
  - Homebrew on Mac OS X :  
      ```
      brew install gauge
      ```
  - [Download Installer](http://getgauge.io/download.html)
- [Install language plugin](http://getgauge.io/documentation/user/current/plugins/installation.html) by running<br>
  ```
  gauge --install {language}
  ```
  
  ```
  gauge --install java
  gauge --install ruby
  gauge --install csharp
  ```
- Install [Maven](https://maven.apache.org/)

- Clone this repo.

- Executing specs   

  ```
  sh starttests.sh {language}
  ```
  ```
  sh starttests.sh java
  sh starttests.sh ruby
  sh starttests.sh csharp
  ```

This will also compile all the supporting code implementations and run your specs in parallel.
