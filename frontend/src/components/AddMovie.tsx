import MovieService from "../services/MovieService";
import { useNavigate } from "react-router-dom";

function AddMovie() {
  const navigate = useNavigate();
  const movieService = new MovieService();
  const addMovieHandler = async (event: any) => {
    event.preventDefault();
    const form = event.currentTarget;
    const movie = JSON.parse(await movieService.addMovie(new FormData(form)));
    if(movie.id != null) navigate("/movie/" + movie.id)
  };

  if(localStorage.getItem("role") !== "ROLE_ADMIN") {
    navigate("/")
  }

  return (
    <>
    <form onSubmit={addMovieHandler}>
      <label htmlFor="title">Title: </label>
      <input name="title" id="title" type="text"></input><br/>
      <label htmlFor="Plot">Plot: </label>
      <input name="plot" id="plot" type="text"></input><br/>
      <label htmlFor="Release year">Release year: </label>
      <input name="releaseYear" id="releaseYear" type="number" min="1900" max="2099" step="1" /><br/>
      <label htmlFor="runtime">Runtime: </label>
      <input name="runtime" id="runtime" type="number" min="1" max="500"></input><br/>
      <label htmlFor="genres">Genres(Divided by "," ): </label>
      <input name="genres" id="genres" type="text"></input><br/>
      <label htmlFor="directors">Directors(Divided by "," ): </label>
      <input name="directors" id="directors" type="text"></input><br/>
      <label htmlFor="cast">Cast(Divided by "," ): </label>
      <input name="cast" id="cast" type="text"></input><br/>
      <label htmlFor="posterFile">Poster file (max 5MB): </label>
      <input name="posterFile" id="posterFile" type="file"></input><br/>
      <input type="submit" value="Add movie" />
    </form>
    </>
  )
}

export default AddMovie