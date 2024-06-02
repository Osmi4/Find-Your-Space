import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Button } from '@nextui-org/react';

const SpaceEditPage = () => {
    const { getAccessTokenSilently } = useAuth0();
    const { spaceId } = useParams();
    const navigate = useNavigate();
    const [spaceData, setSpaceData] = useState({
        spaceName: '',
        spaceLocation: '',
        spaceSize: '',
        spacePrice: '',
        spaceDescription: ''
    });
    const [availability, setAvailability] = useState('NOT_RELEASED');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchSpaceData = async () => {
            try {
                const token = await getAccessTokenSilently();
                const response = await axios.get(`http://localhost:8080/api/space/${spaceId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setSpaceData({
                    spaceName: response.data.spaceName,
                    spaceLocation: response.data.spaceLocation,
                    spaceSize: response.data.spaceSize,
                    spacePrice: response.data.spacePrice,
                    spaceDescription: response.data.spaceDescription
                });
                setAvailability(response.data.availability);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching space data', error);
                setLoading(false);
            }
        };

        fetchSpaceData();
    }, [spaceId, getAccessTokenSilently]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSpaceData({ ...spaceData, [name]: value });
    };

    const handleAvailabilityChange = async () => {
        try {
            const token = await getAccessTokenSilently();
            const response = await axios.put(`http://localhost:8080/api/space/${spaceId}/change_availability`, availability, {
                headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }
            });
            setAvailability(response.data.availability);
        } catch (error) {
            console.error('Error changing availability', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = await getAccessTokenSilently();
            await axios.put(`http://localhost:8080/api/space/${spaceId}`, spaceData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate('/my_spaces');
        } catch (error) {
            console.error('Error updating space', error);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Edit Space</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <label className="block text-gray-700">Space Name</label>
                    <input
                        type="text"
                        name="spaceName"
                        value={spaceData.spaceName}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded mt-1"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700">Space Location</label>
                    <input
                        type="text"
                        name="spaceLocation"
                        value={spaceData.spaceLocation}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded mt-1"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700">Space Size (sq m)</label>
                    <input
                        type="number"
                        name="spaceSize"
                        value={spaceData.spaceSize}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded mt-1"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700">Space Price ($)</label>
                    <input
                        type="number"
                        name="spacePrice"
                        value={spaceData.spacePrice}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded mt-1"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700">Space Description</label>
                    <textarea
                        name="spaceDescription"
                        value={spaceData.spaceDescription}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded mt-1"
                    />
                </div>
                <Button type="submit" className="bg-blue-500 text-white p-2 rounded">Save Changes</Button>
            </form>
            <div className="mt-4">
                <h2 className="text-xl font-bold">Change Availability</h2>
                <select
                    value={availability}
                    onChange={(e) => setAvailability(e.target.value)}
                    className="w-full p-2 border border-gray-300 rounded mt-1"
                >
                    <option value="NOT_RELEASED">Not Released</option>
                    <option value="AVAILABLE">Available</option>
                    <option value="BLOCKED">Blocked</option>
                </select>
                <Button onClick={handleAvailabilityChange} className="mt-2 bg-blue-500 text-white">
                    Change Availability
                </Button>
            </div>
        </div>
    );
};

export default SpaceEditPage;
