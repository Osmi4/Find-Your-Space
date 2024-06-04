import React from 'react';
import { NextUIProvider } from "@nextui-org/react";
import { Routes, Route } from 'react-router-dom';
import Nav from "./components/Nav";
import HomePage from "./pages/HomePage";
import ReportList from "./pages/ReportList";
import ReviewList from "./pages/ReviewList";

function App() {
    return (
        <NextUIProvider>
            <Nav />
            <div>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/reports" element={<ReportList />} />
                    <Route path="/reviews" element={<ReviewList />} />
                </Routes>
            </div>
        </NextUIProvider>
    );
}

export default App;
