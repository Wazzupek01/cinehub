import { Outlet } from "react-router-dom";
import Footer from "../components/Footer";
import TopBar from "../components/TopBar";

function RootLayout() {
  return (
    <>
      <TopBar />
      <main>
        <Outlet />
      </main>
      <Footer />
    </>
  );
}

export default RootLayout;
