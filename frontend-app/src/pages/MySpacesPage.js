import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Button } from '@nextui-org/react';
import { useAuth0 } from "@auth0/auth0-react";

const MySpacesPage = () => {
    const { getAccessTokenSilently } = useAuth0();
    const [spaces, setSpaces] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSpaces = async () => {
            try {
                const token = await getAccessTokenSilently();
                const spaceFilter = {};

                const response = await axios.post('http://localhost:8080/api/space/my-spaces', spaceFilter, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });

                const spacesWithImages = await Promise.all(response.data.content.map(async space => {
                    if (space.spaceId) {
                        const imageUrl = await fetchImage(space.spaceId, token);
                        return { ...space, imageUrl };
                    }
                    return space;
                }));

                setSpaces(spacesWithImages);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching spaces:', error);
                setLoading(false);
            }
        };

        fetchSpaces();
    }, [getAccessTokenSilently]);

    const fetchImage = async (spaceId, token) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/space/${spaceId}/images`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching image:', error);
            return null;
        }
    };

    const handleDelete = async (spaceId) => {
        if (!window.confirm('Are you sure you want to delete this space?')) return;

        try {
            const token = await getAccessTokenSilently();
            await axios.delete(`http://localhost:8080/api/space/${spaceId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setSpaces(spaces.filter(space => space.spaceId !== spaceId));
        } catch (error) {
            console.error('Error deleting space:', error);
        }
    };

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">My Spaces for Rent</h1>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                    {spaces.map(space => (
                        <div key={space.spaceId} className="bg-white p-4 rounded-lg shadow-md">
                            {space.imageUrl ? (
                                <img
                                    src={space.imageUrl}
                                    alt={space.spaceName}
                                    className="w-full h-48 object-cover mb-4 rounded"
                                />
                            ) : (
                                <div className="w-full h-48 flex items-center justify-center bg-gray-200 mb-4 rounded">
                                    <p>No Image Available</p>
                                </div>
                            )}
                            <h2 className="text-xl font-semibold">{space.spaceName}</h2>
                            <p className="text-gray-700">Location: {space.spaceLocation}</p>
                            <p className="text-gray-700">Size: {space.spaceSize} sq.ft.</p>
                            <p className="text-gray-700">Price: ${space.spacePrice}</p>
                            <p className="text-gray-700">Description: {space.spaceDescription}</p>
                            <p className="text-gray-700">Availability: {space.availability}</p>
                            <p className="text-gray-700">Added: {new Date(space.dateAdded).toLocaleDateString()}</p>
                            <p className="text-gray-700">Updated: {new Date(space.dateUpdated).toLocaleDateString()}</p>
                            <div className="flex gap-2 mt-2">
                                <Button onClick={() => navigate(`/space/${space.spaceId}/edit`)} className="bg-blue-500 text-white">
                                    View Details
                                </Button>
                                <Button onClick={() => handleDelete(space.spaceId)} className="bg-red-500 text-white">
                                    Delete
                                </Button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default MySpacesPage;
