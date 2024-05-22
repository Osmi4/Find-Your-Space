import {NextUIProvider} from "@nextui-org/react";
import { useNavigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import { Route,Routes } from 'react-router-dom';
import Nav from "./components/Nav";
import HomePage from "./pages/HomePage";
import FindSpacePage from "./pages/FindSpacePage";
import SpacePage from "./pages/SpacePage";
import ReviewsPage from "./pages/ReviewsPage";
import CheckoutPage from "./pages/CheckoutPage";
import RentPage from "./pages/RentPage";
import AboutPage from "./pages/AboutPage";
function App() {
  const navigate = useNavigate();

  return (
    <NextUIProvider navigate={navigate}>
      <Nav/>
        <div>
          <Routes>    
            <Route path="/" element={<HomePage/>}/>    
            <Route path="/login"  element={<LoginPage/>} />
            <Route path="/find"  element={<FindSpacePage/>} />
            <Route path="space/:id"  element={<SpacePage/>} />
            <Route path="space/:id/reviews" element={<ReviewsPage/>} />
            <Route path="space/:id/checkout/:days" element={<CheckoutPage/>} />
            <Route path="/rent" element={<RentPage/>} />
            <Route path="/about" element={<AboutPage/>} />
          </Routes>
        </div>
        <></> 
    </NextUIProvider>
  );
}

export default App;
