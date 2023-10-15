import { useLoaderData } from "react-router-dom";
import MovieCard from "../components/movie-browser/MovieCard";
import Grid from "@mui/material/Grid";
import { useCallback, useEffect, useState } from "react";
import RecentReviews from "../components/review-list/RecentReviews";
import MovieInfo from "../components/movie-browser/MovieInfo";

function MoviePage() {
  const [topReviews, setTopReviews] = useState();
  const movie = JSON.parse(useLoaderData() as string);
  const [topReviewsLoading, setTopReviewsLoading] = useState(true);

  const fetchReviewsHandler = useCallback(async () => {
    setTopReviewsLoading(true);

    try {
      const response = await fetch("http://localhost:8080/review/movie/recent/" + movie.id);
      
      if(!(response.status < 300)){
        throw new Error("Error: " + response.status);
      }
      
      const data = await response.json();

      console.log(data)

      const transformedReviews = data.map((reviewData: any) => {
        return {
          id: reviewData.id,
          content: reviewData.content,
          rating: reviewData.rating,
          userId: reviewData.userId,
          movieId: reviewData.movieId,
          timestamp: reviewData.timestamp
        };
      });
      setTopReviews(transformedReviews);
    } catch (error) {
      console.log("error");
    }
    setTopReviewsLoading(false);
  }, []);

  useEffect(() => {
    fetchReviewsHandler();
  },[fetchReviewsHandler]);


  return (
    <Grid container spacing={2} alignItems="center">
      <Grid item xs={12} sm={12} md={6} alignItems="center">
        <MovieCard
          posterUrl={movie.posterUrl}
          title={movie.title}
          releaseYear={movie.releaseYear}
          directors={movie.directors}
          runtime={movie.runtime}
          genres={movie.genres}
          id={movie.id}
          disabled={true}
        />
      </Grid>
      <Grid item xs={12} sm={12} md={6} alignItems="center">
        <MovieInfo movie={movie} />
      </Grid>
      <Grid item xs={12} alignItems="center">
        {!topReviewsLoading && <RecentReviews reviews={topReviews} />}
      </Grid>
    </Grid>
  );
}

export default MoviePage;
