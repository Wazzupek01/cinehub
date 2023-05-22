import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from "./pages/RootLayout";
import BrowseMoviesPage from "./pages/BrowseMoviesPage";
import MainPage from "./pages/MainPage";
import TopRatedPage from "./pages/TopRatedPage";

import MovieService from './services/MovieService';
import ErrorPage from "./pages/ErrorPage";
import MoviePage from "./pages/MoviePage";

function App() {

  const movieService = new MovieService();

  const router = createBrowserRouter([
    {
      path: "/",
      element: <RootLayout />,
      errorElement: <ErrorPage />,
      children: [
        {
          index: true,
          element: <MainPage />,
          loader: async () => {
            return movieService.loadCarouselMovies();
          }
        },
        {
          path: "movie/:id",
          element: <MoviePage />,
          loader: async ({params}) => {
            if(params.id === undefined) throw Error("Movie not specified");
            return movieService.getMovieById(params.id);
          }
        },
        {
          path: "browse/:page",
          element: <BrowseMoviesPage />,
          loader: async ({ params }) => {
            if(params.page === undefined) throw Error("Page not specified");
            return movieService.getAllMoviesSorted(+params.page, "ReleaseYear", false);
          },
        },
        {
          path: "top/:page",
          element: <TopRatedPage />,
          loader: async ({ params }) => {
            if(params.page === undefined) throw Error("Page not specified");
            return movieService.getAllMoviesSorted(+params.page, "rating", false);
          }
        },
  
        {
          path: "find/:title/:page",
          element: <BrowseMoviesPage />,
          loader: async ({ params }) => {
            if(params.page === undefined || params.title === undefined) throw Error("Page not specified");
            return movieService.getMoviesByTitle(+params.page, params.title);
          },
        },
        {
          path: "filter/:orderBy/:filterBy/:filterValue/:isAscending/:page",
          element: <BrowseMoviesPage />,
          loader: async ({ params }) => {
            let data = null;
            switch(params.filterBy){
              case "director":
                data = movieService.getMoviesByDirectorSorted(params.page, params.orderBy, params.filterValue, params.isAscending);
                break;
              case "runtime":
                data = movieService.getMoviesByRuntimeSorted(params.page, params.orderBy, params.filterValue, params.isAscending);
                break;
              case "genre":
                data = movieService.getMoviesByGenreSorted(params.page, params.orderBy, params.filterValue, params.isAscending);
              }
            return data;
          },
        }
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App;
