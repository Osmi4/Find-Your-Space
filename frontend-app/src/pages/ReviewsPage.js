const ReviewsPage = () => {
    return (
        <div className="flex font-semibold">
            <div className="ml-[200px] mt-[70px] mr-[150px]">
                <h1 className="text-4xl mb-[50px]">Reviews</h1>
                <div className=" flex gap-[5px]">
                    <span className="text-4xl">&#9733;</span>
                    <span className="text-4xl">&#9733;</span>
                    <span className="text-4xl">&#9733;</span>
                    <span className="text-4xl">&#9733;</span>
                    <span className="text-4xl">&#9733;</span>
                </div>
                <p className="text-2xl my-[20px]">11 reviews</p>
                <div className="flex items-center mb-[10px] w-[250px]"><p>5 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(1)</p></div>
                <div className="flex items-center mb-[10px] w-[250px]"><p>4 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(0)</p></div>
                <div className="flex items-center mb-[10px] w-[250px]"><p>3 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(1)</p></div>
                <div className="flex items-center mb-[10px] w-[250px]"><p>2 stars</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(0)</p></div>
                <div className="flex items-center mb-[10px] w-[250px]"><p>1 star</p><hr className="border-black w-[100px] mx-[10px]"></hr> <p>(0)</p></div>
            </div>
            <div className="mt-[200px]">          
                <div className="">
                    <div className="flex gap-[5px] justify-between">
                        <div>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                        </div>
                        <div className="mr-[350px]">
                            <p className="font-normal">October 21, 2022</p>
                            <p className="text-right">Nick A</p>
                        </div>
                        
                    </div>
                    <h1 className="text-2xl mb-[20px]">"Amazing place, pleasant owner!"</h1>
                    <p className="mr-[500px] font-medium">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <div className="mt-[20px] font-medium text-sm flex">
                        <p className="mr-[2px]">Was this review helpful?</p>
                        <button className="mr-[2px]">Yes (1)</button>
                        <button>No (0)</button>
                        <div className="border-l-[2px] border-black mx-[20px]"></div>
                        <button>Flag as inappropriate</button>
                    </div>
                </div>
                <div className="mt-[50px]">
                    <div className="flex gap-[5px] justify-between">
                        <div>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                            <span className="text-2xl">&#9733;</span>
                        </div>
                        <div className="mr-[350px]">
                            <p className="font-normal">January 11, 2020</p>
                            <p className="text-right">John M</p>
                        </div>
                        
                    </div>
                    <h1 className="text-2xl mb-[20px]">"Overall not bad, but the place is old"</h1>
                    <p className="mr-[500px] font-medium">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <div className="mt-[20px] font-medium text-sm flex">
                        <p className="mr-[2px]">Was this review helpful?</p>
                        <button className="mr-[2px]">Yes (5)</button>
                        <button>No (1)</button>
                        <div className="border-l-[2px] border-black mx-[20px]"></div>
                        <button>Flag as inappropriate</button>
                    </div>
                </div>
            </div>  

        </div>  
    )
 };

export default ReviewsPage;