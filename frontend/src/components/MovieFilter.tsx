import React from 'react'

function MovieFilter() {
  return (
    <div>
        <label htmlFor="orderby">Order by: </label>
        <select id="orderby">
            <option>releaseYear</option>
            <option>runtime</option>
            <option>rating</option>
        </select>
        <label htmlFor="filter">Filter: </label>
        <select id="filter">
            <option>genre</option>
            <option>director</option>
            <option>runtime</option>
        </select>
    </div>
  )
}

export default MovieFilter