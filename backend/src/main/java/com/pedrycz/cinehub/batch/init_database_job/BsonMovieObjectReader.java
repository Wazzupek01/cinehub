package com.pedrycz.cinehub.batch;

import com.pedrycz.cinehub.exceptions.IncompleteDocumentException;
import com.pedrycz.cinehub.model.entities.Movie;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

@Component
@Slf4j
public class BsonMovieObjectReader implements JsonObjectReader<Movie> {

    private BufferedReader bufferedReader;
    private String line;
    private Document document;

    @Override
    public Movie read() throws Exception {
        if ((line = bufferedReader.readLine()) != null) {
            document = Document.parse(line);
            try {
                return new Movie(getField(document, "title").toString(),
                        getField(document, "plot").toString(),
                        getField(document, "year").toString(),
                        (Integer) getField(document, "runtime"),
                        getField(document, "poster").toString(),
                        (List<String>) getField(document, "genres"),
                        (List<String>) getField(document, "directors"),
                        (List<String>) getField(document, "cast"));
            } catch(NullPointerException e){
                throw new IncompleteDocumentException();
            }
        } else {
            return null;
        }
    }

    @Override
    public void open(Resource resource) throws Exception {
        JsonObjectReader.super.open(resource);
        bufferedReader = new BufferedReader(new FileReader(resource.getFile()));
    }

    @Override
    public void close() throws Exception {
        JsonObjectReader.super.close();
    }

    private Object getField(Document document, String fieldname) throws NullPointerException {
        Object field = document.get(fieldname);
        if(field == null){
            throw new NullPointerException("Field not found");
        }
        return field;
    }
}
