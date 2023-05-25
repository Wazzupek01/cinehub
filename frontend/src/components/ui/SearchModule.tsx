import { useState } from "react";
import classes from "./SearchModule.module.css";
import SearchIcon from "@mui/icons-material/Search";
import { useNavigate } from "react-router-dom";
function SearchModule() {
  const [searchedName, setSearchedName] = useState("");
  const [showSearchField, setShowSearchField] = useState(false);
  let navigate = useNavigate();

  const handleSearch = () => {
    if (searchedName.trim().length > 0) {
      let path = "/find/" + searchedName + "/0";
      navigate(path);
    }
    setShowSearchField(!showSearchField);
  };

  return (
    <div className={classes.border_gradient}>
      <div className={classes.search_module}>
        {showSearchField && (
          <input
            type="text"
            value={searchedName}
            onChange={(event) => {
              setSearchedName(event.target.value);
            }}
          />
        )}
        <div onClick={handleSearch}>
          <SearchIcon fontSize="small" />
        </div>
      </div>
    </div>
  );
}

export default SearchModule;
