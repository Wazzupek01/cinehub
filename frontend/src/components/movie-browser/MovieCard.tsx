import { Link } from 'react-router-dom';
import classes from './MovieCard.module.css';

type CardProps = {
    id: string,
    posterUrl: string,
    title: string,
    releaseYear: string,
    directors: string[],
    runtime: number,
    genres: string[],
    disabled: boolean
}

const brokenPosterUrl = 
"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/800px-No-Image-Placeholder.svg.png";

const MovieCard = ({posterUrl, title, releaseYear, directors, runtime, genres, disabled = false, id}: CardProps) => {

  let genreString = "";
  for(let i = 0; i < genres.length; i++){
    if(i !== genres.length - 1){
      genreString += `${genres[i]}, `;
    } else {
      genreString += genres[i];
    }
  }
  
  return (
    <div className={classes.movieCard} style={{ backgroundImage : `url(${brokenPosterUrl})`}}>
      <div className={classes.movieCard} style={{ backgroundImage : `url(${posterUrl})` }}>
        {!disabled && <Link to={`/movie/${id}`}><div className={classes.movieCard_details}>
          <h1>{title}</h1>
          <h2>{releaseYear}</h2>
          {directors.map((dir) => <h3 key={dir}>{dir}</h3>)}
          <h4>{genreString}</h4>
          <h4>{runtime} minutes</h4>
        </div></Link>}
      </div>
    </div>
  )
}

export default MovieCard;