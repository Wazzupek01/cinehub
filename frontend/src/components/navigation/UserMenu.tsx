import { AccountCircle } from "@mui/icons-material";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import IconButton from "@mui/material/IconButton";
import { useEffect, useState } from "react";
import { doesHttpOnlyCookieExist } from "../../helpers/doesHttpOnlyCookieExist";
import { Link } from "react-router-dom";
import AuthenticationService from "../../services/AuthenticationService";

function UserMenu() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [nickname, setNickname] = useState<null | string>(localStorage.getItem('nickname'));
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(doesHttpOnlyCookieExist('jwt'));

  useEffect(() => {
    if(!isLoggedIn){
      localStorage.removeItem('nickname');
      setNickname("");
      setIsLoggedIn(false);
    }
  },[isLoggedIn, nickname]);


  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    const authservice = new AuthenticationService();
    authservice.logout().then(res => {
      if(res === 200){
        window.location.reload();
      }
    })
    
  }

  const menuItems = isLoggedIn ? [
  <MenuItem key={0} onClick={handleClose}><Link to={`/profile/${nickname}`}>Profile</Link></MenuItem>,
  <MenuItem key={2} onClick={handleLogout}>Logout</MenuItem>
   ] :
  [
  <MenuItem key={0} onClick={handleClose}><Link to="/auth/login">Login</Link></MenuItem>,
  <MenuItem key={1} onClick={handleClose}><Link to="/auth/register">Register</Link></MenuItem>
  ]

  return (
    <div>
        <span>{nickname}</span>
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
          {menuItems}
        </Menu>
      </div>
  )
}

export default UserMenu