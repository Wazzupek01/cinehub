# CineHub
Project main goal is to recreate key functionalities of websites such as imdb and 
rottentomatoes using SpringBoot, ReactJS and noSQL database. Application also uses minIO object storage for poster pictures for movies uploaded by users. Other posters are retrieved from URLs provided in `movies` collection from MongoDB example dataset `Sample Mflix Dataset`, which is also used as initial movie data in this project.

# Executing
## Backend
### Install JDK 19, Docker and Maven
1. Install [docker](https://docs.docker.com/engine/install/) in your operating system 
2. Install [JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
3. Install maven through your systems' package manager, or on windows follow the [tutorial](https://phoenixnap.com/kb/install-maven-windows)

### Run backend application
1. Go to `docker` folder and build docker containers with `docker compose up`
2. Inside `backend` directory execute `mvn spring-boot:run` or `mvn clean spring-boot:run`

For documentation, while backend application is running, go to `localhost:8080/documentation` in your web browser.

## Frontend
In order to run frontend application you'll need [NodeJS](https://nodejs.org/en/download) installed in your system

### Run frontend application
1. Go to `frontend` directory and install required packages with `npm install`
2. Run application using `npm run dev`
### Work in progress
