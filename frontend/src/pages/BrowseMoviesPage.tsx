import { useLoaderData, useLocation } from "react-router-dom";
import MovieBrowser from "../components/movie-browser/MovieBrowser";
import MovieFilter from "../components/MovieFilter";

function BrowseMoviesPage() {
  const url: string = useLocation().pathname;
  const pathVariables: string[] = url.split('/');
  const howMuchToTrim: number = pathVariables[pathVariables.length-1].length;

  const data: unknown = useLoaderData();

  return (
    <>
      <MovieFilter />
      <MovieBrowser pageTitle="Browse movies" data={data} url={url.substring(0, url.length - howMuchToTrim)} />
    </>
  );
}

export default BrowseMoviesPage;
