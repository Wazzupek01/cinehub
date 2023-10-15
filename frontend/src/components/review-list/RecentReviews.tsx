import Review from "./Review";
import classes from "./RecentReviews.module.css";

function RecentReviews(props: any) {
  return (
    <div className={classes.RecentReviews_container}>
      <h1>Most recent reviews</h1>
      {(props.reviews !== undefined) && props.reviews.map((review: any) => (
        <Review
          key={review.id}
          rating={review.rating}
          userId={review.userId}
          content={review.content}
          timestamp={review.timestamp}
        />
      ))}
    </div>
  );
}

export default RecentReviews;
