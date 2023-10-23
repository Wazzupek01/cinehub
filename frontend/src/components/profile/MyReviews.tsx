import MyReviewsItem from "./MyReviewsItem";
import classes from "./MyReviews.module.css";

function MyReviews(props: any) {
  const reviews = props.data.map((review: any) => (
    <MyReviewsItem review={review} showDelete={props.showDelete} />
  ));

  return (
    <div className={classes.MyReviews_container}>
      <ul>{reviews}</ul>
    </div>
  );
}

export default MyReviews;
