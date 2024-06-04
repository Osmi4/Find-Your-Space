import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { Button } from "@nextui-org/react";
import { Link } from 'react-router-dom';

const ReviewList = () => {
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [spaceId, setSpaceId] = useState('');
    const [ownerId, setOwnerId] = useState('');

    const fetchReviews = useCallback(async () => {
        try {
            const token = localStorage.getItem('authToken');
            const response = await axios.post('http://localhost:8080/api/rating/filter', {
                spaceId: spaceId || null,
                ownerId: ownerId || null
            }, {
                headers: { Authorization: `Bearer ${token}` },
                params: { page: 0, size: 10, sort: 'dateAdded,desc' }
            });
            setReviews(response.data.content); // Assuming the reviews are paginated and in the `content` field
            setLoading(false);
        } catch (error) {
            setError(error.message);
            setLoading(false);
        }
    }, [spaceId, ownerId]);

    useEffect(() => {
        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchReviews();
        }
    }, [isAuthenticated, getIdTokenClaims, loginWithRedirect, fetchReviews]);

    const handleDeleteReview = async (reviewId) => {
        try {
            const token = localStorage.getItem('authToken');
            await axios.delete(`http://localhost:8080/api/rating/${reviewId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setReviews(reviews.filter(review => review.ratingId !== reviewId));
        } catch (error) {
            setError(error.message);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="p-4">
            <div className="mb-4">
                <label className="block text-gray-700">Space ID</label>
                <input
                    type="text"
                    className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-opacity-50"
                    value={spaceId}
                    onChange={e => setSpaceId(e.target.value)}
                />
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Owner ID</label>
                <input
                    type="text"
                    className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-opacity-50"
                    value={ownerId}
                    onChange={e => setOwnerId(e.target.value)}
                />
            </div>
            <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md" onClick={fetchReviews}>Apply Filters</Button>
            <div className="mt-6 overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                    <tr>
                        <th className="py-2 px-4 border-b border-gray-200">Rating ID</th>
                        <th className="py-2 px-4 border-b border-gray-200">Score</th>
                        <th className="py-2 px-4 border-b border-gray-200">Comment</th>
                        <th className="py-2 px-4 border-b border-gray-200">Date Added</th>
                        <th className="py-2 px-4 border-b border-gray-200">Space ID</th>
                        <th className="py-2 px-4 border-b border-gray-200">User ID</th>
                        <th className="py-2 px-4 border-b border-gray-200">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {reviews.map(review => (
                        <tr key={review.ratingId}>
                            <td className="py-2 px-4 border-b border-gray-200">{review.ratingId}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{review.score}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{review.comment}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{new Date(review.dateAdded).toLocaleDateString()}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{review.spaceId}</td>
                            <td className="py-2 px-4 border-b border-gray-200">
                                <Link to={`/user/${review.userId}`}>{review.userId}</Link>
                            </td>
                            <td className="py-2 px-4 border-b border-gray-200">
                                <Button className="bg-red-500 text-white px-4 py-2 rounded-md shadow-md" onClick={() => handleDeleteReview(review.ratingId)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ReviewList;
