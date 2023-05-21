import IconButton from "@mui/material/IconButton";
import { useState } from "react";
import classes from "./TopBar.module.css";
import { Link, NavLink } from "react-router-dom";
import { AccountCircle } from "@mui/icons-material";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import SearchModule from "../ui/SearchModule";


function TopBar() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);


  // const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   setAuth(event.target.checked);
  // };

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

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
      <div>
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="menu-appbar"
          aria-haspopup="true"
          onClick={handleMenu}
          color="inherit"
        >
          <AccountCircle />
        </IconButton>
        <Menu
          id="menu-appbar"
          anchorEl={anchorEl}
          anchorOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          keepMounted
          transformOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          open={Boolean(anchorEl)}
          onClose={handleClose}
        >
          <MenuItem onClick={handleClose}>Profile</MenuItem>
          <MenuItem onClick={handleClose}>My account</MenuItem>
        </Menu>
      </div>
    </div>
  );
}

export default TopBar;
