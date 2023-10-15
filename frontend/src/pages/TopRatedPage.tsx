import { useLoaderData } from "react-router-dom";
import MovieBrowser from "../components/movie-browser/MovieBrowser";

function TopRatedPage() {
  const data = useLoaderData() as string;
  
  return <MovieBrowser pageTitle="Browse movies" data={data} url={"/top/"} />;
}

export default TopRatedPage;
