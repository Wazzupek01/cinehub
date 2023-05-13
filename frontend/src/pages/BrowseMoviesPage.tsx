import { Grid, Pagination, PaginationItem } from "@mui/material";
import React from "react";
import { Link, useLoaderData } from "react-router-dom";
import MovieCard from "../components/MovieCard";
import classes from "./BrowseMoviesPage.module.css";

function BrowseMoviesPage () {
 const data = useLoaderData();

  const pageNumber: number = data.pageable.pageNumber;
  const totalPages: number = data.totalPages;

  const movies = data.content.map((movie) => (
    <Grid item xs={12} sm={12} md={6} lg={3} xl={3}>
      <MovieCard
        posterUrl={movie.posterUrl}
        title={movie.title}
        releaseYear={movie.releaseYear}
      />
    </Grid>
  ));

  return (
    <div className={classes.container}>
      <Grid container spacing={2}>
        {movies}
      </Grid>

      <div style={{backgroundColor: "var(--white)"}}>
      <Pagination
        color="secondary"
        variant="outlined"
        size="large"
        page={pageNumber + 1}
        count={data.totalPages}
        renderItem={(item) => (
          <PaginationItem
            component={Link}
            to={`/browse/${item.page - 1}`}
            {...item}
          />
        )}
      />
      </div>
    </div>
  );
}

export default BrowseMoviesPage;
