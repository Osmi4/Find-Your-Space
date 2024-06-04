import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Input, Button, CardFooter, Card, CardHeader, CardBody } from '@nextui-org/react';

const MySpacesPage = () => {
    const [spaces, setSpaces] = useState([]);
    const [loading, setLoading] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const navigate = useNavigate();

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
        ],
        availabilities: [
            "NOT_RELEASED",
            "AVAILABLE",
            "BLOCKED"
        ]
    });

    const [selectedFilters, setSelectedFilters] = useState({
        spaceType: [],
        spaceName: '',
        spaceLocation: '',
        spaceSizeLowerBound: 0,
        spaceSizeUpperBound: 100000,
        spacePriceLowerBound: 0,
        spacePriceUpperBound: 100000,
        availability: [],
        type: 'ASC', // Default sorting type
        variable: 'PRICE', // Default sorting variable
        page: 0,
        size: 10
    });

    useEffect(() => {
        const fetchSpaces = async () => {
            try {
                const token = localStorage.getItem('authToken');
                if (!token) throw new Error("No auth token found");

                let response;

                    if(selectedFilters.spaceType.length === 0 && selectedFilters.availability.length === 0) {
                        response = await axios.get("http://localhost:8080/api/space/all-my-spaces", {
                            headers: { Authorization: `Bearer ${token}` },
                            params: { page: selectedFilters.page, size: selectedFilters.size }
                        });
                    } else {
                        response = await axios.post('http://localhost:8080/api/space/my-spaces', selectedFilters, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'Content-Type': 'application/json'
                        },
                        params: { page: selectedFilters.page, size: selectedFilters.size }
                    });
                    }


                const spacesWithImages = await Promise.all(response.data.content.map(async space => {
                    if (space.spaceId) {
                        const imageUrl = await fetchImage(space.spaceId, token);
                        return { ...space, imageUrl };
                    }
                    return space;
                }));

                setSpaces(spacesWithImages);
                setTotalPages(response.data.totalPages);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching spaces:', error);
                setLoading(false);
            }
        };

        fetchSpaces();
    }, [selectedFilters]);

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

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSelectedFilters({ ...selectedFilters, [name]: value });
    };

    const handleCheckboxChange = (e) => {
        const { name, value, checked } = e.target;
        setSelectedFilters(prevFilters => {
            const newValues = checked
                ? [...prevFilters[name], value]
                : prevFilters[name].filter(item => item !== value);
            return { ...prevFilters, [name]: newValues };
        });
    };

    const handleFilterSubmit = async () => {
        setSelectedFilters({ ...selectedFilters, page: 0 });
    };

    const handlePageChange = (newPage) => {
        setSelectedFilters((prevFilters) => ({ ...prevFilters, page: newPage }));
    };

    const handleDelete = async (spaceId) => {
        if (!window.confirm('Are you sure you want to delete this space?')) return;

        try {
            const token = localStorage.getItem('authToken');
            if (!token) throw new Error("No auth token found");

            await axios.delete(`http://localhost:8080/api/space/${spaceId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setSpaces(spaces.filter(space => space.spaceId !== spaceId));
        } catch (error) {
            console.error('Error deleting space:', error);
        }
    };

    const openSpacePage = (id) => {
        navigate(`/space/${id}/edit`);
    };

    const viewBookings = (spaceId) => {
        navigate(`/space/${spaceId}/bookings`);
    };

    return (
        <div className="flex mt-10 gap-24 ml-24">
            <div className="flex-col w-[20vw] ml-10 mr-5 mt-10">
                <div className="flex gap-5">
                    <h1 className="text-xl font-semibold">Filters</h1>
                    <button onClick={() => setSelectedFilters({
                        spaceType: [],
                        spaceName: '',
                        spaceLocation: '',
                        spaceSizeLowerBound: 0,
                        spaceSizeUpperBound: Number.MAX_SAFE_INTEGER,
                        spacePriceLowerBound: 0,
                        spacePriceUpperBound: Number.MAX_SAFE_INTEGER,
                        availability: [],
                        type: 'ASC',
                        variable: 'PRICE',
                        page: 0,
                        size: 10
                    })} className="text-xs text-gray-400 underline hover:text-gray-700">Clear Filters</button>
                </div>
                <p className="text-sm font-semibold mt-2.5 mb-1.5">Space Types</p>
                <div className="flex flex-col">
                    {filter.spaceTypes.map(spaceType => (
                        <div key={spaceType} className="mt-1">
                            <input
                                type="checkbox"
                                id={spaceType}
                                name="spaceType"
                                value={spaceType}
                                checked={selectedFilters.spaceType.includes(spaceType)}
                                onChange={handleCheckboxChange}
                                className="mr-2"
                            />
                            <label htmlFor={spaceType} className="text-small font-default text-gray-600">{spaceType[0].toUpperCase() + spaceType.slice(1).replace("_"," ").toLowerCase()}</label>
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

                <p className="text-sm font-semibold my-2.5">Space Size Range (sq m)</p>
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
                <div className="flex flex-col mb-2">
                    {filter.availabilities.map(avail => (
                        <div key={avail} className="mt-1">
                            <input
                                type="checkbox"
                                id={avail}
                                name="availability"
                                value={avail}
                                checked={selectedFilters.availability.includes(avail)}
                                onChange={handleCheckboxChange}
                                className="mr-2"
                            />
                            <label htmlFor={avail} className="text-small text-gray-600">{avail[0].toUpperCase() + avail.slice(1).replace("_"," ").toLowerCase()}</label>
                        </div>
                    ))}
                </div>

                <p className="text-sm font-semibold my-2.5">Sort By</p>
                <div className="flex flex-col mb-2">
                    <div>
                        <input
                            type="radio"
                            id="PRICE"
                            name="variable"
                            value="PRICE"
                            checked={selectedFilters.variable === "PRICE"}
                            onChange={handleInputChange}
                            className="mr-2"
                        />
                        <label htmlFor="PRICE" className="text-small text-gray-600">Price</label>
                    </div>
                    <div>
                        <input
                            type="radio"
                            id="SIZE"
                            name="variable"
                            value="SIZE"
                            checked={selectedFilters.variable === "SIZE"}
                            onChange={handleInputChange}
                            className="mr-2"
                        />
                        <label htmlFor="SIZE" className="text-small text-gray-600">Size</label>
                    </div>
                </div>

                <p className="text-sm font-semibold my-2.5">Sort Direction</p>
                <div className="flex flex-col mb-2">
                    <div>
                        <input
                            type="radio"
                            id="ASC"
                            name="type"
                            value="ASC"
                            checked={selectedFilters.type === "ASC"}
                            onChange={handleInputChange}
                            className="mr-2"
                        />
                        <label htmlFor="ASC" className="text-small">Ascending</label>
                    </div>
                    <div>
                        <input
                            type="radio"
                            id="DESC"
                            name="type"
                            value="DESC"
                            checked={selectedFilters.type === "DESC"}
                            onChange={handleInputChange}
                            className="mr-2"
                        />
                        <label htmlFor="DESC" className="text-small">Descending</label>
                    </div>
                </div>
            </div>
            <div>
            <div className="flex w-[75vw] mt-12 gap-y-5 gap-x-3.5 flex-wrap">
                {loading ? (
                    <p>Loading...</p>
                ) : (
                    spaces.map(item => (
                        <Card key={item.spaceId} className="w-[15.75vw] bg-black" isPressable onPress={() => openSpacePage(item.spaceId)} >
                        <CardHeader className="pb-0 pt-2 flex-col items-start">
                        <p className="text-md text-white font-bold mb-0.25">{item.spaceLocation}</p>
                        <div className="flex mb-0.5">
                            <p className="text-sm text-white">{item.spaceSize} m<sup>2</sup></p>
                            <p className="text-sm text-white ml-[160px]">${item.spacePrice}/mo</p>
                        </div>     
                        </CardHeader>
                        <CardBody className="overflow-visible p-0">
                        <img src={item.imageUrl} alt={item.spaceName} className="object-cover w-full" />
                        </CardBody>
                        <CardFooter className="flex justify-between items-center p-2 gap-[5px]">
                            <Button onClick={() => openSpacePage(item.spaceId)} className="mt-2 bg-blue-500 text-white w-full font-bold">View Details</Button>
                            <Button onClick={() => handleDelete(item.spaceId)} className="mt-2 bg-red-500 text-white w-full font-bold">Delete</Button>
                            <Button onClick={() => viewBookings(item.spaceId)} className="mt-2 bg-green-500 text-white w-full font-bold">Bookings</Button>
                        </CardFooter>
                        </Card>
                    ))
                )}
            </div>

            <div className="flex justify-start w-full mt-6">
                <Button
                    onClick={() => handlePageChange(Math.max(0, selectedFilters.page - 1))}
                    disabled={selectedFilters.page === 0}
                    className="mx-1 bg-black text-white"
                    variant="bordered"
                    color = "danger"
                >
                    Previous
                </Button>
                <Button
                    onClick={() => handlePageChange(Math.min(totalPages - 1, selectedFilters.page + 1))}
                    disabled={selectedFilters.page === totalPages - 1}
                    className="mx-1 bg-black text-white"
                    color="primary"
                    variant="bordered"
                >
                    Next
                </Button>
            </div>
            </div>
        </div>
    );
};

export default MySpacesPage;
