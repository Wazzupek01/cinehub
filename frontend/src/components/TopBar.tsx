import IconButton from "@mui/material/IconButton";
import { useState } from "react";
import classes from "./TopBar.module.css";
import { Link, NavLink } from "react-router-dom";
import { AccountCircle } from "@mui/icons-material";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import SearchIcon from "@mui/icons-material/Search";
import TextField from "@mui/material/TextField";

function TopBar() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [searchedName, setSearchedName] = useState("");
  const [showSearchField, setShowSearchField] = useState(false);

  // const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   setAuth(event.target.checked);
  // };

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleSearch = () => {
    setShowSearchField(!showSearchField);
  };

  const generateSearch = () => {
    if (searchedName.trim().length > 1) {
      return (
        <>
          {showSearchField && <input type="text" value={searchedName} onChange={(event) => {setSearchedName(event.target.value)}} />}
          <Link to={`/find/${searchedName}/0`}>
          <div onClick={handleSearch}>
            <SearchIcon fontSize="small" />
          </div>
          </Link>
          </>
      );
    }
    return (
      <div>
        {showSearchField && <input type="text" value={searchedName} onChange={(event) => {setSearchedName(event.target.value)}}/>}
        <div onClick={handleSearch}>
          <SearchIcon fontSize="small" />
        </div>
      </div>
    );
  };

  const search = generateSearch();

  return (
    <div className={classes.topbar}>
      <Link to="/" className={classes.logo}>
        <span className={classes.logo_first_half}>Cine</span>
        <span className={classes.logo_second_half}>HUB</span>
      </Link>
      <nav>
        <ul>
          <li className={classes.border_gradient}>
            <NavLink to="/top">
              <div>Top 100</div>
            </NavLink>
          </li>
          <li className={classes.border_gradient}>
            <NavLink to="/browse/0">
              <div>Browse movies</div>
            </NavLink>
          </li>
          <li className={classes.border_gradient}>{search}</li>
        </ul>
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
