import { useLoaderData, useParams } from "react-router-dom";
import WatchLater from "./WatchLater";
import { useState } from "react";
import MyReviews from "./MyReviews";
import classes from "./UserLists.module.css";

function UserLists() {
  const { nickname } = useParams();
  const [isMyReviewsActive, setIsMyReviewsActive] = useState(false);
  const data: any = useLoaderData();

  return (
    <div className={classes.UserLists_container}>
      <h1>User: {nickname}</h1>
      <h2>Reviews: {data.myReviews.length}</h2>
      <h2>Wants to watch: {data.watchLater.length}</h2>
        <nav className={classes.UserLists_navbar}>
          <ul>
            <li className={!isMyReviewsActive ? classes.active : ""} onClick={() => {setIsMyReviewsActive(false)}}>Watch Later</li>
            <li className={isMyReviewsActive ? classes.active : ""} onClick={() => {setIsMyReviewsActive(true)}}>My Reviews</li>
          </ul>
        </nav>
      {isMyReviewsActive ? (
        <MyReviews data={data.myReviews} />
      ) : (
        <WatchLater data={data.watchLater} />
      )}
    </div>
  );
}

export default UserLists;
