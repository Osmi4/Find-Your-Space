import React from 'react';
import { NextUIProvider } from "@nextui-org/react";
import {Routes, Route, useNavigate} from 'react-router-dom';
import Nav from "./components/Nav";
import HomePage from "./pages/HomePage";
import ReportList from "./pages/ReportList";
import ReviewList from "./pages/ReviewList";
import UserPage from "./pages/UserPage";
import MessagePersonPage from "./pages/MessagePerPersonPage";
import UserListPage from "./pages/UserListPage";
import SpacePage from "./pages/SpacePage";
import SpaceListPage from "./pages/SpaceListPage";
import BookingListPage from "./pages/BookingListPage";
import BookingPage from "./pages/BookingPage";
import MessagePage from "./pages/MessagePage";

function App() {
    const navigate = useNavigate();
    return (
        <NextUIProvider navigate={navigate}>
            <Nav />
            <div>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/reports" element={<ReportList />} />
                    <Route path="/reviews" element={<ReviewList />} />
                    <Route path="/user/:userId" element={<UserPage />} />
                    <Route path="/message" element={<MessagePage/>}/>
                    <Route path="/message/:id" element={<MessagePersonPage />} />
                    <Route path="/users" element={<UserListPage />} />
                    <Route path="/spaces" element={<SpaceListPage />} />
                    <Route path="/space/:spaceId" element={<SpacePage />} />
                    <Route path="/bookings" element={<BookingListPage />} />
                    <Route path="/booking/:bookingId" element={<BookingPage />} />
                </Routes>
            </div>
        </NextUIProvider>
    );
}

export default App;
