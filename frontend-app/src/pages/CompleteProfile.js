import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';

const CompleteProfile = () => {
    const { getAccessTokenSilently, user, loginWithRedirect } = useAuth0();
    const [profileData, setProfileData] = useState({ contactInfo: '', bankAccountNumber: ''});
    const [userId, setUserId] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserId = async () => {
            try {
                const token = await getAccessTokenSilently();
                const response = await axios.get('http://localhost:8080/api/user/my-details', {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setUserId(response.data.userId);
                setProfileData({
                    bankAccountNumber: response.data.bankAccountNumber || '',
                    contactInfo: response.data.contactInfo || ''
                });
            } catch (error) {
                console.error("Error fetching user details", error);
                if (error.response && error.response.status === 401) {
                    // If unauthorized, try to get a new token
                    try {
                        await getAccessTokenSilently();
                        fetchUserId(); // Retry fetching user details
                    } catch (retryError) {
                        console.error("Retrying fetch user details failed", retryError);
                        loginWithRedirect(); // Redirect to login if retry fails
                    }
                }
            }
        };

        if (user) {
            fetchUserId();
        }
    }, [user, getAccessTokenSilently, loginWithRedirect]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProfileData({ ...profileData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = await getAccessTokenSilently();

        try {
            await axios.patch(`http://localhost:8080/api/user/${userId}/details`, profileData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate('/');
        } catch (error) {
            console.error("Error completing profile", error);
            if (error.response && error.response.status === 401) {
                try {
                    await getAccessTokenSilently();
                    handleSubmit(); // Retry submitting the form
                } catch (retryError) {
                    console.error("Retrying submit failed", retryError);
                    loginWithRedirect(); // Redirect to login if retry fails
                }
            }
        }
    };

    return (
        <div className="container mx-auto p-4">
            <div className="bg-white shadow-md rounded-lg p-6">
                <h1 className="text-2xl font-bold mb-4">Complete Your Profile</h1>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-gray-700">Bank Account</label>
                        <input
                            type="text"
                            name="bankAccountNumber"
                            value={profileData.bankAccountNumber}
                            onChange={handleChange}
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Contact Info</label>
                        <input
                            type="text"
                            name="contactInfo"
                            value={profileData.contactInfo}
                            onChange={handleChange}
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            required
                        />
                    </div>
                    <button type="submit" className="bg-blue-500 text-white p-2 rounded">Submit</button>
                </form>
            </div>
        </div>
    );
};

export default CompleteProfile;
