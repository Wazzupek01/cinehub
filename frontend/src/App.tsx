import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from "./pages/RootLayout";
import BrowseMoviesPage from "./pages/BrowseMoviesPage";
import MainPage from "./pages/MainPage";
import TopRatedPage from "./pages/TopRatedPage";
import MovieService from "./services/MovieService";
import ErrorPage from "./pages/ErrorPage";
import MoviePage from "./pages/MoviePage";
import AuthenticationPage from "./pages/AuthenticationPage";
import UserService from "./services/UserService";
import UserLists from "./components/profile/UserLists";
import AddMovie from "./components/AddMovie";

function App() {
  const movieService: MovieService = new MovieService();
  const userService: UserService = new UserService();

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
          },
        },
        {
          path: "movie/:id",
          element: <MoviePage />,
          loader: async ({ params }) => {
            if (params.id === undefined) throw Error("Movie not specified");
            return movieService.getMovieById(params.id);
          },
        },
        {
          path: "browse/:page",
          element: <BrowseMoviesPage />,
          loader: async ({ params }) => {
            if (params.page === undefined) throw Error("Page not specified");
            return movieService.getAllMoviesSorted(
              +params.page,
              "ReleaseYear",
              "false"
            );
          },
        },
        {
          path: "top/:page",
          element: <TopRatedPage />,
          loader: async ({ params }) => {
            if (params.page === undefined) throw Error("Page not specified");
            return movieService.getAllMoviesSorted(
              +params.page,
              "rating",
              "false"
            );
          },
        },

        {
          path: "find/:title/:page",
          element: <BrowseMoviesPage />,
          loader: async ({ params }) => {
            if (params.page === undefined || params.title === undefined)
              throw Error("Page not specified");
            return movieService.getMoviesByTitle(+params.page, params.title);
          },
        },
        {
          path: "filter/:orderBy/:filterBy/:filterValue/:isAscending/:page",
          element: <BrowseMoviesPage />,
          loader: async ({ params }) => {
            let data = null;
            if (
              params.page === undefined ||
              params.orderBy === undefined ||
              params.isAscending === undefined ||
              params.filterBy === undefined
            )
              throw new Error("Search parameters undefined");

            if (
              params.orderBy !== "no-filter" &&
              params.filterValue === undefined
            )
              throw new Error("No filter value");

            switch (params.filterBy) {
              case "director":
                data = movieService.getMoviesByDirectorSorted(
                  +params.page,
                  params.orderBy,
                  params.filterValue,
                  params.isAscending
                );
                break;
              case "actor":
                data = movieService.getMoviesByActorSorted(
                  +params.page,
                  params.orderBy,
                  params.filterValue,
                  params.isAscending
                );
                break;
              case "runtime":
                data = movieService.getMoviesByRuntimeSorted(
                  +params.page,
                  params.orderBy,
                  params.filterValue as string,
                  params.isAscending
                );
                break;
              case "genre":
                data = movieService.getMoviesByGenreSorted(
                  +params.page,
                  params.orderBy,
                  params.filterValue,
                  params.isAscending
                );
                break;
              case "no-filter":
                data = movieService.getAllMoviesSorted(
                  +params.page,
                  params.orderBy,
                  params.isAscending
                );
                break;
              case "title":
                data = movieService.getMoviesByTitleSorted(
                  +params.page,
                  params.orderBy,
                  params.filterValue,
                  params.isAscending
                )
            }
            return data;
          },
        },
        {
          path: "/profile/:nickname",
          element: <UserLists />,
          loader: async ({params}) => {
            if(params.nickname === undefined) throw new Error("No nickname defined");
            return await userService.getUserByNickname(params.nickname);
          }
        },
        {
          path: "/add",
          element: <AddMovie />
        }
      ],
    },
    {
      path: "/auth/:method",
      element: <AuthenticationPage />,
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App;
