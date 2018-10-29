# Flights API

Spring Boot REST API for sheduled flights requests.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* IntelliJ IDEA
* Docker
* Lombok plugin
* MongoDB

### Installing

**IntelliJ IDEA Lombok plugin:**

* Go to: **File | Settings** for Windows and Linux or **IntelliJ IDEA | Preferences** for macOS.
* In the **Settings/Preferences dialog**, click **Plugins**. The plugins page opens.
* In the right-hand part of the dialog, click on the **Browse repositories...** button.
* In the search pane, type **lombok**.
* Right-click the required plugin and select **Download and Install**.
* Restart IntelliJ IDEA.

**MongoDB on Docker:**

* Run **docker pull mongo**
* Run **docker run --name mongodb -d mongo:latest**
* *No extra configuration needed*

## Running the tests

Run separately each Test class under src/test/java/com/nearsoft/training/spring/flightsapi

## Built With

* [Spring](https://spring.io/) - The java framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Isaac Sias** - *Initial work* - [isiasns](https://github.com/isiasns)
