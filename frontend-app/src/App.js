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
import AccountPage from "./pages/AccountPage";
import MySpacesPage from "./pages/MySpacesPage";
import CompleteProfile from "./pages/CompleteProfile";
import SpaceEditPage from "./pages/SpaceEditPage";
import MessagePersonPage from "./pages/MessagePersonPage";
import MessagePage from "./pages/MessagePage";
import ReportPage from "./pages/ReportPage";
import MyReportsPage from "./pages/MyReportsPage";
import BookingsForSpacePage from "./pages/BookingsForSpacePage";
import MyBookingsPage from "./pages/MyBookingsPage";
import BookingPage from "./pages/BookingPage";
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
            <Route path="/checkout/:id/:startDate/:endDate" element={<CheckoutPage />} />
            <Route path="/space/:spaceId/edit" element={<SpaceEditPage />} />
            <Route path="/rent" element={<RentPage/>} />
            <Route path="/about" element={<AboutPage/>} />
            <Route path="/account" element={<AccountPage />}/>
            <Route path="/my_spaces" element={<MySpacesPage />}/>
            <Route path="/complete-profile" element={<CompleteProfile />} />
            <Route path="/message" element={<MessagePage/>}/>
            <Route path="/message/:id" element={<MessagePersonPage/>}/>
            <Route path="report/:id"  element={<ReportPage/>}/>
            <Route path="/my-reports" element={<MyReportsPage/>}/>
            <Route path="/space/:spaceId/bookings" element={<BookingsForSpacePage />} />
            <Route path="/my-bookings" element={<MyBookingsPage />} />
            <Route path="/booking/:bookingId" element={<BookingPage />} />
          </Routes>
        </div>
    </NextUIProvider>
  );
}

export default App;
