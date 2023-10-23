import classes from "./MyReviewsItem.module.css";
import RateMovie from "../rating/RateMovie";
import { Grid } from "@mui/material";
import dateFormat from "dateformat";
import DeleteIcon from '@mui/icons-material/Delete';
import ReviewService from "../../services/ReviewService";

function MyReviewsItem(props: any) {
  const reviewService: ReviewService = new ReviewService();
  const timestamp: string = props.review.timestamp;
  const removeReview = () => {
    console.log(props)
      reviewService.removeReviewById(props.review.id);
      window.location.reload()
  }

  return (
    <>
      <Grid
        container
        alignItems="top"
        className={classes.MyReviewsItem_container}
      >
        <Grid item xs={12} sm={6} md={6}>
          <div className={classes.MyReviewsItem_movieInfo}>
            <img
              src={props.review.movie.posterUrl}
              alt={props.review.movie.title}
            />
            <div style={{ flexDirection: "column" }}>
              <h3>
                {props.review.movie.title} {props.review.movie.releaseYear}
              </h3>
              <h4>{props.review.movie.director}</h4>
              <h4>{props.review.movie.runtime} minutes</h4>
            </div>
          </div>
        </Grid>

        <Grid item xs={12} sm={6} md={6}>
          <div className={classes.MyReviewsItem_review}>
            <RateMovie
              ratingValue={props.review.rating}
              readOnly={false}
              showWatchlist={false}
            />
            <b>{props.review.content}</b>
            <p>{dateFormat(timestamp, "H:MM dd mmmm yyyy")}</p>
            {props.showDelete && <div onClick={removeReview}><DeleteIcon /></div>}
            
          </div>
        </Grid>
      </Grid>
      <div style={{ justifyContent: "center" }}>
        <div
          style={{
            width: "100%",
            height: "2px",
            background: "var(--light-blue)",
            marginBottom: "10px",
          }}
        ></div>
      </div>
    </>
  );
}

export default MyReviewsItem;
