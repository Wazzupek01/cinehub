import { useNavigate } from 'react-router-dom';
import { genCommaDividedList } from '../../helpers/genCommaDividedList';
import { IMovieInfoProps } from '../../interfaces/IMovieInfoProps';
import RateMovie from '../rating/RateMovie';
import DeleteIcon from '@mui/icons-material/Delete';
import classes from "./MovieInfo.module.css";
import MovieService from '../../services/MovieService';

function MovieInfo(props: IMovieInfoProps) {
  const navigate = useNavigate();
  const movieService = new MovieService();
  const movie = props.movie;

  const addReviewHandler = () => {
    window.location.reload();
  }

  const removeMovieHandler = () => {
    movieService.deleteMovieById(movie.id);
    navigate("/browse/0");
  }

  const genreString: string = genCommaDividedList(movie.genres);
  const castString: string = (movie.actors !== null) ? genCommaDividedList(movie.actors) : "";


  return (
    <div className={classes.MovieInfo_container}>
      <h1  className={classes.title}><>{movie.title}
      {localStorage.getItem("role") === "ROLE_ADMIN" && <div onClick={removeMovieHandler}><DeleteIcon /></div>}</>
      </h1>
        <RateMovie rating={movie.rating} readOnly={false} movieId={movie.id} onAddReview={addReviewHandler} />
        <h2>Mean rating: {movie.rating.toFixed(2)}</h2>
        <h2>{movie.releaseYear}</h2>
        {movie.directors.map((dir: string) => (
          <h3 key={dir}>{dir}</h3>
        ))}
        <h3>{genreString}</h3>
        <h3>{movie.runtime} minutes</h3>
        <p style={{width: "50%"}}>{movie.plot}</p>
        <h2>Cast:</h2>
        <p>{castString}</p>
    </div>
  )
}

export default MovieInfo