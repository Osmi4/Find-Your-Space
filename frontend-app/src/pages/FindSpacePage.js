import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Input, Button } from "@nextui-org/react";
import axios from "axios";

const FindSpacePage = () => {
    const navigate = useNavigate();
    const [spaces, setSpaces] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    const [filter, setFilter] = useState({
        spaceTypes: [
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
        ]
    });

    const [selectedFilters, setSelectedFilters] = useState({
        spaceType: 'OFFICE',
        spaceName: '',
        spaceLocation: '',
        spaceSizeLowerBound: 0,
        spaceSizeUpperBound: Number.MAX_SAFE_INTEGER,
        spacePriceLowerBound: 0,
        spacePriceUpperBound: Number.MAX_SAFE_INTEGER,
        availability: 'AVAILABLE',
        page: 0,
        size: 10
    });

    useEffect(() => {
        const fetchSpaces = async () => {
            const token = localStorage.getItem('authToken');

            if (token) {
                try {
                    const response = await axios.post("http://localhost:8080/api/space/search", selectedFilters, {
                        headers: { Authorization: `Bearer ${token}` },
                        params: { page: selectedFilters.page, size: selectedFilters.size }
                    });

                    const spacesWithImages = await Promise.all(response.data.content.map(async space => {
                        const imageUrl = await fetchImage(space.spaceId, token);
                        return { ...space, imageUrl };
                    }));

                    setSpaces(spacesWithImages);
                    setTotalPages(response.data.totalPages);
                } catch (error) {
                    console.error("Error fetching spaces:", error);
                }
            }
        };

        fetchSpaces();
    }, [selectedFilters]);

    const fetchImage = async (spaceId, token) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/space/${spaceId}/images`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching image:', error);
            return null;
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSelectedFilters({ ...selectedFilters, [name]: value });
    };

    const handleFilterSubmit = async () => {
        setSelectedFilters({ ...selectedFilters, page: 0 });
    };

    const handlePageChange = (newPage) => {
        setSelectedFilters((prevFilters) => ({ ...prevFilters, page: newPage }));
    };

    const openSpacePage = (id) => {
        navigate(`/space/${id}`);
    };

    return (
        <div className="flex mt-10 gap-24 ml-24">
            <div className="flex-col w-64 ml-10 mr-5 mt-10">
                <div className="flex gap-5">
                    <h1 className="text-xl font-semibold">Filters</h1>
                    <button onClick={() => setSelectedFilters({
                        spaceType: 'OFFICE',
                        spaceName: '',
                        spaceLocation: '',
                        spaceSizeLowerBound: 0,
                        spaceSizeUpperBound: Number.MAX_SAFE_INTEGER,
                        spacePriceLowerBound: 0,
                        spacePriceUpperBound: Number.MAX_SAFE_INTEGER,
                        availability: 'AVAILABLE',
                        page: 0,
                        size: 10
                    })} className="text-xs text-gray-400 underline hover:text-gray-700">Clear Filters</button>
                </div>
                <p className="text-sm font-semibold my-2.5">Space Types</p>
                <div className="flex flex-col">
                    {filter.spaceTypes.map(spaceType => (
                        <div key={spaceType} className="mt-1">
                            <input
                                type="radio"
                                id={spaceType}
                                name="spaceType"
                                value={spaceType}
                                checked={selectedFilters.spaceType === spaceType}
                                onChange={handleInputChange}
                                className="mr-2"
                            />
                            <label htmlFor={spaceType} className="text-small">{spaceType}</label>
                        </div>
                    ))}
                </div>

                <p className="text-sm font-semibold my-2.5">Space Name</p>
                <Input
                    type="text"
                    name="spaceName"
                    value={selectedFilters.spaceName}
                    onChange={handleInputChange}
                    placeholder="Enter space name"
                    className="mb-2"
                />

                <p className="text-sm font-semibold my-2.5">Space Location</p>
                <Input
                    type="text"
                    name="spaceLocation"
                    value={selectedFilters.spaceLocation}
                    onChange={handleInputChange}
                    placeholder="Enter space location"
                    className="mb-2"
                />

                <p className="text-sm font-semibold my-2.5">Space Size Range (sq ft)</p>
                <Input
                    type="number"
                    name="spaceSizeLowerBound"
                    value={selectedFilters.spaceSizeLowerBound}
                    onChange={handleInputChange}
                    placeholder="Min size"
                    className="mb-2"
                />
                <Input
                    type="number"
                    name="spaceSizeUpperBound"
                    value={selectedFilters.spaceSizeUpperBound}
                    onChange={handleInputChange}
                    placeholder="Max size"
                    className="mb-2"
                />

                <p className="text-sm font-semibold my-2.5">Space Price Range ($)</p>
                <Input
                    type="number"
                    name="spacePriceLowerBound"
                    value={selectedFilters.spacePriceLowerBound}
                    onChange={handleInputChange}
                    placeholder="Min price"
                    className="mb-2"
                />
                <Input
                    type="number"
                    name="spacePriceUpperBound"
                    value={selectedFilters.spacePriceUpperBound}
                    onChange={handleInputChange}
                    placeholder="Max price"
                    className="mb-2"
                />

                <p className="text-sm font-semibold my-2.5">Availability</p>
                <div className="mb-2">
                    <select
                        name="availability"
                        value={selectedFilters.availability}
                        onChange={handleInputChange}
                        className="w-full px-3 py-2 border border-gray-300 rounded-md"
                    >
                        <option value="NOT_RELEASED">Not Released</option>
                        <option value="AVAILABLE">Available</option>
                        <option value="BLOCKED">Blocked</option>
                    </select>
                </div>

                <Button onClick={handleFilterSubmit} className="mt-4">Apply Filters</Button>
            </div>

            <div className="flex w-full mt-12 gap-y-5 gap-x-3.5 flex-wrap">
                {spaces.map(item => (
                    <div key={item.spaceId} className="w-72 bg-white rounded-lg shadow-md overflow-hidden">
                        <img src={item.imageUrl} alt={item.spaceName} className="w-full h-48 object-cover" />
                        <div className="p-4">
                            <h1 className="font-bold text-lg mb-2 hover:underline cursor-pointer" onClick={() => openSpacePage(item.spaceId)}>{item.spaceName}</h1>
                            <p className="text-sm text-gray-600 mb-2">{item.spaceLocation}</p>
                            <p className="text-sm text-gray-600 mb-2">Size: {item.spaceSize} sq ft</p>
                            <p className="text-sm text-gray-600 mb-2">Price: ${item.spacePrice}</p>
                            <Button onClick={() => openSpacePage(item.spaceId)} className="mt-2 bg-blue-500 text-white w-full">View Details</Button>
                        </div>
                    </div>
                ))}
            </div>

            <div className="flex justify-center w-full mt-6">
                <Button
                    onClick={() => handlePageChange(Math.max(0, selectedFilters.page - 1))}
                    disabled={selectedFilters.page === 0}
                    className="mx-1"
                >
                    Previous
                </Button>
                <Button
                    onClick={() => handlePageChange(Math.min(totalPages - 1, selectedFilters.page + 1))}
                    disabled={selectedFilters.page === totalPages - 1}
                    className="mx-1"
                >
                    Next
                </Button>
            </div>
        </div>
    );
};

export default FindSpacePage;
