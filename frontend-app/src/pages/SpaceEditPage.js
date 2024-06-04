import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Input, Textarea, Select, SelectItem } from '@nextui-org/react';

const SpaceEditPage = () => {
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
            const token = localStorage.getItem('authToken');
            try {
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
    }, [spaceId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSpaceData({ ...spaceData, [name]: value });
    };

    const handleAvailabilityChange = async () => {
        const token = localStorage.getItem('authToken');
        try {
            const response = await axios.put(`http://localhost:8080/api/space/${spaceId}/change_availability`, availability, {
                headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }
            });
            setAvailability(response.data.availability);
            alert('Availability changed successfully');
        } catch (error) {
            console.error('Error changing availability', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('authToken');
        try {
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
                    <Input
                        label="Space Name"
                        type="text"
                        name="spaceName"
                        value={spaceData.spaceName}
                        onChange={handleChange}
                        className="w-full rounded mt-1"
                        variant='bordered'
                    />
                </div>
                <div className="mb-4">
                    <Input
                        label="Space Location"
                        type="text"
                        name="spaceLocation"
                        value={spaceData.spaceLocation}
                        onChange={handleChange}
                        className="w-full rounded mt-1"
                        variant='bordered'
                    />
                </div>
                <div className="mb-4">
                    <Input
                        label="Space Size in m²"
                        type="number"
                        name="spaceSize"
                        value={spaceData.spaceSize}
                        onChange={handleChange}
                        className="w-full rounded mt-1"
                        variant='bordered'
                    />
                </div>
                <div className="mb-4">
                    <Input
                        label="Space Price in $"
                        type="number"
                        name="spacePrice"
                        value={spaceData.spacePrice}
                        onChange={handleChange}
                        className="w-full rounded mt-1"
                        variant='bordered'
                    />
                </div>
                <div className="mb-4">
                    <Textarea
                        label="Space Description"
                        name="spaceDescription"
                        value={spaceData.spaceDescription}
                        onChange={handleChange}
                        className="w-full rounded mt-1"
                        variant='bordered'
                    />
                </div>
                <Button type="submit" className="text-white font-semibold" color="success">Save Changes</Button>
            </form>
            <div className="mt-4 flex gap-[1vw]">
                <Select
                value={availability}
                placeholder={availability[0].toUpperCase() + availability.slice(1).replace('_', ' ').toLowerCase()}
                onChange={(e) => setAvailability(e.target.value)}
                label="Change Availability"
                className="max-w-xs"
                >
                    <SelectItem value="NOT_RELEASED">
                        Not released
                    </SelectItem>
                    <SelectItem value="AVAILABLE">
                        Available
                    </SelectItem>
                    <SelectItem value="BLOCKED">
                        Blocked
                    </SelectItem>

                </Select>
                <Button onClick={handleAvailabilityChange} className="mt-2 font-semibold" color="primary">
                    Change Availability
                </Button>
            </div>
        </div>
    );
};

export default SpaceEditPage;
