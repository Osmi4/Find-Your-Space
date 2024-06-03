import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const MyBookingsPage = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBookings = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const response = await axios.get('http://localhost:8080/api/booking/my-Bookings', {
                    headers: { Authorization: `Bearer ${token}` },
                    params: { page: currentPage, size: 10 }
                });
                setBookings(response.data.content);
                setTotalPages(response.data.totalPages);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching bookings:', error);
                setLoading(false);
            }
        };

        fetchBookings();
    }, [currentPage]);

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
    };

    if (loading) return <div>Loading...</div>;

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">My Bookings</h1>
            {bookings.length === 0 ? (
                <p>No bookings found</p>
            ) : (
                <div className="grid grid-cols-1 gap-4">
                    {bookings.map(booking => (
                        <div key={booking.bookingId} className="bg-white p-4 rounded-lg shadow-md">
                            <p className="text-lg font-semibold">Booking ID: {booking.bookingId}</p>
                            <p className="text-gray-600">Start Date: {new Date(booking.startDateTime).toLocaleString()}</p>
                            <p className="text-gray-600">End Date: {new Date(booking.endDateTime).toLocaleString()}</p>
                            <p className="text-gray-600">Description: {booking.description}</p>
                            <p className="text-gray-600">Status: {booking.status}</p>
                        </div>
                    ))}
                </div>
            )}
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

export default MyBookingsPage;
