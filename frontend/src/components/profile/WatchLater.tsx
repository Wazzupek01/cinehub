import Grid from "@mui/material/Grid";
import MovieCard from "../movie-browser/MovieCard";
import classes from "./WatchLater.module.css";

function WatchLater(props: any) {
  const movies = props.data.map((movie: any) => (
    <Grid
      item
      xs={12}
      sm={12}
      md={6}
      lg={3}
      xl={3}
      alignItems="center"
      key={movie.id}
    >
      <MovieCard
        posterUrl={movie.posterUrl}
        title={movie.title}
        releaseYear={movie.releaseYear}
        directors={movie.directors}
        runtime={movie.runtime}
        genres={movie.genres}
        id={movie.id}
        disabled={false}
      />
    </Grid>
  ));

  return (
    <div className={classes.WatchLater_container}>
      <Grid container spacing={2} alignItems="center">
        {movies}
      </Grid>
    </div>
  );
}

export default WatchLater;
