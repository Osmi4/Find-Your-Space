import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';
import { Button, Spinner } from '@nextui-org/react';

const BookingListPage = () => {
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);
    const navigate = useNavigate();

    const fetchBookings = useCallback(async () => {
        try {
            const token = localStorage.getItem('authToken');
            const response = await axios.get('http://localhost:8080/api/booking/all', {
                headers: { Authorization: `Bearer ${token}` },
                params: { page, size }
            });
            setBookings(response.data.content);
            setTotalPages(response.data.totalPages);
            setLoading(false);
        } catch (error) {
            setError("Unfortunately, an error occurred while trying to load the list of bookings. Please try again later.");
            setLoading(false);
        }
    }, [page, size]);

    useEffect(() => {
        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchBookings();
        }
    }, [isAuthenticated, getIdTokenClaims, loginWithRedirect, fetchBookings]);

    const handlePageChange = (newPage) => {
        setPage(newPage);
    };

    const handleViewDetails = (bookingId) => {
        navigate(`/booking/${bookingId}`);
    };

    if (loading) {
        return <div className='flex items-center justify-center h-[100vh]'><Spinner label="Loading..." color="primary" labelColor="primary" /></div>;
    }

    if (error) {
        return <div className='flex flex-col w-full h-[100vh] items-center'>
        <h1 className='text-9xl font-bold'>404</h1>
        <p className='text-gray-700'>{error}</p>
    </div>;
    }

    return (
        <div className="p-4">
            <h1 className="text-2xl font-semibold mb-4">Booking List</h1>
            <div className="mt-6 overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                    <tr>
                        <th className="py-2 px-4 border-b border-gray-200">Booking ID</th>
                        <th className="py-2 px-4 border-b border-gray-200">Client</th>
                        <th className="py-2 px-4 border-b border-gray-200">Space</th>
                        <th className="py-2 px-4 border-b border-gray-200">Status</th>
                        <th className="py-2 px-4 border-b border-gray-200">Start Date</th>
                        <th className="py-2 px-4 border-b border-gray-200">End Date</th>
                        <th className="py-2 px-4 border-b border-gray-200">Cost</th>
                        <th className="py-2 px-4 border-b border-gray-200">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {bookings.map(booking => (
                        <tr key={booking.bookingId}>
                            <td className="py-2 px-4 border-b border-gray-200">{booking.bookingId}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{booking.client.firstName} {booking.client.lastName}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{booking.spaceId}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{booking.status}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{new Date(booking.startDateTime).toLocaleDateString()}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{new Date(booking.endDateTime).toLocaleDateString()}</td>
                            <td className="py-2 px-4 border-b border-gray-200">${booking.cost.toFixed(2)}</td>
                            <td className="py-2 px-4 border-b border-gray-200">
                                <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md" onClick={() => handleViewDetails(booking.bookingId)}>View Details</Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <div className="mt-4 flex justify-between">
                <Button
                    className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md"
                    onClick={() => handlePageChange(page - 1)}
                    disabled={page === 0}
                >
                    Previous
                </Button>
                <span>Page {page + 1} of {totalPages}</span>
                <Button
                    className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md"
                    onClick={() => handlePageChange(page + 1)}
                    disabled={page === totalPages - 1}
                >
                    Next
                </Button>
            </div>
        </div>
    );
};

export default BookingListPage;
