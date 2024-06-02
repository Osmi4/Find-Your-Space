import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Checkbox, Input, Button } from "@nextui-org/react";
import axios from "axios";
import { useAuth0 } from '@auth0/auth0-react';

const FindSpacePage = () => {
    const navigate = useNavigate();
    const { isAuthenticated, getAccessTokenSilently } = useAuth0();

    const [spaces, setSpaces] = useState([]);
    const [filter, setFilter] = useState({
        categories: [
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
        ],
        cities: ["Poznan", "Wroclaw", "Krakow", "Warsaw", "Gdansk", "Lodz"],
    });

    const [selectedFilters, setSelectedFilters] = useState({
        categories: [],
        cities: [],
        spaceName: '',
        spaceLocation: '',
        spaceSizeLowerBound: 0,
        spaceSizeUpperBound: Number.MAX_SAFE_INTEGER,
        spacePriceLowerBound: 0,
        spacePriceUpperBound: Number.MAX_SAFE_INTEGER,
    });

    useEffect(() => {
        const fetchSpaces = async () => {
            if (isAuthenticated) {
                const token = await getAccessTokenSilently();
                localStorage.setItem('authToken', token);

                try {
                    const response = await axios.post("http://localhost:8080/api/space/search", selectedFilters, {
                        headers: { Authorization: `Bearer ${token}` }
                    });
                    const spacesWithImages = await Promise.all(response.data.content.map(async space => {
                        const imageUrl = await fetchImage(space.spaceId, token);
                        return { ...space, imageUrl };
                    }));
                    setSpaces(spacesWithImages);
                } catch (error) {
                    console.error("Error fetching spaces:", error);
                }
            }
        };

        fetchSpaces();
    }, [isAuthenticated, selectedFilters, getAccessTokenSilently]);

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

    const handleCheckboxChange = (filterType, value) => {
        setSelectedFilters(prevFilters => {
            const updatedFilters = { ...prevFilters };
            const index = updatedFilters[filterType].indexOf(value);

            if (index !== -1) {
                updatedFilters[filterType] = updatedFilters[filterType].filter(item => item !== value);
            } else {
                updatedFilters[filterType] = [...updatedFilters[filterType], value];
            }

            return updatedFilters;
        });
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSelectedFilters({ ...selectedFilters, [name]: value });
    };

    const handleFilterSubmit = async () => {
        if (isAuthenticated) {
            const token = await getAccessTokenSilently();
            localStorage.setItem('authToken', token);

            const filtersToSend = {
                ...selectedFilters,
                spaceSizeUpperBound: selectedFilters.spaceSizeUpperBound === Number.MAX_SAFE_INTEGER ? 1000000 : selectedFilters.spaceSizeUpperBound,
                spacePriceUpperBound: selectedFilters.spacePriceUpperBound === Number.MAX_SAFE_INTEGER ? 1000000 : selectedFilters.spacePriceUpperBound,
            };

            try {
                const response = await axios.post("http://localhost:8080/api/space/search", filtersToSend, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                const spacesWithImages = await Promise.all(response.data.content.map(async space => {
                    const imageUrl = await fetchImage(space.spaceId, token);
                    return { ...space, imageUrl };
                }));
                setSpaces(spacesWithImages);
            } catch (error) {
                console.error("Error fetching spaces:", error);
            }
        }
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
                        categories: [],
                        cities: [],
                        spaceName: '',
                        spaceLocation: '',
                        spaceSizeLowerBound: 0,
                        spaceSizeUpperBound: Number.MAX_SAFE_INTEGER,
                        spacePriceLowerBound: 0,
                        spacePriceUpperBound: Number.MAX_SAFE_INTEGER,
                    })} className="text-xs text-gray-400 underline hover:text-gray-700">Clear Filters</button>
                </div>
                <p className="text-sm font-semibold my-2.5">Categories</p>
                <div className="flex flex-col">
                    {filter.categories.map(category => (
                        <Checkbox
                            key={category}
                            classNames={{ label: "text-small" }}
                            className='mt-1'
                            id={category}
                            isSelected={selectedFilters.categories.includes(category)}
                            onValueChange={() => handleCheckboxChange('categories', category)}
                        >
                            {category}
                        </Checkbox>
                    ))}
                </div>

                <p className="text-sm font-semibold my-2.5">Cities</p>
                <div className="flex flex-col">
                    {filter.cities.map(city => (
                        <Checkbox
                            key={city}
                            classNames={{ label: "text-small" }}
                            className='mt-1'
                            id={city}
                            isSelected={selectedFilters.cities.includes(city)}
                            onValueChange={() => handleCheckboxChange('cities', city)}
                        >
                            {city}
                        </Checkbox>
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

                <Button onClick={handleFilterSubmit} className="mt-4">Apply Filters</Button>
            </div>

            <div className="flex w-full mt-12 gap-y-5 gap-x-3.5 flex-wrap">
                {spaces.map(item => (
                    <div key={item.spaceId} className="w-72 bg-white rounded-lg shadow-md overflow-hidden">
                        <img src={item.imageUrl} alt={item.spaceName} className="w-full h-48 object-cover" />
                        <div className="p-4">
                            <h1 className="font-bold text-lg mb-2 hover:underline cursor-pointer">{item.spaceName}</h1>
                            <p className="text-sm text-gray-600 mb-2">{item.spaceLocation}</p>
                            <p className="text-sm text-gray-600 mb-2">Size: {item.spaceSize} sq ft</p>
                            <p className="text-sm text-gray-600 mb-2">Price: ${item.spacePrice}</p>
                            <Button onClick={() => openSpacePage(item.spaceId)} className="mt-2 bg-blue-500 text-white w-full">View Details</Button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default FindSpacePage;
