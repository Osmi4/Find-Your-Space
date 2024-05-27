import HomePagePic from "../images/HomePagePic.png";
import { Button } from "@nextui-org/react";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
    const navigate = useNavigate();
    const searchSpaces = () => {
        navigate('/find');
    }   
    return (
        <div className="text-center justify-center">
            <h1 className="lg:text-4xl text-2xl bold text-center font-semibold lg:mt-[50px] mt-[25px] mx-[20px]">Find a space for your advertisement</h1>
            <p className="text-gray-400 text-center lg:py-[20px] py-[10px] mx-[25vw] text-xs lg:text-lg">"Find Your Space" simplifies advertising by connecting businesses with ideal ad locations and helping space owners monetize their assets effortlessly.</p>
            <Button color="primary" variant="bordered" className="bg-black" onClick={searchSpaces}>Search spaces</Button>
            <img src={HomePagePic} className="lg:w-[50%] lg:h-[35%] bg-center mt-[30px] block mx-auto w-[80%] h-[35%]" alt=""/> 
        </div>
    );
}
export default HomePage;