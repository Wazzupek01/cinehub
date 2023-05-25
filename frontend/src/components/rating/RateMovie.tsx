import Rating from "@mui/material/Rating";
import { useState, useEffect } from "react";
import classes from "./RateMovie.module.css";
import { doesHttpOnlyCookieExist } from "../../helpers/doesHttpOnlyCookieExist";
import ReviewService from "../../services/ReviewService";
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import FavoriteIcon from '@mui/icons-material/Favorite';
import UserService from "../../services/UserService";

function RateMovie(props: any) {
  const [ratingValue, setRatingValue] = useState<number>(
    props.ratingValue === undefined ? 0.5 : props.ratingValue
  );
  const [isReadOnly, setIsReadOnly] = useState(props.readOnly);
  const [ratedByActiveUser, setRatedByActiveUser] = useState(false);
  const [watchlistedByActiveUser, setWatchlistedByActiveUser] = useState(false);

  const [newReview, setNewReview] = useState({
    rating: 0,
    content: ""
  })

  const reviewService: ReviewService = new ReviewService();
  const userService: UserService = new UserService();

  const addToWatchlistHandler = () => {
    if(watchlistedByActiveUser){
      userService.removeFromWatchlist(props.movieId).then((res) => {
        if(+res === 204){
          setWatchlistedByActiveUser(false);
        }
      })
    } else {
      userService.addToWatchlist(props.movieId).then((res) => {
        if(+res === 200){
          setWatchlistedByActiveUser(true);
        }
      });
    }
  }

  const addReviewHandler = (event: any) => {
    event.preventDefault();
    console.log(newReview);
    reviewService.addReview(newReview.rating*2, newReview.content, props.movieId);
    props.onAddReview();
  }

  useEffect(() => {
    if (doesHttpOnlyCookieExist("jwt")) {
      fetch(
        "http://localhost:8080/user/nickname/" +
          localStorage.getItem("nickname")
      ).then(response => response.text())
      .then(result => {
        const user = JSON.parse(result);
        const foundReview = user.myReviews.find((review: any) => review.movieId === props.movieId);
        const foundWatchlisted = user.watchLater.find((movie: any) => movie.id === props.movieId);
        if(foundReview !== undefined){
        setRatingValue(+foundReview.rating/2);
        setIsReadOnly(true);
        setRatedByActiveUser(true);
        }
        if(foundWatchlisted !== undefined){
          setWatchlistedByActiveUser(true);
        }
      });
    } else {
      setIsReadOnly(true);
    }
  }, []);

  const rating = isReadOnly ? (
    <Rating value={ratingValue} precision={0.5} readOnly />
  ) : (
    <Rating
      value={ratingValue}
      precision={0.5}
      onChange={(event, newRatingValue) => {
        setRatingValue(newRatingValue as number);
        setNewReview({
          rating: newRatingValue,
          content: newReview.content
        })
      }}
    />
  );

  const watchListButton = watchlistedByActiveUser ? 
  <FavoriteIcon onClick={addToWatchlistHandler} /> : <FavoriteBorderIcon onClick={addToWatchlistHandler} />;

  return (
    <div className={classes.RateMovie_container}>
      {ratedByActiveUser && <span>Your rating: </span> }
      <div className={classes.verticalFlex}>{rating}
      {!isReadOnly && <div>{watchListButton}</div>}
      </div>
      
      {!isReadOnly && (
        <form onSubmit={addReviewHandler}>
          <textarea cols={40} rows={2} placeholder="Comment..." value={newReview.content} onChange={(event) => {
            setNewReview({
              rating: newReview.rating,
              content: event.target.value
            })
          } } />
          <input type="submit" value="Add review"/>
        </form>
      )}
    </div>
  );
}

export default RateMovie;
