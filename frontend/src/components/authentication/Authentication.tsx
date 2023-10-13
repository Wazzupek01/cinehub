import Login from "./Login";
import { Link, NavLink, useParams } from "react-router-dom";
import classes from "./Authentication.module.css";
import Register from "./Register";

const Authentication = () => {
  const { method } = useParams();

  return (
    <div className={classes.Auth_container}>
      <nav className={classes.auth_nav}>
        <NavLink
          to="/auth/login"
          className={({ isActive }) => (isActive ? classes.active : undefined)}
        >
          Login
        </NavLink>
        <NavLink
          to="/auth/register"
          className={({ isActive }) => (isActive ? classes.active : undefined)}
        >
          Register
        </NavLink>
      </nav>
      {method === "register" ? <Register /> : <Login />}
      <Link to="/">Back to Main Page</Link>
    </div>
  );
}

export default Authentication;
