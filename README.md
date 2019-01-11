GAUGE testing GAUGE!
====================
[![Gauge Badge](http://getgauge.io/Gauge_Badge.svg)](http://getgauge.io)

Tests for Gauge Command-Line Utility using Gauge

### Steps to get started
- [Install Gauge](https://docs.gauge.org/latest/installation.html#install-gauge-for-your-os)

- [Install language plugin](https://docs.gauge.org/latest/installation.html#language-plugins) by running<br>
  ```
  gauge install {language}
  ```

  ```
  gauge install java
  gauge install ruby
  gauge install csharp
  gauge install js
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
  sh starttests.sh js
  ```

This will also compile all the supporting code implementations and run your specs in parallel.
