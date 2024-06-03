import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const BookingPage = () => {
    const { bookingId } = useParams();
    const [booking, setBooking] = useState(null);
    const [space, setSpace] = useState(null);
    const [owner, setOwner] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchBookingDetails = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const bookingResponse = await axios.get(`http://localhost:8080/api/booking/${bookingId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setBooking(bookingResponse.data);

                const spaceResponse = await axios.get(`http://localhost:8080/api/space/${bookingResponse.data.spaceId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setSpace(spaceResponse.data);

                const ownerResponse = await axios.get(`http://localhost:8080/api/user/${spaceResponse.data.owner.userId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setOwner(ownerResponse.data);

                setLoading(false);
            } catch (error) {
                console.error('Error fetching booking details:', error);
                setLoading(false);
            }
        };

        fetchBookingDetails();
    }, [bookingId]);

    if (loading) return <div>Loading...</div>;

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Booking Details</h1>
            {booking && (
                <div className="bg-white p-4 rounded-lg shadow-md mb-4">
                    <p className="text-lg font-semibold">Booking ID: {booking.bookingId}</p>
                    <p className="text-gray-600">Start Date: {new Date(booking.startDateTime).toLocaleString()}</p>
                    <p className="text-gray-600">End Date: {new Date(booking.endDateTime).toLocaleString()}</p>
                    <p className="text-gray-600">Description: {booking.description}</p>
                    <p className="text-gray-600">Status: {booking.status}</p>
                    <p className="text-gray-600">Cost: ${booking.cost}</p>
                    <p className="text-gray-600">Date Added: {new Date(booking.dateAdded).toLocaleString()}</p>
                    <p className="text-gray-600">Date Updated: {new Date(booking.dateUpdated).toLocaleString()}</p>
                </div>
            )}
            {space && (
                <div className="bg-white p-4 rounded-lg shadow-md mb-4">
                    <h2 className="text-xl font-semibold mb-2">Space Details</h2>
                    <p className="text-lg font-semibold">Space Name: {space.spaceName}</p>
                    <p className="text-gray-600">Description: {space.spaceDescription}</p>
                    <p className="text-gray-600">Location: {space.spaceLocation}</p>
                    <p className="text-gray-600">Size: {space.spaceSize} sq ft</p>
                    <p className="text-gray-600">Price: ${space.spacePrice}</p>
                </div>
            )}
            {owner && (
                <div className="bg-white p-4 rounded-lg shadow-md mb-4">
                    <h2 className="text-xl font-semibold mb-2">Owner Details</h2>
                    <p className="text-lg font-semibold">Name: {owner.firstName} {owner.lastName}</p>
                    <p className="text-gray-600">Email: {owner.email}</p>
                    <p className="text-gray-600">Contact Info: {owner.contactInfo}</p>
                    {owner.pictureUrl && <img src={owner.pictureUrl} alt="Owner" className="w-32 h-32 rounded-full mt-2" />}
                </div>
            )}
        </div>
    );
};

export default BookingPage;
