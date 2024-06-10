import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Select, Spinner } from '@nextui-org/react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const BookingPage = () => {
    const { bookingId } = useParams();
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [booking, setBooking] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [status, setStatus] = useState('');
    const navigate = useNavigate();

    const statusOptions = ["INQUIRY",
        "ACCEPTED",
        "COMPLETED",
        "CANCELLED",
        "REJECTED",
        "PENDING"];

    useEffect(() => {
        const fetchBooking = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.get(`http://localhost:8080/api/booking/${bookingId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setBooking(response.data);
                setStatus(response.data.status);
                setLoading(false);
            } catch (error) {
                setError("Unfortunately there was an error when trying to load the booking information. Please try again later.");
                setLoading(false);
            }
        };

        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchBooking();
        }
    }, [bookingId, isAuthenticated, loginWithRedirect]);

    const handleUpdateStatus = async () => {
        try {
            const token = localStorage.getItem('authToken');
            const response = await axios.put(`http://localhost:8080/api/booking/status/${bookingId}`, { status }, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setBooking(response.data);
        } catch (error) {
            // setError("Unfortunately there was an error when trying to update the booking status. Please try again later.");
            toast.error("Unfortunately there was an error when trying to update the booking status. Please try again later.");
        }
    };

    const handleDeleteBooking = async () => {
        try {
            const token = localStorage.getItem('authToken');
            await axios.delete(`http://localhost:8080/api/booking/${bookingId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate('/bookings');
        } catch (error) {
            // setError("Unfortunately there was an error when trying to delete the booking. Please try again later.");
            toast.error("Unfortunately there was an error when trying to delete the booking. Please try again later.");
        }
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

    if (!booking) {
        return <div>No booking data found.</div>;
    }

    return (
        <div className="p-4">
            <ToastContainer />
            <h1 className="text-2xl font-semibold mb-4">Booking Details</h1>
            <div className="mb-4">
                <label className="block text-gray-700">Booking ID</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{booking.bookingId}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Client</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{booking.client.firstName} {booking.client.lastName}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Space ID</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{booking.spaceId}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Start Date</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{new Date(booking.startDateTime).toLocaleString()}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">End Date</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{new Date(booking.endDateTime).toLocaleString()}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Status</label>
                <select
                    id="status"
                    name="status"
                    className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm"
                    value={status}
                    onChange={(e) => setStatus(e.target.value)}
                    fullWidth
                >
                    {statusOptions.map((option) => (
                        <option key={option} value={option}>{option}</option>
                    ))}
                </select>
            </div>
            <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md mb-4" onClick={handleUpdateStatus}>Update
                Status</Button>
            {/*<Button className="bg-red-500 text-white px-4 py-2 rounded-md shadow-md mb-4" onClick={handleDeleteBooking}>Delete*/}
            {/*    Booking</Button>*/}
        </div>
    );
};

export default BookingPage;
