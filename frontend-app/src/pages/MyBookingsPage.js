import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { ClipLoader } from 'react-spinners';

const MyBookingsPage = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchBookings = async () => {
            const token = localStorage.getItem('authToken');
            setLoading(true); // Start loading
            try {
                const response = await axios.get('http://localhost:8080/api/booking/my-Bookings', {
                    headers: { Authorization: `Bearer ${token}` },
                    params: { page: currentPage, size: 10 }
                });
                setBookings(response.data.content);
                setTotalPages(response.data.totalPages);
                setLoading(false); // End loading
            } catch (error) {
                console.error('Error fetching bookings:', error);
                setLoading(false); // End loading
            }
        };

        fetchBookings();
    }, [currentPage]);

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setCurrentPage(newPage);
        }
    };

    const handleBookingClick = (bookingId) => {
        navigate(`/booking/${bookingId}`);
    };

    const getStatusColor = (status) => {
        switch (status) {
            case 'INQUIRY':
                return 'bg-yellow-100';
            case 'ACCEPTED':
                return 'bg-green-100';
            case 'COMPLETED':
                return 'bg-blue-100';
            default:
                return 'bg-white';
        }
    };

    if (loading) {
        return (
            <div className="flex justify-center items-center h-screen">
                <ClipLoader size={35} color={"#123abc"} loading={loading} />
            </div>
        );
    }

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">My Bookings</h1>
            {bookings.length === 0 ? (
                <p>No bookings found</p>
            ) : (
                <div className="grid grid-cols-1 gap-4">
                    {bookings.map(booking => (
                        <div
                            key={booking.bookingId}
                            className={`${getStatusColor(booking.status)} p-4 rounded-lg shadow-md cursor-pointer`}
                            onClick={() => handleBookingClick(booking.bookingId)}
                        >
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
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 0}
                    className="mx-1 px-4 py-2 bg-gray-300 rounded"
                >
                    Previous
                </button>
                <span className="mx-2 px-4 py-2">{`Page ${currentPage + 1} of ${totalPages}`}</span>
                <button
                    onClick={() => handlePageChange(currentPage + 1)}
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
