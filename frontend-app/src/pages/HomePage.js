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
            <h1 className="text-4xl bold text-center font-semibold mt-[50px]">Find a space for your advertisement</h1>
            <p className="text-gray-400 text-center py-[20px] mx-[490px]">"Find Your Space" simplifies advertising by connecting businesses with ideal ad locations and helping space owners monetize their assets effortlessly.</p>
            {/* <Link className="border-1 py-[10px] px-[20px] text-xs text-extrabold border-black hover:bg-gray-300" to="/find">Search spaces</Link> */}
            <Button color="primary" variant="bordered" className="bg-black" onClick={searchSpaces}>Search spaces</Button>
            <img src={HomePagePic} className="w-[50%] h-[35%] bg-center mt-[30px] block mx-auto" alt=""/> 
        </div>
    );
}
export default HomePage;