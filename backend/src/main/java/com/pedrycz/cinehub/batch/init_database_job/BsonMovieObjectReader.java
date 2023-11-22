package com.pedrycz.cinehub.batch.init_database_job;

import com.pedrycz.cinehub.exceptions.IncompleteDocumentException;
import com.pedrycz.cinehub.model.entities.Movie;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;

@Component
@Slf4j
public class BsonMovieObjectReader implements JsonObjectReader<Movie> {

    private BufferedReader bufferedReader;

    @Override
    public Movie read() throws Exception {
        String line;
        if ((line = bufferedReader.readLine()) != null) {
            Document document = Document.parse(line);
            try {
                return new Movie(getField(document, "title").toString(),
                        getField(document, "plot").toString(),
                        getField(document, "year").toString(),
                        (Integer) getField(document, "runtime"),
                        getField(document, "poster").toString(),
                        (List<String>) getField(document, "genres"),
                        (List<String>) getField(document, "directors"),
                        (List<String>) getField(document, "cast"));
            } catch (NullPointerException e) {
                throw new IncompleteDocumentException();
            }
        } else {
            return null;
        }
    }

    @Override
    public void open(@NotNull Resource resource) throws Exception {
        JsonObjectReader.super.open(resource);
        bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }

    @Override
    public void close() throws Exception {
        JsonObjectReader.super.close();
    }

    private Object getField(Document document, String fieldName) throws NullPointerException {
        Object field = document.get(fieldName);
        if (field == null) {
            throw new NullPointerException("Field not found");
        }
        return field;
    }
}
