import { useState } from 'react';
import { Input, Textarea, Button } from "@nextui-org/react";
import { Select } from 'antd';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';

const RentPage = () => {
    const spaceTypes = [
        "OFFICE",
        "MEETING ROOM",
        "EVENT SPACE",
        "COWORKING SPACE",
        "RETAIL SPACE",
        "STORAGE SPACE",
        "PARKING SPACE",
        "WAREHOUSE",
        "INDUSTRIAL SPACE",
        "OTHER"
    ];

    const { isAuthenticated, user, loginWithRedirect } = useAuth0();

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

    const mapSpaceType = (type) => {
        return type.replace(' ', '_');
    };

    const handleSpaceChange = (e) => {
        const { name, value } = e.target;
        setSpaceInfo({ ...spaceInfo, [name]: value });
    };

    const handleSpaceTypeChange = (value) => {
        setSpaceInfo({ ...spaceInfo, spaceType: mapSpaceType(value) });
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
            spaceDescription: spaceInfo.spaceDescription,
            spaceType: spaceInfo.spaceType
        };

        if (isAuthenticated) {
            const token = localStorage.getItem('authToken');
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
        } else {
            loginWithRedirect(); // Redirect to login if not authenticated
        }
    };

    const uploadImages = async (spaceId) => {
        const token = localStorage.getItem('authToken');
        const formData = new FormData();
        spaceInfo.images.forEach((image) => {
            formData.append('image', image);
        });

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
                <h1 className="text-2xl sm:text-4xl font-semibold mb-[10px] text-center sm:mt-0 sm:mb-8">Rent your space</h1>
                <p className='text-gray-700 font-bold text-sm text-center mb-[10px] sm:text-lg sm:text-left ml-[0.5vw]'>Please enter the details about your space below :</p>
                <form onSubmit={handleSpaceSubmit} className="flex flex-col gap-y-[15px]">
                    <div className="flex sm:gap-x-6 gap-x-[10px]">
                        <Input
                            name="spaceName"
                            label="Name"
                            placeholder="Enter name"
                            variant="bordered"
                            value={spaceInfo.spaceName}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                        <Input
                            name="rentCity"
                            label="City"
                            placeholder="Enter city"
                            variant="bordered"
                            value={spaceInfo.rentCity}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                    </div>
                    <div className="flex sm:gap-x-6 gap-x-[10px]">
                        <Input
                            name="rentZipCode"
                            label="ZIP Code"
                            placeholder="Enter ZIP code"
                            variant="bordered"
                            value={spaceInfo.rentZipCode}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                        <Input
                            name="rentAddress"
                            label="Address"
                            placeholder="Enter address"
                            variant="bordered"
                            value={spaceInfo.rentAddress}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                    </div>
                    <div className="flex sm:gap-x-6 gap-x-[10px]">
                        <Input
                            name="rentPrice"
                            label="Price per month in $"
                            placeholder="Enter price "
                            variant="bordered"
                            value={spaceInfo.rentPrice}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                        <Input
                            name="size"
                            label="Size in m2"
                            placeholder="Enter size"
                            variant="bordered"
                            value={spaceInfo.size}
                            onChange={handleSpaceChange}
                            className="w-1/2"
                        />
                    </div>
                    <p className='text-gray-700 font-bold text-sm sm:text-lg sm:text-left ml-[0.5vw]'>Choose Type of the space :</p>
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
                        placeholder="Enter description"
                        variant="bordered"
                        value={spaceInfo.spaceDescription}
                        onChange={handleSpaceChange}
                        className="mb-6"
                    />
                    <div className="mb-6">
                        <label className="block text-gray-700 text-sm font-bold mb-4 ml-[0.5vw]">
                            Upload Images
                        </label>
                        <input
                            type="file"
                            name="images"
                            multiple
                            onChange={handleImageChange}
                            className="hidden "
                            id="file-upload"
                        />
                        <label
                            htmlFor="file-upload"
                            className="cursor-pointer px-4 py-2 rounded-xl shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-500 active:bg-blue-500 active:scale-95"
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
