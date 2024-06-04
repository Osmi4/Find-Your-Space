import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Select } from '@nextui-org/react';

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
                setError(error.message);
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
            setError(error.message);
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
            setError(error.message);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!booking) {
        return <div>No booking data found.</div>;
    }

    return (
        <div className="p-4">
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
                <Select
                    name="status"
                    placeholder="Select status"
                    value={status}
                    onChange={(value) => setStatus(value)}
                    fullWidth
                >
                    {statusOptions.map((status) => (
                        <Select.Option key={status} value={status}>{status}</Select.Option>
                    ))}
                </Select>
            </div>
            <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md mb-4" onClick={handleUpdateStatus}>Update Status</Button>
            <Button className="bg-red-500 text-white px-4 py-2 rounded-md shadow-md mb-4" onClick={handleDeleteBooking}>Delete Booking</Button>
        </div>
    );
};

export default BookingPage;
