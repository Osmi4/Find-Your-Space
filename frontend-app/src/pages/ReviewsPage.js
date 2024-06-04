import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAuth0 } from '@auth0/auth0-react';
import ReviewModal from "../components/ReviewModal";
import axios from "axios";
import { ClipLoader } from "react-spinners";
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import { Card } from '@nextui-org/react';

const ReviewsPage = () => {
    const { id } = useParams();
    const { isAuthenticated } = useAuth0();
    const [reviews, setReviews] = useState([]);
    const [averageRating, setAverageRating] = useState(0);
    const [ratingsCount, setRatingsCount] = useState({ 1: 0, 2: 0, 3: 0, 4: 0, 5: 0 });
    const [spaceDetails, setSpaceDetails] = useState(null);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        const fetchSpaceDetails = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.get(`http://localhost:8080/api/space/${id}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setSpaceDetails(response.data);
            } catch (error) {
                console.error("Error fetching space details:", error);
                toast.error("Error fetching space details.");
            }
        };

        fetchSpaceDetails();
    }, [id]);

    const fetchReviews = async () => {
        const token = localStorage.getItem('authToken');
        if (!token) {
            console.error("No auth token found");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/rating/filter", {
                spaceId: id
            }, {
                params: { page: currentPage, size: 10 },
                headers: { Authorization: `Bearer ${token}` }
            });

            const fetchedReviews = response.data.content;
            await fetchUsernames(fetchedReviews);
            setReviews(fetchedReviews);
            setTotalPages(response.data.totalPages);
            calculateAverageRating(fetchedReviews);
            calculateRatingsCount(fetchedReviews);
            setLoading(false);
        } catch (error) {
            console.error("Error fetching reviews:", error);
            toast.error("Error fetching reviews.");
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchReviews();
    }, [id, currentPage]);

    const fetchUsernames = async (reviews) => {
        const token = localStorage.getItem('authToken');
        for (const review of reviews) {
            try {
                const response = await axios.get(`http://localhost:8080/api/user/${review.userId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                review.username = `${response.data.firstName} ${response.data.lastName}`;
            } catch (error) {
                console.error(`Error fetching user details for userId ${review.userId}:`, error);
                review.username = 'Unknown User';
            }
        }
    };

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

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
        setLoading(true);
    };

    const handleReviewAdded = () => {
        setLoading(true);
        fetchReviews();
    };

    if (loading) {
        return (
            <div className="flex justify-center items-center h-screen">
                <ClipLoader size={35} color={"#123abc"} loading={loading} />
            </div>
        );
    }

    return (
        <div className="flex flex-col lg:flex-row font-semibold items-center text-center lg:items-start lg:text-left">
            <ToastContainer />
            {spaceDetails && (
                <Card className="lg:ml-[200px] lg:mt-[70px] lg:mr-[150px] mt-[4vh] p-6 bg-gray-100 rounded-lg shadow-lg">
                    <h1 className="text-2xl lg:text-4xl mb-[2vh]">{spaceDetails.spaceName}</h1>
                    <p className="text-lg">{spaceDetails.spaceDescription}</p>
                    <p className="mt-4"><strong>Location:</strong> {spaceDetails.spaceLocation}</p>
                    <p className="mt-2"><strong>Size:</strong> {spaceDetails.spaceSize} sq ft</p>
                    <p className="mt-2"><strong>Price:</strong> ${spaceDetails.spacePrice}</p>
                    <p className="mt-2"><strong>Availability:</strong> {spaceDetails.availability}</p>
                    <p className="mt-2"><strong>Date Added:</strong> {new Date(spaceDetails.dateAdded).toLocaleDateString()}</p>
                    <p className="mt-2"><strong>Date Updated:</strong> {new Date(spaceDetails.dateUpdated).toLocaleDateString()}</p>
                </Card>
            )}
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
                        <div className="flex gap-[30px] justify-between ml-[3vw] lg:ml-0">
                            <div>
                                {[...Array(5)].map((_, index) => (
                                    <span key={index} className="text-2xl">
                                        {index < review.score ? "★" : "☆"}
                                    </span>
                                ))}
                            </div>
                            <div className="xl:mr-[24vw] mr-[3vw]">
                                <p className="font-semibold">{new Date(review.dateAdded).toLocaleDateString()}</p>
                                <p className="text-right">{review.username || 'Loading...'}</p>
                            </div>
                        </div>
                        <h1 className="text-xl mb-[20px] mx-[0.1vw]">"{review.comment.split(': ')[0]}"</h1>
                        <p className="lg:mr-[26vw] font-medium mx-[3vw]">{review.comment.split(': ')[1]}</p>
                    </div>
                ))}
                {!isAuthenticated && <p className="mt-[20px] text-red-600">Please log in to write a review</p>}
                {isAuthenticated && <ReviewModal spaceId={id} onReviewAdded={handleReviewAdded} />}
            </div>
            <div className="flex justify-center mt-6">
                <button
                    onClick={() => handlePageChange(Math.max(0, currentPage - 1))}
                    disabled={currentPage === 0}
                    className="mx-1 px-4 py-2 bg-gray-300 rounded"
                >
                    Previous
                </button>
                <button
                    onClick={() => handlePageChange(Math.min(totalPages - 1, currentPage + 1))}
                    disabled={currentPage === totalPages - 1}
                    className="mx-1 px-4 py-2 bg-gray-300 rounded"
                >
                    Next
                </button>
            </div>
        </div>
    );
};

export default ReviewsPage;
