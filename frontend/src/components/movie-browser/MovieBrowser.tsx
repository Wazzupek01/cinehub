import Grid from "@mui/material/Grid";
import Pagination from "@mui/material/Pagination";
import PaginationItem from "@mui/material/PaginationItem";
import { Link } from "react-router-dom";
import classes from "./MovieBrowser.module.css";
import MovieCard from "./MovieCard";

const MovieBrowser = (props: any) => {
  const data = JSON.parse(props.data);

  const pageNumber: number = data.pageable.pageNumber;
  const totalPages: number = data.totalPages;

  const movies = data.content.map((movie: any) => (
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
        id={movie.id} disabled={false} />
    </Grid>
  ));

  return (
    <div className={classes.container}>
      <h1>{props.pageTitle}</h1>
      <Grid container spacing={2} alignItems="center">
        {movies}
      </Grid>

      <div className={classes.paginationContainer}>
        <Pagination
          color="secondary"
          variant="outlined"
          size="large"
          page={pageNumber + 1}
          count={totalPages}
          renderItem={(item) => (
            <PaginationItem
              component={Link}
              to={props.url + (item.page - 1)}
              {...item}
            />
          )}
        />
      </div>
    </div>
  );
};

export default MovieBrowser;
