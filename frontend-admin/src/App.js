import React from 'react';
import { NextUIProvider } from "@nextui-org/react";
import { Routes, Route } from 'react-router-dom';
import Nav from "./components/Nav";
import HomePage from "./pages/HomePage";
import ReportList from "./pages/ReportList";

function App() {
    return (
        <NextUIProvider>
            <Nav />
            <div>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/reports" element={<ReportList />} />
                </Routes>
            </div>
        </NextUIProvider>
    );
}

export default App;
