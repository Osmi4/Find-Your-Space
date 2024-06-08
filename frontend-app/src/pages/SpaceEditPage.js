import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Input, Textarea, Select, SelectItem } from '@nextui-org/react';
import { ClipLoader } from 'react-spinners';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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
    const [image, setImage] = useState(null);
    const [imagePreview, setImagePreview] = useState('');
    const [currentImageId, setCurrentImageId] = useState(null);

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

    useEffect(() => {
        const fetchSpaceData = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const response = await axios.get(`http://localhost:8080/api/space/${spaceId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                let spaceData = response.data;
                const imageUrl = await fetchImage(spaceData.spaceId, token);
                spaceData.image = imageUrl.image;
                spaceData.imageId = imageUrl.id;

                setSpaceData({
                    spaceName: spaceData.spaceName,
                    spaceLocation: spaceData.spaceLocation,
                    spaceSize: spaceData.spaceSize,
                    spacePrice: spaceData.spacePrice,
                    spaceDescription: spaceData.spaceDescription
                });
                setAvailability(spaceData.availability || 'NOT_RELEASED');
                setImagePreview(spaceData.image);  // Use url property for image preview
                setCurrentImageId(spaceData.imageId);  // Use id property for image ID
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
            const response = await axios.put(`http://localhost:8080/api/space/${spaceId}/change_availability`, availability , {
                headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' }
            });
            setAvailability(response.data.availability);
            toast.success('Availability changed successfully');
        } catch (error) {
            console.error('Error changing availability', error);
            toast.error('Error changing availability');
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('authToken');
        try {
            await axios.put(`http://localhost:8080/api/space/${spaceId}`, spaceData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            toast.success('Space updated successfully');
            if (image) {
                await handleImageUpload(); // Call image upload function if there's an image to upload
            }
            navigate('/my_spaces');
        } catch (error) {
            console.error('Error updating space', error);
            toast.error('Error updating space');
        }
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setImage(file);
        setImagePreview(URL.createObjectURL(file));
    };

    const handleImageUpload = async () => {
        const token = localStorage.getItem('authToken');
        const formData = new FormData();
        formData.append('image', image);

        try {
            const idToDelete = await axios.get(`http://localhost:8080/api/space/${spaceId}/images/getId`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            if (idToDelete.data) {
                await axios.delete(`http://localhost:8080/api/space/${spaceId}/images/${idToDelete.data}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
            }

            const response = await axios.post(`http://localhost:8080/api/space/${spaceId}/images`, formData, {
                headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'multipart/form-data' }
            });

            setCurrentImageId(response.data.id);
            toast.success('Image uploaded successfully');
        } catch (error) {
            console.error('Error uploading image', error);
            toast.error('Error uploading image');
        }
    };

    if (loading) {
        return (
            <div className="flex justify-center items-center h-screen">
                <ClipLoader size={35} color={"#123abc"} loading={loading} />
            </div>
        );
    }

    return (
        <div className="container mx-auto p-4">
            <ToastContainer />
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
            <div className="mt-4 flex gap-4 items-center">
                <Select
                id="availability"
                name="availability"
                value={availability}
                label="Availability"
                placeholder={availability[0].toUpperCase() + availability.slice(1).replace('_', ' ').toLowerCase()}
                onChange={(e) => setAvailability(e.target.value)}
                className="max-w-xs"
                >
                    <SelectItem key="NOT_RELEASED" value="NOT_RELEASED">
                        Not released
                    </SelectItem>
                    <SelectItem key="AVAILABLE" value="AVAILABLE">
                        Available
                    </SelectItem>
                    <SelectItem key="BLOCKED" value="BLOCKED">
                        Blocked
                    </SelectItem>
                </Select>
                <Button onClick={handleAvailabilityChange} className="font-semibold" color="primary">
                    Change Availability
                </Button>
            </div>
            <div className="mt-4">
                <label className="block text-sm font-medium text-gray-700">Upload Image</label>
                <input
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                    className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:text-sm file:font-semibold file:bg-indigo-50 file:text-indigo-600 hover:file:bg-indigo-100 mt-1"
                />
                {imagePreview && (
                    <div className="mt-4 flex flex-col items-center">
                        <img src={imagePreview} alt="Preview" className="w-1/2 h-auto rounded-md" />
                        <Button onClick={handleImageUpload} className="mt-2 font-semibold" color="primary">
                            Upload Image
                        </Button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default SpaceEditPage;
