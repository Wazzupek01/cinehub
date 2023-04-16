package com.pedrycz.cinehub;

import com.pedrycz.cinehub.model.Movie;
import com.pedrycz.cinehub.repositories.MovieRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class CinehubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinehubApplication.class, args);
    }

    @Bean
    public static CommandLineRunner initDB(MovieRepository movieRepository, MongoTemplate mongoTemplate) {
        return args -> {
            if(movieRepository.findAll().isEmpty()){
                JSONParser parser = new JSONParser();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
                JSONArray movies = (JSONArray) parser.parse(new FileReader("./src/main/resources/movies.json"));
                for (Object o : movies) {
                    JSONObject movie = (JSONObject) o;
                    movieRepository.insert(new Movie(
                            (movie.get("Title") == null) ? null : movie.get("Title").toString(),
                            (movie.get("Production Buget") == null) ? null : Integer.parseInt(String.valueOf(movie.get("Production Buget"))),
                            LocalDate.parse(movie.get("Release Date").toString(), formatter),
                            (movie.get("Running Time min") == null) ? null : Integer.parseInt(movie.get("Running Time min").toString()),
                            (movie.get("Distributor") == null) ? null : movie.get("Distributor").toString(),
                            (movie.get("Major Genre") == null) ? null : movie.get("Major Genre").toString(),
                            (movie.get("Director") == null) ? null : movie.get("Director").toString()
                    ));
                }
            }
        };
    }
}
