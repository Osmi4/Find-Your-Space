import {NextUIProvider} from "@nextui-org/react";
import { useNavigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import { Route,Routes } from 'react-router-dom';
import Nav from "./Nav";
import HomePage from "./pages/HomePage";
import FindSpacePage from "./pages/FindSpacePage";
import SpacePage from "./pages/SpacePage";
import ReviewsPage from "./pages/ReviewsPage";
import CheckoutPage from "./pages/CheckoutPage";
import RentPage from "./pages/RentPage";
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
          </Routes>
        </div>
        {/* put the footer */}
        <></> 
    </NextUIProvider>
  );
}

export default App;
