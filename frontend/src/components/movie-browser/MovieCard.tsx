import classes from './MovieCard.module.css';

type CardProps = {
    posterUrl: string,
    title: string,
    releaseYear: string,
    directors: string[],
}

const brokenPosterUrl: string = 
"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/800px-No-Image-Placeholder.svg.png";

const MovieCard = ({posterUrl, title, releaseYear, directors}: CardProps) => {

  return (
    <div className={classes.movieCard} style={{ backgroundImage : `url(${brokenPosterUrl})`}}>
      <div className={classes.movieCard} style={{ backgroundImage : `url(${posterUrl})` }}>
        <div className={classes.movieCard_details}>
          <h1>{title}</h1>
          <h2>{releaseYear}</h2>
          {directors.map((dir) => <h3 key={dir}>{dir}</h3>)}
        </div>
      </div>
    </div>
  )
}

export default MovieCard;