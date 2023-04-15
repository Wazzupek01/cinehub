package com.pedrycz.cinehub;

import com.mongodb.client.MongoClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@SpringBootApplication
public class CinehubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinehubApplication.class, args);
	}
}
