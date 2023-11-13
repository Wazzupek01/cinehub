import { Link } from "react-router-dom";
import classes from "./Footer.module.css";

function Footer() {
  return (
    <div className={classes.footer}>
      <span>Pedrycz © 2023</span>
      {localStorage.getItem("role") === "ROLE_ADMIN" &&<span>Hey admin ( ͡° ͜ʖ ͡°) <Link to="/add"> add new movie</Link></span>}
    </div>
  );
}

export default Footer;
