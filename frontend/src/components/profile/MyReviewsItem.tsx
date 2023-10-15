import classes from "./MyReviewsItem.module.css";
import RateMovie from "../rating/RateMovie";
import { Grid } from "@mui/material";
import dateFormat from "dateformat";

function MyReviewsItem(props: any) {
  const timestamp: string = props.review.timestamp;

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
