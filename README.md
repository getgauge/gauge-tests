GAUGE testing GAUGE!
====================

[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v1.4%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

Tests for Gauge Command-Line Utility using Gauge

### Steps to get started
- [Install Gauge](https://docs.gauge.org/getting_started/installing-gauge.html)

- [Install language plugin](https://docs.gauge.org/plugin.html) by running<br>
  ```
  gauge install {language}
  ```

  ```
  gauge install java
  gauge install ruby
  gauge install csharp
  gauge install dotnet
  gauge install python
  gauge install js
  ```

- Clone this repo.

- Executing specs

  ```
  ./gradlew clean {language}FT # On Linux or Mac
  gradlew.bat clean {language}FT # On Windows
  ```
  ```
  ./gradlew clean javaFT      # For Windows - gradlew.bat clean javaFT
  ./gradlew clean javaFT      # For Windows - gradlew.bat clean javaFT
  ./gradlew clean pythonFT    # For Windows - gradlew.bat clean pythonFT
  ./gradlew clean rubyFT      # For Windows - gradlew.bat clean rubyFT
  ./gradlew clean csharpFT    # For Windows - gradlew.bat clean csharpFT
  ./gradlew clean dotnetFT    # For Windows - gradlew.bat clean dotnetFT
  ```

This will also compile all the supporting code implementations and run your specs in parallel.
