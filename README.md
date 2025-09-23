# Film Base

FilmBase is a Java-based application designed to manage and track films. It follows a modular structure using Gradle for build automation and dependency management.


## Project Structure

FilmBase
├── build/ # Compiled output files
├── gradle/ # Gradle wrapper files
├── src/main/java/com/example/filmbase
│ ├── Controller/ # Contains controllers like GlobalController
│ ├── Manager/ # Contains managers like DataBaseManager
│ ├── Model/ # Contains models like Film
│ └── FilmBaseApplication # Main application entry point
├── src/main/resources/ # Application resources
├── templates/ # UI templates or HTML files
├── build.gradle # Gradle build configuration
├── settings.gradle # Gradle settings
├── gradlew # Gradle wrapper script (Linux/Mac)
├── gradlew.bat # Gradle wrapper script (Windows)
└── README.md # Project documentation


## Features

- Manage films using `Film` model.
- Centralized database operations via `DataBaseManager`.
- Global application control with `GlobalController`.
- Java application with Gradle build support.

## Prerequisites

- Java 17 or above
- Gradle 7.x or use the included Gradle wrapper
- IDE (e.g., IntelliJ IDEA)

## Setup

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd FilmBase
    ```

2. Build the project using Gradle:
    ```bash
    ./gradlew build
    ```

3. Run the application:
    ```bash
    ./gradlew run
    ```


