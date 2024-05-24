import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Button } from '@nextui-org/react';

const MySpacesPage = () => {
    const [spaces, setSpaces] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSpaces = async () => {
            try {
                const token = localStorage.getItem('token');
                const spaceFilter = {};

                const response = await axios.post('http://localhost:8080/api/space/my-spaces', spaceFilter, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });

                const spacesWithImages = await Promise.all(response.data.content.map(async space => {
                    if (space.id) {
                        const imageUrl = await fetchImage(space.id);
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
    }, []);

    const fetchImage = async (spaceId) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/space/${spaceId}/images`);
            return response.data;
        } catch (error) {
            console.error('Error fetching image:', error);
            return null;
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
                        <div key={space.id} className="bg-white p-4 rounded-lg shadow-md">
                            {space.imageUrl ? (
                                <img
                                    src={space.imageUrl}
                                    alt={space.name}
                                    className="w-full h-48 object-cover mb-4 rounded"
                                />
                            ) : (
                                <div className="w-full h-48 flex items-center justify-center bg-gray-200 mb-4 rounded">
                                    <p>No Image Available</p>
                                </div>
                            )}
                            <h2 className="text-xl font-semibold">{space.name}</h2>
                            <p className="text-gray-700">{space.description}</p>
                            <Button onClick={() => navigate(`/space/${space.id}`)} className="mt-2 bg-blue-500 text-white">
                                View Details
                            </Button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default MySpacesPage;
