import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import PaymentForm from "../components/PaymentForm";

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

    if (loading) return <div style={styles.loading}>Loading...</div>;

    return (
        <div style={styles.container}>
            <h1 style={styles.title}>Booking Details</h1>
            {booking && (
                <div style={styles.card}>
                    <p style={styles.cardText}><strong>Booking ID:</strong> {booking.bookingId}</p>
                    <p style={styles.cardText}><strong>Start Date:</strong> {new Date(booking.startDateTime).toLocaleString()}</p>
                    <p style={styles.cardText}><strong>End Date:</strong> {new Date(booking.endDateTime).toLocaleString()}</p>
                    <p style={styles.cardText}><strong>Description:</strong> {booking.description}</p>
                    <p style={styles.cardText}><strong>Status:</strong> {booking.status}</p>
                    <p style={styles.cardText}><strong>Cost:</strong> ${booking.cost}</p>
                    <p style={styles.cardText}><strong>Date Added:</strong> {new Date(booking.dateAdded).toLocaleString()}</p>
                    <p style={styles.cardText}><strong>Date Updated:</strong> {new Date(booking.dateUpdated).toLocaleString()}</p>
                </div>
            )}
            {space && (
                <div style={styles.card}>
                    <h2 style={styles.subtitle}>Space Details</h2>
                    <p style={styles.cardText}><strong>Space Name:</strong> {space.spaceName}</p>
                    <p style={styles.cardText}><strong>Description:</strong> {space.spaceDescription}</p>
                    <p style={styles.cardText}><strong>Location:</strong> {space.spaceLocation}</p>
                    <p style={styles.cardText}><strong>Size:</strong> {space.spaceSize} sq ft</p>
                    <p style={styles.cardText}><strong>Price:</strong> ${space.spacePrice}</p>
                </div>
            )}
            {owner && (
                <div style={styles.card}>
                    <h2 style={styles.subtitle}>Owner Details</h2>
                    <p style={styles.cardText}><strong>Name:</strong> {owner.firstName} {owner.lastName}</p>
                    <p style={styles.cardText}><strong>Email:</strong> {owner.email}</p>
                    <p style={styles.cardText}><strong>Contact Info:</strong> {owner.contactInfo}</p>
                    {owner.pictureUrl && <img src={owner.pictureUrl} alt="Owner" style={styles.ownerImage} />}
                </div>
            )}
            {booking && booking.status === 'ACCEPTED' && (
                <PaymentForm bookingId={booking.bookingId} amount={booking.cost} />
            )}
        </div>
    );
};

const styles = {
    container: {
        maxWidth: '800px',
        margin: '0 auto',
        padding: '20px',
        backgroundColor: '#f9f9f9',
        borderRadius: '8px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)',
    },
    title: {
        fontSize: '2em',
        fontWeight: 'bold',
        marginBottom: '20px',
        textAlign: 'center',
    },
    subtitle: {
        fontSize: '1.5em',
        fontWeight: 'bold',
        marginBottom: '10px',
    },
    card: {
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '8px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)',
        marginBottom: '20px',
    },
    cardText: {
        fontSize: '1em',
        color: '#333',
        marginBottom: '10px',
    },
    ownerImage: {
        width: '100px',
        height: '100px',
        borderRadius: '50%',
        marginTop: '10px',
    },
    loading: {
        fontSize: '1.5em',
        textAlign: 'center',
        marginTop: '20px',
    }
};

export default BookingPage;
