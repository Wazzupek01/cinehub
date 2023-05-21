import { Outlet } from "react-router-dom";
import Footer from "../components/navigation/Footer";
import TopBar from "../components/navigation/TopBar";

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
