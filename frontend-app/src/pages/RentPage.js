import { useState } from 'react';
import { Input, Textarea, Button } from "@nextui-org/react";
import { Select } from 'antd';
import axios from 'axios';

const RentPage = () => {
    const spaceTypes = [
        "OFFICE",
        "MEETING_ROOM",
        "EVENT_SPACE",
        "COWORKING_SPACE",
        "RETAIL_SPACE",
        "STORAGE_SPACE",
        "PARKING_SPACE",
        "WAREHOUSE",
        "INDUSTRIAL_SPACE",
        "OTHER"
    ];

    const [spaceInfo, setSpaceInfo] = useState({
        spaceName: '',
        rentCity: '',
        rentZipCode: '',
        rentAddress: '',
        rentPrice: '',
        size: '',
        spaceType: '',
        spaceDescription: '',
        images: []
    });
    const [imagePreviews, setImagePreviews] = useState([]);
    const [spaceId, setSpaceId] = useState(null);

    const handleSpaceChange = (e) => {
        const { name, value } = e.target;
        setSpaceInfo({ ...spaceInfo, [name]: value });
    };

    const handleSpaceTypeChange = (value) => {
        setSpaceInfo({ ...spaceInfo, spaceType: value });
    };

    const handleImageChange = (e) => {
        const files = Array.from(e.target.files);
        setSpaceInfo((prev) => ({
            ...prev,
            images: [...prev.images, ...files]
        }));
        setImagePreviews((prev) => [
            ...prev,
            ...files.map((file) => URL.createObjectURL(file))
        ]);
    };

    const handleImageDelete = (index) => {
        setSpaceInfo((prev) => ({
            ...prev,
            images: prev.images.filter((_, i) => i !== index)
        }));
        setImagePreviews((prev) => prev.filter((_, i) => i !== index));
    };

    const handleSpaceSubmit = async (e) => {
        e.preventDefault();

        const spaceData = {
            spaceName: spaceInfo.spaceName,
            spaceLocation: `${spaceInfo.rentCity}, ${spaceInfo.rentAddress}, ${spaceInfo.rentZipCode}`,
            spaceSize: parseFloat(spaceInfo.size),
            spacePrice: parseFloat(spaceInfo.rentPrice),
            spaceType: spaceInfo.spaceType,
            spaceDescription: spaceInfo.spaceDescription
        };

        const token = localStorage.getItem('token'); // Retrieve the token from local storage

        try {
            const response = await axios.post('http://localhost:8080/api/space', spaceData, {
                headers: {
                    Authorization: `Bearer ${token}` // Include the token in the headers
                }
            });

            if (response.status === 200) {
                setSpaceId(response.data.spaceId); // Assuming the response contains the space ID
                alert("Space information submitted successfully!");
                await uploadImages(response.data.spaceId);
            } else {
                alert("Failed to submit space information.");
            }
        } catch (error) {
            console.error("Error submitting space information:", error);
            alert("An error occurred while submitting the space information.");
        }
    };

    const uploadImages = async (spaceId) => {
        const formData = new FormData();
        spaceInfo.images.forEach((image) => {
            formData.append('image', image);
        });

        const token = localStorage.getItem('token'); // Retrieve the token from local storage

        try {
            const response = await axios.post(`http://localhost:8080/api/space/${spaceId}/images`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: `Bearer ${token}` // Include the token in the headers
                }
            });

            if (response.status === 200) {
                alert("Images uploaded successfully!");
            } else {
                alert("Failed to upload images.");
            }
        } catch (error) {
            console.error("Error uploading images:", error);
            alert("An error occurred while uploading the images.");
        }
    };

    return (
        <div className="flex justify-center items-center h-screen">
            <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-3xl">
                <h1 className="text-4xl font-semibold mb-8 text-center">Rent your space</h1>
                <form onSubmit={handleSpaceSubmit} className="flex flex-col gap-y-6">
                    <div className="flex gap-x-6 mb-6">
                        <Input
                            name="spaceName"
                            label="Name of the space"
                            placeholder="Enter name of the space"
                            variant="bordered"
                            value={spaceInfo.spaceName}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                        <Input
                            name="rentCity"
                            label="City"
                            placeholder="Enter city of the space"
                            variant="bordered"
                            value={spaceInfo.rentCity}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                    </div>
                    <div className="flex gap-x-6 mb-6">
                        <Input
                            name="rentZipCode"
                            label="ZIP Code"
                            placeholder="Enter ZIP code of the space"
                            variant="bordered"
                            value={spaceInfo.rentZipCode}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                        <Input
                            name="rentAddress"
                            label="Address"
                            placeholder="Enter address of the space"
                            variant="bordered"
                            value={spaceInfo.rentAddress}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                    </div>
                    <div className="flex gap-x-6 mb-6">
                        <Input
                            name="rentPrice"
                            label="Price (per month) in $"
                            placeholder="Enter price of the space"
                            variant="bordered"
                            value={spaceInfo.rentPrice}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                        <Input
                            name="size"
                            label="Size in m2"
                            placeholder="Enter size of the space"
                            variant="bordered"
                            value={spaceInfo.size}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                    </div>
                    <Select
                        name="spaceType"
                        placeholder="Select type of the space"
                        value={spaceInfo.spaceType}
                        onChange={handleSpaceTypeChange}
                        className="mb-6 w-full"
                    >
                        {spaceTypes.map((type) => (
                            <Select.Option key={type} value={type}>
                                {type.replace('_', ' ')}
                            </Select.Option>
                        ))}
                    </Select>
                    <Textarea
                        name="spaceDescription"
                        label="Space Description"
                        placeholder="Enter description of the space"
                        variant="bordered"
                        value={spaceInfo.spaceDescription}
                        onChange={handleSpaceChange}
                        className="mb-6"
                    />
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-2">
                            Upload Images
                        </label>
                        <input
                            type="file"
                            name="images"
                            multiple
                            onChange={handleImageChange}
                            className="hidden"
                            id="file-upload"
                        />
                        <label
                            htmlFor="file-upload"
                            className="cursor-pointer px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50"
                        >
                            Choose Images
                        </label>
                    </div>
                    <div className="grid grid-cols-3 gap-4 mb-6">
                        {imagePreviews.map((preview, index) => (
                            <div key={index} className="relative">
                                <img src={preview} alt="Preview" className="w-full h-auto rounded-lg" />
                                <button
                                    type="button"
                                    onClick={() => handleImageDelete(index)}
                                    className="absolute top-2 right-2 bg-red-500 text-white p-1 rounded-full"
                                >
                                    &times;
                                </button>
                            </div>
                        ))}
                    </div>
                    <Button color="primary" variant="bordered" type="submit"
                            className="w-full h-12 text-lg font-semibold bg-black text-white">
                        Submit Space Information
                    </Button>
                </form>
            </div>
        </div>
    );
}

export default RentPage;
