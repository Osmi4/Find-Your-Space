import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const BookingsForSpacePage = () => {
    const { spaceId } = useParams();
    const [bookings, setBookings] = useState([]);
    const [space, setSpace] = useState(null);
    const [loading, setLoading] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);

    useEffect(() => {
        const fetchBookings = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const [bookingsResponse, spaceResponse] = await Promise.all([
                    axios.get(`http://localhost:8080/api/booking/${spaceId}/bookings`, {
                        headers: { Authorization: `Bearer ${token}` },
                        params: { page: currentPage, size: 10 }
                    }),
                    axios.get(`http://localhost:8080/api/space/${spaceId}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                ]);
                setBookings(bookingsResponse.data.content);
                setSpace(spaceResponse.data);
                setTotalPages(bookingsResponse.data.totalPages);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching data:', error);
                setLoading(false);
            }
        };

        fetchBookings();
    }, [spaceId, currentPage]);

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
    };

    const handleStatusChange = async (bookingId, newStatus) => {
        const token = localStorage.getItem('authToken');
        try {
            const response = await axios.put(
                `http://localhost:8080/api/booking/status/${bookingId}`,
                JSON.stringify(newStatus), // Convert newStatus to JSON string
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json' // Set the correct Content-Type header
                    }
                }
            );
            setBookings(bookings.map(booking =>
                booking.bookingId === bookingId ? { ...booking, status: response.data.status } : booking
            ));
        } catch (error) {
            console.error('Error updating status:', error);
        }
    };

    if (loading) return <div>Loading...</div>;

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Bookings for Space</h1>
            {space && (
                <div className="bg-gray-100 p-4 rounded-lg mb-4">
                    <h2 className="text-xl font-semibold">{space.spaceName}</h2>
                    <p>{space.spaceDescription}</p>
                    <p>Price: ${space.spacePrice}</p>
                </div>
            )}
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
                            {booking.status === 'INQUIRY' && (
                                <div className="mt-2">
                                    <button
                                        onClick={() => handleStatusChange(booking.bookingId, 'ACCEPTED')}
                                        className="bg-green-500 text-white px-4 py-2 rounded mr-2"
                                    >
                                        Accept
                                    </button>
                                    <button
                                        onClick={() => handleStatusChange(booking.bookingId, 'REJECTED')}
                                        className="bg-red-500 text-white px-4 py-2 rounded"
                                    >
                                        Reject
                                    </button>
                                </div>
                            )}
                            {/*{booking.status === 'ACCEPTED' && (*/}
                            {/*    <div className="mt-2">*/}
                            {/*        <button*/}
                            {/*            onClick={() => handleStatusChange(booking.bookingId, 'COMPLETED')}*/}
                            {/*            className="bg-blue-500 text-white px-4 py-2 rounded mr-2"*/}
                            {/*        >*/}
                            {/*            Complete*/}
                            {/*        </button>*/}
                            {/*        <button*/}
                            {/*            onClick={() => handleStatusChange(booking.bookingId, 'CANCELLED')}*/}
                            {/*            className="bg-red-500 text-white px-4 py-2 rounded"*/}
                            {/*        >*/}
                            {/*            Cancel*/}
                            {/*        </button>*/}
                            {/*    </div>*/}
                            {/*)}*/}
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

export default BookingsForSpacePage;
