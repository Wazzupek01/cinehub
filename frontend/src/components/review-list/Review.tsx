import { useCallback, useEffect, useState } from 'react'
import classes from "./Review.module.css";
import dateFormat from "dateformat";
import Rating from '@mui/material/Rating';
import { Link } from 'react-router-dom';

function Review(props: any) {
  const [nickName, setNickName] = useState<string>("");

  const fetchUserHandler = useCallback(async () => {;
    try {
      const response = await fetch("http://localhost:8080/user/id/" + props.userId);
      
      if(!(response.status < 300)){
        throw new Error("Error: " + response.status);
      }
      
      const data = await response.json();
      const nickname = data.nickname;
      setNickName(nickname);
    } catch (error) {
      throw new Error("Couldn't fetch user");
    }
  }, []);

  useEffect(() => {
    fetchUserHandler();
  }, [fetchUserHandler]);

  const date = new Date(props.timestamp);

  return (
    <div className={classes.review_container}>
      <Link to={`/profile/${nickName}`}><h3>{nickName}</h3></Link><h4>{dateFormat(date, "H:MM dd mmmm yyyy")}</h4>
      <Rating
            value={+props.rating/2}
            precision={0.5}
            readOnly
          />
      <p>{props.content}</p>
    </div>
  )
}

export default Review