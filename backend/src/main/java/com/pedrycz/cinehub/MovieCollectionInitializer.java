package com.pedrycz.cinehub;

import com.pedrycz.cinehub.model.Movie;
import com.pedrycz.cinehub.repositories.MovieRepository;
import me.tongfei.progressbar.ProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class MovieCollectionInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieCollectionInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(movieRepository.count() == 0)
            InitializeData();
    }

    private void InitializeData() {
        List<JSONObject> objects = loadJSON("./src/main/resources/movies.json");
        if (objects != null) {
            ProgressBar pb = new ProgressBar("Initializing DB", objects.size());
            pb.start();

            for (JSONObject object : objects) {
                Integer runtime;
                try {
                    runtime = Integer.parseInt(getElementFromJSONObject(object.getJSONObject("runtime"),"$numberInt"));
                } catch(JSONException e){
                    runtime = null;
                }
                String year;
                try {
                    year = getElementFromJSONObject(object.getJSONObject("year"), "$numberInt");
                } catch (JSONException e){
                    year = null;
                }

                movieRepository.insert(
                        new Movie(
                                getElementFromJSONObject(object, "title"),
                                getElementFromJSONObject(object, "plot"),
                                year,
                                runtime,
                                getElementFromJSONObject(object, "poster"),
                                getArrayFromJSONObject(object, "genres"),
                                getArrayFromJSONObject(object, "directors"),
                                getArrayFromJSONObject(object, "cast")
                        )
                );
                pb.step();
            }
            pb.stop();

            System.out.println("Added " + movieRepository.count() + " from " + objects.size());
        }
    }

    private List<JSONObject> loadJSON(String path) {
        List<JSONObject> objects = null;
        try {
            File file = new File(path);
            objects = new ArrayList<>();
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                objects.add(new JSONObject(myReader.nextLine()));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return (objects.isEmpty()) ? null : objects;
    }

    private List<String> getArrayFromJSONObject(JSONObject object, String fieldname) {
        List<String> elements = new ArrayList<>();
        try {
            JSONArray array = object.getJSONArray(fieldname);
            for (Object o : array) {
                elements.add(o.toString());
            }
        } catch (JSONException e) {
            return null;
        }
        return elements;
    }

    private String getElementFromJSONObject(JSONObject object, String fieldname) {
        String value = null;
        try {
            value = object.get(fieldname).toString();
        } catch (JSONException e) {
            // logger here in future
        }
        return value;
    }
}
