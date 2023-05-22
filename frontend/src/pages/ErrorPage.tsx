import { Link } from "react-router-dom";
import TopBar from "../components/navigation/TopBar";
import Footer from "../components/navigation/Footer";
import classes from "./ErrorPage.module.css";
function ErrorPage() {
  return (
    <>
      <TopBar />
      <div className={classes.errorMessage}>
        <h1>Nothing here!</h1>
        <h2>Movie you've been looking for is missing!</h2>
        <h3>
          If you've an account, you can login and add it to our database{" "}
          <Link to="">here</Link>
        </h3>
      </div>
      <Footer />
    </>
  );
}

export default ErrorPage;
