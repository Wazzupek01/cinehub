import {
  CarouselProvider,
  Slider,
  Slide,
  ButtonBack,
  ButtonNext,
  Image,
} from "pure-react-carousel";
import "pure-react-carousel/dist/react-carousel.es.css";
import classes from "./MainPage.module.css";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import { Link, useLoaderData } from "react-router-dom";
import { useEffect, useState } from "react";

function MainPage() {

  const data = useLoaderData();

  const [movies, setMovies] = useState([]);
  
  const retrieveMovies = async() => {
    const m: [] = await data;
    setMovies(m);
  }

  

  useEffect(() => {
    retrieveMovies();

  }, []);
  let it = -1;

  return (
    <div>
      <CarouselProvider
        naturalSlideWidth={100}
        naturalSlideHeight={35}
        totalSlides={2}
      >
        <Slider>
          {movies.map((movie) => (
            <Link to="/" key={movie.id}>
              <Slide index={it++}>
                <Link to=""><Image src={movie.posterUrl} className={classes.image} hasMasterSpinner={true}></Image></Link>
              </Slide>
            </Link>
          ))}
        </Slider>
        <div className={classes.button_container}>
          <ButtonBack className={classes.navigation_button}>
            <ChevronLeftIcon />
          </ButtonBack>
          <ButtonNext className={classes.navigation_button}>
            <ChevronRightIcon />
          </ButtonNext>
        </div>
      </CarouselProvider>
      <h1>
        Enhance your experience and <Link to="">Login</Link>
      </h1>
    </div>
  );
}

export default MainPage;
