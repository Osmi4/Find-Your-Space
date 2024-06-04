import React from 'react';
import HomePagePic from "../images/HomePagePic.png";
import { Button } from "@nextui-org/react";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
    const navigate = useNavigate();
    const searchSpaces = () => {
        navigate('/');
    }

    return (
        <div className="text-center justify-center">
            Nothing
        </div>
    );
}

export default HomePage;
