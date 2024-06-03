import React, { useState } from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { Elements, CardElement, useStripe, useElements } from '@stripe/react-stripe-js';
import axios from 'axios';

const stripePromise = loadStripe('pk_test_51P6JyDK9FeT1ROkk2JzoEa4G4qBbXmUtagpyHkfd66m1HOMzjIU1SG9SXK8x1vw5jYMjf28MNH1G6moOZWBH14kq00SZOdkcRL');

const PaymentForm = ({ bookingId, amount }) => {
    const stripe = useStripe();
    const elements = useElements();
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);

        if (!stripe || !elements) {
            setLoading(false);
            return;
        }

        const cardElement = elements.getElement(CardElement);

        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: 'card',
            card: cardElement,
        });

        if (error) {
            setError(error.message);
            setLoading(false);
            return;
        }

        const token = localStorage.getItem('authToken');
        console.log('paymentMethod:', paymentMethod);
        try {
            const response = await axios.post('http://localhost:8080/api/payment/charge', {
                token: paymentMethod.id,
                amount: amount,
                bookingId: bookingId,
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            const { clientSecret } = response.data;

            const { error: confirmError, paymentIntent } = await stripe.confirmCardPayment(clientSecret);

            if (confirmError) {
                setError(confirmError.message);
            } else if (paymentIntent.status === 'succeeded') {
                alert('Payment successful!');
            } else {
                setError('Payment failed. Please try again.');
            }
        } catch (error) {
            setError('Payment failed. Please try again.');
        }

        setLoading(false);
    };

    return (
        <form id="payment-form" style={styles.form} onSubmit={handleSubmit}>
            <div style={styles.formGroup}>
                <label htmlFor="card-element" style={styles.formLabel}>Credit or debit card</label>
                <div id="card-element" style={styles.cardElement}>
                    <CardElement />
                </div>
                <div id="card-errors" style={styles.formError} role="alert">{error}</div>
            </div>
            <button type="submit" style={styles.btnSubmit} disabled={!stripe || loading}>
                {loading ? 'Processing...' : 'Submit Payment'}
            </button>
        </form>
    );
};

const PaymentPage = ({ bookingId, amount }) => (
    <Elements stripe={stripePromise}>
        <PaymentForm bookingId={bookingId} amount={amount} />
    </Elements>
);

const styles = {
    form: {
        maxWidth: '400px',
        margin: '0 auto',
        padding: '20px',
        backgroundColor: '#f9f9f9',
        borderRadius: '8px',
        boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)',
    },
    formGroup: {
        marginBottom: '20px',
    },
    formLabel: {
        display: 'block',
        marginBottom: '5px',
        fontWeight: 'bold',
        color: '#333',
    },
    cardElement: {
        padding: '10px',
        border: '1px solid #ccc',
        borderRadius: '5px',
    },
    btnSubmit: {
        display: 'block',
        width: '100%',
        padding: '10px',
        backgroundColor: '#007bff',
        color: '#fff',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    },
    btnSubmitHover: {
        backgroundColor: '#0056b3',
    },
    formError: {
        color: '#dc3545',
        marginTop: '5px',
        fontSize: '14px',
    }
};

export default PaymentPage;
