# MyMarketPlace
This project was completed by three students from the fullstack development course IDATT2105 at NTNU.

The project itself entailed creating a marketplace website with functionalities that would enable users to list and sell their items.

Our solution was built using Vue for the frontend and Spring Boot Maven for the backend API calls.

The application was designed while keeping both PC and Phone formats in mind.

## Content
1. [Documentation](#documentation)
2. [Database](#database)
3. [Installation and Setup](#installation-and-setup)
4. [Security](#security)
5. [API Documentation](#api-documentation) 
6. [Server](#server)
7. [CI/CD](#cicd)
8. [Contributors](#contributors)

## Documentation

### Front-end
As mentioned, our front-end runs on Vue 3.
For our frontend, we used a set of libraries and modules to enable ease of use.

#### Some of the more important ones are:
- Typescript for typescript support
- Vue Router for defining routes
- Pinia for state management
- Vitest for unit testing components
- Axios for api calls
- Leaflet for displaying DD coordinates in maps


### Back-end
Our backend runs on Spring Boot Maven 3.
During the project, we put a lot of emphasis on simplifying the use of different databases between production and development stages.
Therefore the backend project runs on different databases depending on if it's deployed on server or runs locally.

When run on the local profile, it simply uses a H2 database in C:\FullstackProjectDatabase.
When deployed on server it connects to a MySQLv8 database which is set up through Google's SQL service.

#### Some important dependencies used by the backend are:
- Spring-boot-starter for default configurations for the Spring Boot project.
- Springdoc-openapi used to generate OpenAPI documentation for the RESTful APIs in a Spring Boot project, including a web UI using Swagger.
- jakarta.persistence for managing and persisting Java objects to a relational database.
- h2 for databases run locally. Runs in memory when running tests, runs as a file when run during development.
- spring-boot-starter-data-jpa for using Spring Data JPA.
- spring-boot-starter-security for authentication and authorization.
- lombok for auto generating boilerplate code through annotations.
- mysql-socket-factory and mysql-connector-java for connecting to MySQL database

## Database
![ERDiagram](https://github.com/Navish97/Fullstack-Project/blob/dev/ER%20Diagram.png?raw=true)

As mentioned earlier, this database is setup as a h2 database when run locally, but as a mysql database using MySQLv8.
The local h2 database is saved in the backend project folder inside a database folder.

## Installation and Setup

### Clone the repo
First step to getting the project running locally is by actually cloning the repo to local.


### Backend
Once you've got a local repository you can start running the project.
Before running the backend, you have to first cd into the backend folder directory.
Once inside the backend directory you have to first install the dependencies from the pom file.
```
mvn clean install
```

Once the dependencies are installed you can run the project with:
```
mvn spring-boot:run
```

### Frontend
Open the root directory again and cd into the vue-frontend directory.
Once inside the frontend directory, install the necessary modules with:
```
npm install
```
Once the modules finished installing, you can run the local dev server with:
```
npm run dev
```

## Security
During the entire development process security has continuously been a big focus for the team.
It might be one of the most important things to focus on for developers.
As of right now we've utilized jwt tokens for authentication, these are sent to the browser as cookies to ensure better security.
All authenticated API calls that relate to a user getting their own information, the cookies are always verified by the filter first.

Once the token is verified, the user identification is extracted from the token instead of it being passed as a parameter.
This also ensures that tokens from normal users can't be used to get access to information that isn't related to their user account.

Only a certain few necessary api calls have the authentication token set as optional.

## Server

## CI/CD


## Contributors
Daniel Skymoen, Erik Skjellevik & Navid Muradi
