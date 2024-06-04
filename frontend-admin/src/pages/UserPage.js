import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useParams, Link } from 'react-router-dom';
import { Button } from '@nextui-org/react';

const UserPage = () => {
    const { userId } = useParams();
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.get(`http://localhost:8080/api/user/${userId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setUser(response.data);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchUser();
        }
    }, [userId, isAuthenticated, loginWithRedirect]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!user) {
        return <div>No user data found.</div>;
    }

    return (
        <div className="p-4">
            <h1 className="text-2xl font-semibold mb-4">User Details</h1>
            <div className="mb-4">
                <label className="block text-gray-700">User ID</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{user.userId}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Email</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{user.email}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Contact Info</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{user.contactInfo}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">First Name</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{user.firstName}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Last Name</label>
                <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{user.lastName}</p>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Picture</label>
                {user.pictureUrl && <img src={user.pictureUrl} alt="User" className="block w-32 h-32 mt-1 border border-gray-300 rounded-md shadow-sm" />}
            </div>
            <Link to={`/message/${user.userId}`}>
                <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md">Send Message</Button>
            </Link>
        </div>
    );
};

export default UserPage;
