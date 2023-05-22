import { useLoaderData } from "react-router-dom";
import MovieCard from "../components/movie-browser/MovieCard";
import Grid from "@mui/material/Grid";
import Rating from "@mui/material/Rating";
import { useCallback, useEffect, useState } from "react";
import ReviewService from "../services/ReviewService";

function MoviePage() {
  const reviewService = new ReviewService();
  const [ratingValue, setRatingValue] = useState<number>(5);
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
      console.log(data);

      const transformedReviews = data.map((reviewData) => {
        console.log(reviewData);
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

  let genreString: string = "";
  for (let i: number = 0; i < movie.genres.length; i++) {
    if (i !== movie.genres.length - 1) {
      genreString += `${movie.genres[i]}, `;
    } else {
      genreString += movie.genres[i];
    }
  }

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
      <Grid item sm={12} md={6} alignItems="center">
        <h1>{movie.title}</h1>
        <div>
          <Rating
            name="simple-controlled"
            value={ratingValue}
            onChange={(event, newRatingValue) => {
              setRatingValue(newRatingValue as number);
            }}
          />
          <input type="text" placeholder="Comment..." />
          <input type="submit" value="Add review" />
        </div>

        <h2>{movie.releaseYear}</h2>
        {movie.directors.map((dir: string) => (
          <h3 key={dir}>{dir}</h3>
        ))}
        <h4>{genreString}</h4>
        <h4>{movie.runtime} minutes</h4>
      </Grid>
      <Grid item xs={12} alignItems="center">
        {!topReviewsLoading && topReviews.map(review => (<p key={review.id}>{review.content}</p>))}
      </Grid>
    </Grid>
  );
}

export default MoviePage;
