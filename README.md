# MovieAPI - OMDB Movie Information REST API

## Overview

The MovieAPI project is a Java Spring-based REST API that provides comprehensive movie information sourced from the OMDB (Open Movie Database) API. This API allows users to retrieve detailed movie data, search for movies, and obtain information about movie awards.

## Features

- **Movie Search**: Search for movies by title, year, or keywords.
- **Movie Details**: Retrieve comprehensive information about a specific movie.
- **Awards Information**: Determine if a movie has won the "Best Picture" Oscar.
- **RESTful API**: Designed following RESTful principles for easy integration and scalability.
- **Spring Framework**: Built using the Spring Framework, a robust and widely adopted Java framework.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Spring Boot
- Maven (for building and managing dependencies)

### Installation

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/hmehdi/OMBD-Service.git

2. Navigate to the project directory:
    ```bash   
    cd OMBD-Service

3. Build the project using Maven:
   ```bash
      mvn clean install
4. Run the application:
    ```bash
     mvn spring-boot:run
   
The OMBD server should now be up and running on http://localhost:8080.

##Usage
###Endpoints of Movies
- **GET All Movies**:        `/api/movies/all`
- **Search Movies**:         `/api/movies/search?title={title}&year={year}&keywords={keywords}`
- **Get Movie Details**:     `/api/movies/{id}`
- **Check "Best Picture" Oscar**: `/api/movies/{id}/best-picture`



###Endpoints of Oscar Winner Movies
- **GET All Movies**:        `/api/oscar-winners/all`
- **Get Retrieve Oscar Winners By Movie Title**:         `/api/oscar-winners/by-movie/{movie}`
- **Get Retrieve Oscar Winners By Award**:         `/api/oscar-winners/by-award/{award}`
- **Get Retrieve Oscar Winners By Id**:     `/api/oscar-winners/{id}`

#Configuration
You can configure the OMDB API key and other settings in the `application.properties` 

  










