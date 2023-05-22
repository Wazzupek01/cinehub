import Slider from "@mui/material/Slider";
import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import classes from "./MovieFilter.module.css";

const genres: string[] = [
  'Action',      'Adventure', 'Animation',
  'Biography',   'Comedy',    'Crime',
  'Documentary', 'Drama',     'Family',
  'Fantasy',     'Film-Noir', 'History',
  'Horror',      'Music',     'Musical',
  'Mystery',     'News',      'Romance',
  'Sci-Fi',      'Short',     'Sport',
  'Talk-Show',   'Thriller',  'War',
  'Western'
];

function MovieFilter(props: any) {
  const [orderBy, setOrderBy] = useState<string>("releaseYear");
  const [filterBy, setFilterBy] = useState<string>("director");
  const [filterValue, setFilterValue] = useState<string>("");
  const [isAscending, setIsAscending] = useState<string>("true");
  const [sliderValue, setSliderValue] = useState<number[]>([3, 450]);

  const location = useLocation().pathname;
  const pathVariables = location.split('/');
  console.log(location);
  console.log(pathVariables[2]);
  const searchInfo = 
  `Filtered by ${pathVariables[3]} "${pathVariables[4]}": ordered by ${pathVariables[2]} 
  ${pathVariables[5] === "true" ? "ascending" : "descending"}`;

  function sliderValueText(value: number) {
    return `${value}`;
  }

  const handleSliderChange = (event: Event, newValue: number | number[]) => {
    setSliderValue(newValue as number[]);
    if(filterBy === "runtime")
      setFilterValue(`${sliderValue[0]}-${sliderValue[1]}`);
  };

  return (
    <>
    <div className={classes.movieFilter_container}>
      <label htmlFor="orderby">Order by: </label>
      <select
        id="orderby"
        value={orderBy}
        onChange={(event) => {
          setOrderBy(event.target.value);
        }}
      >
        <option value="releaseYear">releaseYear</option>
        <option value="runtime">runtime</option>
        <option value="rating">rating</option>
      </select>
      <label htmlFor="filter">Filter: </label>
      <select
        id="filter"
        value={filterBy}
        onChange={(event) => {
          setFilterBy(event.target.value);
        }}
      >
        <option value="genre">genre</option>
        <option value="director">director</option>
        <option value="runtime">runtime</option>
      </select>
      {filterBy === "director" && (
        <input
          type="text"
          value={filterValue}
          onChange={(event) => {
            setFilterValue(event.target.value);
          }}
        ></input>
      )}
      {filterBy === "runtime" && (
        <Slider
          className={classes.runtimeSlider}
          min={3}
          max={450}
          step={1}
          getAriaLabel={() => "Runtime range"}
          value={sliderValue}
          onChange={handleSliderChange}
          valueLabelDisplay="auto"
          getAriaValueText={sliderValueText}
        />
      )}
      {filterBy === "genre" && (
        <select value={filterValue} onChange={(event) => {
          setFilterValue(event.target.value);
        }}>
          {genres.map(genre => (<option value={genre}>{genre}</option>))}
        </select>
      )}
      <label htmlFor="isAscending">Ascending: </label>
      <select
        id="isAscending"
        value={isAscending}
        onChange={(event) => {
          setIsAscending(event.target.value);
        }}
      >
        <option value="true">true</option>
        <option value="false">false</option>
      </select>
      <div className={classes.border_gradient}><Link
        to={`/filter/${orderBy}/${filterBy}/${filterValue}/${isAscending}/0`}
      >
        Search
      </Link> </div>
      
    </div>
    {location.includes('filter') && <h2 className={classes.search_params_display}>{searchInfo}</h2>}
    </>
  );
}

export default MovieFilter;
