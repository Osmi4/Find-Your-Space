import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAuth0 } from '@auth0/auth0-react';
import ReviewModal from "../components/ReviewModal";
import axios from "axios";

const ReviewsPage = () => {
    const { spaceId } = useParams();
    const { isAuthenticated } = useAuth0();
    const [reviews, setReviews] = useState([]);
    const [averageRating, setAverageRating] = useState(0);
    const [ratingsCount, setRatingsCount] = useState({ 1: 0, 2: 0, 3: 0, 4: 0, 5: 0 });

    useEffect(() => {
        const fetchReviews = async () => {
            const token = localStorage.getItem('authToken');
            if (!token) {
                console.error("No auth token found");
                return;
            }

            try {
                const response = await axios.post("http://localhost:8080/api/rating/filter", {
                    spaceId: spaceId
                }, {
                    params: { page: 0, size: 10 },
                    headers: { Authorization: `Bearer ${token}` }
                });

                const fetchedReviews = response.data.content;
                setReviews(fetchedReviews);
                calculateAverageRating(fetchedReviews);
                calculateRatingsCount(fetchedReviews);
            } catch (error) {
                console.error("Error fetching reviews:", error);
            }
        };

        fetchReviews();
    }, [spaceId]);

    const calculateAverageRating = (reviews) => {
        if (reviews.length === 0) {
            setAverageRating(0);
            return;
        }
        const totalScore = reviews.reduce((total, review) => total + review.score, 0);
        setAverageRating((totalScore / reviews.length).toFixed(1));
    };

    const calculateRatingsCount = (reviews) => {
        const count = { 1: 0, 2: 0, 3: 0, 4: 0, 5: 0 };
        reviews.forEach(review => {
            count[review.score] += 1;
        });
        setRatingsCount(count);
    };

    return (
        <div className="flex flex-col lg:flex-row font-semibold items-center text-center lg:items-start lg:text-left">
            <div className="lg:ml-[200px] lg:mt-[70px] lg:mr-[150px] mt-[4vh]">
                <h1 className="text-2xl lg:text-4xl mb-[2vh]">Reviews</h1>
                <div className="flex gap-[5px] justify-center lg:justify-normal">
                    {[...Array(5)].map((_, index) => (
                        <span key={index} className="lg:text-4xl text-2xl">
                            {index < Math.round(averageRating) ? "★" : "☆"}
                        </span>
                    ))}
                </div>
                <p className="text-lg lg:text-2xl my-[2vh]">{reviews.length} reviews</p>
                {[5, 4, 3, 2, 1].map(star => (
                    <div key={star} className="flex items-center justify-center lg:justify-normal mb-[10px] w-[250px] text-sm lg:text-md">
                        <p>{star} stars</p>
                        <hr className="border-black w-[100px] mx-[10px]" />
                        <p>({ratingsCount[star]})</p>
                    </div>
                ))}
            </div>
            <hr className="border-black w-[90vw] text-center mt-[2vh] lg:hidden" />
            <div className="lg:mt-[200px] mt-[4vh]">
                {reviews.map(review => (
                    <div key={review.ratingId} className="mb-[50px]">
                        <div className="flex gap-[5px] justify-between ml-[3vw] lg:ml-0">
                            <div>
                                {[...Array(5)].map((_, index) => (
                                    <span key={index} className="text-2xl">
                                        {index < review.score ? "★" : "☆"}
                                    </span>
                                ))}
                            </div>
                            <div className="xl:mr-[24vw] mr-[3vw]">
                                <p className="font-normal">{new Date(review.dateAdded).toLocaleDateString()}</p>
                                <p className="text-right">{review.userId}</p>
                            </div>
                        </div>
                        <h1 className="text-xl mb-[20px] mx-[0.1vw]">"{review.comment.split(': ')[0]}"</h1>
                        <p className="lg:mr-[26vw] font-medium mx-[3vw]">{review.comment.split(': ')[1]}</p>
                        <div className="mt-[20px] font-medium text-sm flex">
                            <p className="mr-[2px]">Was this review helpful?</p>
                            <button className="mr-[2px]">Yes (1)</button>
                            <button>No (0)</button>
                        </div>
                    </div>
                ))}
                {!isAuthenticated && <p className="mt-[20px] text-red-600">Please log in to write a review</p>}
                {isAuthenticated && <ReviewModal />}
            </div>
        </div>
    );
};

export default ReviewsPage;
