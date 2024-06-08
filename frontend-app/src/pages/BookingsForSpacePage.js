import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { ClipLoader } from 'react-spinners';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Pagination, Button } from "@nextui-org/react";

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
                toast.error('Error fetching data');
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
            toast.success('Status updated successfully');
        } catch (error) {
            console.error('Error updating status:', error);
            toast.error('Error updating status');
        }
    };

    if (loading) return (
        <div className="flex justify-center items-center h-screen">
            <ClipLoader size={35} color={"#123abc"} loading={loading} />
        </div>
    );

    return (
        <div className="container mx-auto p-4">
            <ToastContainer />
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
                        </div>
                    ))}
                </div>
            )}
            {
                totalPages >= 1 && (
            <div className="flex justify-between mt-6">
                <Button
                    onClick={() => handlePageChange(Math.max(0, currentPage - 1))}
                    disabled={currentPage === 0 || totalPages === 1}
                    className="bg-black text-white"
                >
                    Previous
                </Button>
                <Pagination total={totalPages} page={currentPage + 1} onChange={(page) => handlePageChange(page - 1)} color="default" variant="bordered" />
                <Button
                    onClick={() => handlePageChange(Math.min(totalPages - 1, currentPage + 1))}
                    disabled={currentPage === totalPages - 1 || totalPages === 1}
                    className="bg-black text-white"
                >
                    Next
                </Button>
            </div>  )
            }
        </div>
    );
};

export default BookingsForSpacePage;
