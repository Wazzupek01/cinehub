import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from "./pages/RootLayout";
import BrowseMoviesPage from "./pages/BrowseMoviesPage";
import MainPage from "./pages/MainPage";
import TopRatedPage from "./pages/TopRatedPage";

import MovieService from './services/MovieService';

function App() {

  const movieService = new MovieService();

  const router = createBrowserRouter([
    {
      path: "/",
      element: <RootLayout />,
      children: [
        {
          index: true,
          element: <MainPage />,
          loader: async () => {
            return movieService.loadCarouselMovies();
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
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App;
