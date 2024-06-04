import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Button } from '@nextui-org/react';

const SpacePage = () => {
    const { spaceId } = useParams();
    const navigate = useNavigate();
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [space, setSpace] = useState(null);
    const [image, setImage] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSpace = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const [spaceResponse, imageResponse] = await Promise.all([
                    axios.get(`http://localhost:8080/api/space/${spaceId}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    }),
                    axios.get(`http://localhost:8080/api/space/${spaceId}/images`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                ]);
                setSpace(spaceResponse.data);
                setImage(imageResponse.data);
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchSpace();
        }
    }, [spaceId, isAuthenticated, loginWithRedirect]);

    const handleDelete = async () => {
        try {
            const token = localStorage.getItem('authToken');
            await axios.delete(`http://localhost:8080/api/space/${spaceId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate('/spaces');
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

    return (
        <div className="p-4">
            <h1 className="text-2xl font-semibold mb-4">Space Details</h1>
            {space && (
                <div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Space ID</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{space.spaceId}</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Name</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{space.spaceName}</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Location</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{space.spaceLocation}</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Description</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{space.spaceDescription}</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Size</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{space.spaceSize} sq ft</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Price</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">${space.spacePrice}</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Type</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{space.spaceType}</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Availability</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{space.availability}</p>
                    </div>
                    <div className="mb-4">
                        <label className="block text-gray-700">Date Added</label>
                        <p className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm">{new Date(space.dateAdded).toLocaleDateString()}</p>
                    </div>
                    {image && (
                        <div className="mb-4">
                            <label className="block text-gray-700">Image</label>
                            <img src={image} alt="Space" className="block w-full mt-1 border border-gray-300 rounded-md shadow-sm" />
                        </div>
                    )}
                    <Button className="bg-red-500 text-white px-4 py-2 rounded-md shadow-md" onClick={handleDelete}>Delete Space</Button>
                </div>
            )}
        </div>
    );
};

export default SpacePage;
