import { useLoaderData, useLocation } from "react-router-dom";
import MovieBrowser from "../components/movie-browser/MovieBrowser";
import { useState } from "react";
import MovieFilter from "../components/MovieFilter";

function BrowseMoviesPage(props: any) {
  let url = useLocation().pathname;
  const pathVariables = url.split('/');
  let howMuchToTrim = pathVariables[pathVariables.length-1].length;

  const data = useLoaderData();

  return (
    <>
      <MovieFilter />
      <MovieBrowser pageTitle="Browse movies" data={data} url={url.substring(0, url.length - howMuchToTrim)} />
    </>
  );
}

export default BrowseMoviesPage;
