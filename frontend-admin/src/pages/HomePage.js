import React from 'react';
import HomePagePic from "../images/HomePagePic.png";
import { Button } from "@nextui-org/react";
import { useNavigate } from "react-router-dom";

const HomePage = () => {

    return (
        <div className="text-center justify-center mt-[2vh]">
            <h1 className="text-4xl font-bold">Welcome to Admin Page for FindYourSpace!</h1>
            <img src={HomePagePic} alt="Home Page" className="mx-auto my-4 w-[60vw]" />
            <p className="text-lg font-semibold">Please use tabs in navigation bar to manage the website.</p>
        </div>
    );
}

export default HomePage;
