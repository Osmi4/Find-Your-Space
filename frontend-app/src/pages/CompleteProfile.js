import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';
import { Input, Button } from "@nextui-org/react";

const CompleteProfile = () => {
    const { user, loginWithRedirect } = useAuth0();
    const [profileData, setProfileData] = useState({ contactInfo: '', bankAccountNumber: '', firstName: '' , lastName: '' });
    const [userId, setUserId] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserId = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const response = await axios.get('http://localhost:8080/api/user/my-details', {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setUserId(response.data.userId);
                setProfileData({
                    bankAccountNumber: response.data.bankAccountNumber || '',
                    contactInfo: response.data.contactInfo || '',
                    firstName: response.data.firstName || '',
                    lastName: response.data.lastName || ''
                });
            } catch (error) {
                console.error("Error fetching user details", error);
                if (error.response && error.response.status === 401) {
                    loginWithRedirect(); // Redirect to login if unauthorized
                }
            }
        };

        if (user) {
            fetchUserId();
        }
    }, [user, loginWithRedirect]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProfileData({ ...profileData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('authToken');

        try {
            await axios.patch(`http://localhost:8080/api/user/my-details`, profileData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate('/');
        } catch (error) {
            console.error("Error completing profile", error);
            if (error.response && error.response.status === 401) {
                await loginWithRedirect(); // Redirect to login if unauthorized
            }
        }
    };

    return (
        <div className="container mx-auto p-4">
            <div className="bg-white shadow-md rounded-lg p-6">
                <h1 className="text-2xl font-bold mb-4 text-gray-700">Complete Your Profile</h1>
                <form onSubmit={handleSubmit}>
                    <Input
                        label="Bank Account"
                        placeholder='Please enter your bank account number'
                        type="text"
                        name="bankAccountNumber"
                        value={profileData.bankAccountNumber}
                        onChange={handleChange}
                        className="w-full mt-1 mb-3"
                        variant='bordered'
                        required
                    />
                    <Input
                        label="Phone number"
                        placeholder='Please enter your phone number'
                        type="text"
                        name="contactInfo"
                        value={profileData.contactInfo}
                        onChange={handleChange}
                        className="w-full mt-1 mb-3"
                        variant='bordered'
                        required
                    />
                    <Button type="submit" color="primary">Submit</Button>
                </form>
            </div>
        </div>
    );
};

export default CompleteProfile;
