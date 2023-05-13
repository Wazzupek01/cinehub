import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from "./pages/RootLayout";
import BrowseMoviesPage from "./pages/BrowseMoviesPage";
import { PanoramaSharp } from '@mui/icons-material';

const router = createBrowserRouter([
  {path: "/", element: <RootLayout />, children: [
    {index: true, element: <div>MainPage</div>},
    {path: "browse/:page", element: <BrowseMoviesPage />, loader: async ({params}) => {
      const response = await fetch("http://localhost:8080/movies/all/"+params.page+"/releaseYear/false");
      const data = response;
      return data;
    }},

    {path: "find/:name/:page", element: <BrowseMoviesPage />, loader: async ({params}) => {
      const response = await fetch("http://localhost:8080/movies/title/"+ params.name + "/" + params.page);
      const data = response;
      return data;
    }}
  ]}
])


function App() {

  return (
    <RouterProvider router={router} />
  )
}

export default App
