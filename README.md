# MyMarketPlace
This project was completed by three students from the fullstack development course IDATT2105 at NTNU.

The project itself entailed creating a marketplace website with functionalities that would enable users to list and sell their items.

Our solution was built using Vue for the frontend and Spring Boot Maven for the backend API calls.

## Documentation

### Front-end
As mentioned, our front-end runs on vue3.
For our frontend, we used a set of libraries and modules to enable ease of use.

#### Some of the more important ones are:

Typescript for typescript support

Vue Router for defining routes

Pinia for state management

Vitest for unit testing components

Axios for api calls

Leaflet for displaying DD coordinates in maps


### Back-end
Our backend runs on Spring Boot Maven 3.
During the project, we put a lot of emphasis on simplifying the use of different databases between production and development stages.
Therefore the backend project runs on different databases depending on if it's deployed on server or runs locally.

When run on the local profile, it simply uses a H2 database in C:\FullstackProjectDatabase.
When deployed on server it connects to a MySQLv8 database which is set up through Google's SQL service.

#### Some important dependencies used by the backend are:
Spring-boot-starter for default configurations for the Spring Boot project.

Springdoc-openapi used to generate OpenAPI documentation for the RESTful APIs in a Spring Boot project, including a web UI using Swagger.

jakarta.persistence for managing and persisting Java objects to a relational database using ORM.

h2 for databases run locally. Runs in memory when running tests, runs as a file when run during development.

spring-boot-starter-data-jpa for using Spring Data JPA.

spring-boot-starter-security for authentication and authorization.

lombok for auto generating boilerplate code through annotations.

mysql-socket-factory and mysql-connector-java for connecting to MySQL databases

## Installation and Setup

### Clone the repo
First step to getting the project running locally is by actually cloning the repo to local.


Backend
Open the backend directory in your preferred IDE
Run the application

Frontend
Open the frontend directory in your preferred code editor
Install dependencies by running npm install
Start the development server by running npm run dev

Usage


Contributors
Daniel Skymoen, Erik Skjellevik & Navid Muradi
