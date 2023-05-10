# CineHub
Project main goal is to recreate key functionalities of websites such as imdb and 
rottentomatoes using SpringBoot, ReactJS and noSQL database. Application also uses minIO object storage for poster pictures for movies uploaded by users. Other posters are retrieved from URLs provided in `movies` collection from MongoDB example dataset `Sample Mflix Dataset`, which is also used as initial movie data in this project.

# Executing
## Backend
1. Go to `docker` folder and build docker containers with `docker compose up`
2. Inside `backend` directory execute `mvn spring-boot:run` or `mvn clean spring-boot:run`

For documentation, while backend application is running, go to `localhost:8080/documentation` in your web browser.

## Frontend
### Work in progress
