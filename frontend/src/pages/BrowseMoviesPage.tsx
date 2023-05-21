import { useLoaderData } from "react-router-dom";
import MovieBrowser from "../components/movie-browser/MovieBrowser";
import { useState } from "react";
import MovieFilter from "../components/MovieFilter";

function BrowseMoviesPage () {
  const [url, setUrl] = useState("/browse");

  const data = useLoaderData();

  return (
    <>
    <MovieFilter />
    <MovieBrowser pageTitle="Browse movies" data={data} url={url} />
    </>
    
  );
}

export default BrowseMoviesPage;
