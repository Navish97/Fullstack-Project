# MyMarketPlace
This project was completed by three students from the fullstack development course IDATT2105 at NTNU.

The project itself entailed creating a marketplace website with functionalities that would enable users to list and sell their items.

Our solution was built using Vue for the frontend and Spring Boot Maven for the backend.

The application was designed while keeping both PC and Phone formats in mind.

There are two already pre-created accounts with some dummy data.

User account:

email: user@localhost.com

password: user

Admin account: admin@localhost.com

password: admin



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

When run on the local profile, it simply uses a H2 database saved inside a database folder in the directory of the backend project.
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

The local h2 database is stored inside the backend project folder and the h2 console can be accessed here:

http://localhost:8080/h2-ui/

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

The secret key for the token generation in the application was also divided between server and local context.
When running locally on development profile a simple key saved directly in the code was used.
When running on server, the key was set as an environment variable in the server and accessed that way for better security.

Furthermore for safety reasons, environment variables were also used in place of sensitive credentials in the configuration for connecting to the MySQLv8 Cloud SQL database.

## API Documentation
For this project, Swagger 3 was used for documenting the api.
This documentation can be accessed locally if you run the backend database first.
The url for the local swagger api documentation site is:

http://localhost:3000/

For the backend google cloud run server, the swagger docs can be accessed here:

https://fullstack-project-xt5ws57jza-lz.a.run.app/swagger-ui/index.html


## Server
The servers were run as two Google Cloud Run services.

The backend was setup through Google's Cloud SQL service, and the backend connects to it using the MySQL connector configuration defined in the application's production configuration. The configuration as mentioned earlier, also has the different sensitive credentials defined as environment variables so only the server has access to these credentials.

NOTE: The servers will be taken down over the course of April 2023. The servers were set up for the purpose of gaining experience with developing a product while utilizing CD in a production environment.

## CI/CD
For CI, github actions was used to run the tests for both front-end and back-end.
The yml file was configured inside the .github/workflows folder, which runs the tests automatically every time there's a pull request or change is pushed to the development branch. Separate docker files for running the tests were created inside the respective folders.

For CD we made use of Google Cloud Run's inbuilt feature for monitoring the github project repository. This was set to auto deploy and run the respective dockerfiles for both front-end and back-end whenever anything is pushed, either directly or through a pull request, into the main branch.

The front-end cloud run url is this:

https://mymarketplace-xt5ws57jza-lz.a.run.app

The back-end is this:

https://fullstack-project-xt5ws57jza-lz.a.run.app

For the front end server, a custom domain was also mapped for the front-end server to make the url cleaner, the domain is this:

https://myserverprojects.store/

As mentioned earlier, these servers will most likely be taken down over the course of April 2023 due to cost. They have served their purpose and have helped us gain experience.

## Contributors
Daniel Skymoen, Erik Skjellevik & Navid Muradi
