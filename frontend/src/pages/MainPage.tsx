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
import { doesHttpOnlyCookieExist } from "../helpers/doesHttpOnlyCookieExist";


function MainPage() {


  const data = useLoaderData();

  const [movies, setMovies] = useState([]);
  
  const retrieveMovies = async() => {
    const m: [any] = await data;
    setMovies(m);
  }

  useEffect(() => {
    retrieveMovies();
  }, []);
  let it: number = 0;

  return (
    <div className={classes.mainPage_container}>
      <CarouselProvider
        naturalSlideWidth={100}
        naturalSlideHeight={35}
        totalSlides={movies.length}
      >
        <Slider>
          {movies.map((movie) => {
            it += 1;
            return <Link to="/" key={movie.id}>
              <Slide index={it}>
                <Image src={movie.posterUrl} className={classes.image} hasMasterSpinner={true}></Image>
              </Slide>
            </Link>
})}
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
      {!doesHttpOnlyCookieExist("jwt") &&
      <h1>
        Enhance your experience and <Link to="/login">Login</Link>
      </h1>}
    </div>
  );
}

export default MainPage;
