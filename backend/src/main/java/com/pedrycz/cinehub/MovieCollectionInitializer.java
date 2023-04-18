package com.pedrycz.cinehub;

import com.pedrycz.cinehub.model.Movie;
import com.pedrycz.cinehub.repositories.MovieRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Component
public class MovieCollectionInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieCollectionInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        if(movieRepository.count() == 0) return;

        FileReader fileReader;

        try {
            fileReader = new FileReader("./src/main/resources/movies.json");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        BufferedReader br = new BufferedReader(fileReader);

        String line;
        Document document;
        int incompleteDocuments = 0;
        int count = 0;
        System.out.println("Initializing db...");

        while ((line = br.readLine()) != null) {
            document = Document.parse(line);
            try {
                movieRepository.insert(new Movie(getField(document, "title").toString(),
                        getField(document, "plot").toString(),
                        getField(document, "year").toString(),
                        (Integer) getField(document, "runtime"),
                        getField(document, "poster").toString(),
                        (List<String>) getField(document, "genres"),
                        (List<String>) getField(document, "directors"),
                        (List<String>) getField(document, "cast")));
            } catch(NullPointerException e){
                incompleteDocuments++;
            } finally {
                count ++;
            }
        }
        System.out.println("DB initialized, missed " + incompleteDocuments + " from " + count + " records due to incomplete data");
    }

    private Object getField(Document document, String fieldname) throws NullPointerException {
        Object field = document.get(fieldname);
        if(field == null){
            throw new NullPointerException("Field not found");
        }
        return field;
    }
}
