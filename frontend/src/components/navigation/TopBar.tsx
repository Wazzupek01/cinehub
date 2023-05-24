import { useState } from "react";
import classes from "./TopBar.module.css";
import { Link, NavLink } from "react-router-dom";
import SearchModule from "../ui/SearchModule";
import UserMenu from "./UserMenu";


function TopBar() {
  // const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   setAuth(event.target.checked);
  // };


  return (
    <div className={classes.topbar}>
      <Link to="/" className={classes.logo}>
        <span className={classes.logo_first_half}>Cine</span>
        <span className={classes.logo_second_half}>HUB</span>
      </Link>
      <nav>
        <ul>
          <li className={classes.border_gradient}>
            <NavLink to="/top/0" className={({ isActive }) =>
                isActive ? classes.active : undefined
              }>
              <div>Top 100</div>
            </NavLink>
          </li>
          <li className={classes.border_gradient}>
            <NavLink to="/browse/0" className={({ isActive }) =>
                isActive ? classes.active : undefined
              }>
              <div>Browse movies</div>
            </NavLink>
          </li>
        </ul>
        <SearchModule />
      </nav>
      <UserMenu />
    </div>
  );
}

export default TopBar;
