//import { useParams } from "react-router-dom";
import ReviewModal from "../components/ReviewModal";
import { useAuth0 } from '@auth0/auth0-react';

const ReviewsPage = () => {
    //let { id } = useParams();

    //const reviews = fetch("http://localhost:8080/api/rating",{method:"GET"}).then((response) => response.json());
    //reviews = reviews.content.filter((review, index) => (review.spaceId === id));
    const { isAuthenticated} = useAuth0();

    return (
        <div className="flex flex-col lg:flex-row font-semibold items-center text-center lg:items-start lg:text-left">
            <div className="lg:ml-[200px] lg:mt-[70px] lg:mr-[150px] mt-[4vh]">
                <h1 className="text-2xl lg:text-4xl mb-[2vh]">Reviews</h1>
                <div className="flex gap-[5px] justify-center lg:justify-normal">
                    <span className="lg:text-4xl text-2xl">&#9733;</span>
                    <span className="lg:text-4xl text-2xl">&#9733;</span>
                    <span className="lg:text-4xl text-2xl">&#9733;</span>
                    <span className="lg:text-4xl text-2xl">&#9733;</span>
                    <span className="lg:text-4xl text-2xl">&#9733;</span>
                </div>
                <p className="text-lg lg:text-2xl my-[2vh]">2 reviews</p>
                <div className="flex items-center justify-center lg:justify-normal mb-[10px] w-[250px] text-sm lg:text-md"><p>5 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(1)</p></div>
                <div className="flex items-center justify-center lg:justify-normal mb-[10px] w-[250px] text-sm lg:text-md"><p>4 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(0)</p></div>
                <div className="flex items-center justify-center lg:justify-normal mb-[10px] w-[250px] text-sm lg:text-md"><p>3 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(1)</p></div>
                <div className="flex items-center justify-center lg:justify-normal mb-[10px] w-[250px] text-sm lg:text-md"><p>2 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(0)</p></div>
                <div className="flex items-center justify-center lg:justify-normal mb-[10px] w-[250px] text-sm lg:text-md"><p>1 star</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(0)</p></div>
            </div>
            <hr className="border-black w-[90vw] text-center mt-[2vh] lg:hidden"></hr>
            <div className="lg:mt-[200px] mt-[4vh]">          
                <div className="">
                    <div className="flex gap-[5px] justify-between ml-[3vw] lg:ml-0">
                        <div>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                        </div>
                        <div className="xl:mr-[24vw] mr-[3vw]">
                            <p className="font-normal">October 21, 2022</p>
                            <p className="text-right">Nick A</p>
                        </div>
                        
                    </div>
                    <h1 className="text-xl mb-[20px] mx-[0.1vw]">"Amazing place, pleasant owner!"</h1>
                    <p className="lg:mr-[26vw] font-medium mx-[3vw]">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <div className="mt-[20px] font-medium text-sm flex">
                        <p className="mr-[2px]">Was this review helpful?</p>
                        <button className="mr-[2px]">Yes (1)</button>
                        <button>No (0)</button>
                    </div>
                </div>
                <div className="mt-[50px]">
                    <div className="flex gap-[5px] justify-between ml-[3vw] lg:ml-0">
                        <div>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                        </div>
                        <div className="xl:mr-[24vw] mr-[3vw]">
                            <p className="font-normal">January 11, 2020</p>
                            <p className="text-right">John M</p>
                        </div>
                        
                    </div>
                    <h1 className="text-xl mb-[20px] mx-[0.1vw]">"Overall not bad, but the place is old"</h1>
                    <p className="lg:mr-[26vw] font-medium mx-[3vw]">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <div className="mt-[20px] font-medium text-sm flex mb-[2vh]">
                        <p className="mr-[2px]">Was this review helpful?</p>
                        <button className="mr-[2px]">Yes (5)</button>
                        <button>No (1)</button>
                    </div>
                </div>
                {!isAuthenticated && <p className="mt-[20px] text-red-600">Please log in to write a review</p>}
                {isAuthenticated && <ReviewModal />}
            </div>  

        </div>  
    )
 };

export default ReviewsPage;