import classes from './MovieCard.module.css';

type CardProps = {
    posterUrl: string,
    title: string,
    releaseYear: string
}

const MovieCard = ({posterUrl, title, releaseYear}: CardProps) => {
  return (
    <div className={classes.movieCard} style={{ backgroundImage : `url(${posterUrl})` }}>
    </div>
  )
}

export default MovieCard;