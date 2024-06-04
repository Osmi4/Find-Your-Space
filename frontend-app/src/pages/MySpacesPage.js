import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Input, Button, Card, CardHeader, CardBody, CardFooter, Divider } from '@nextui-org/react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { ClipLoader } from 'react-spinners';

const MySpacesPage = () => {
    const [spaces, setSpaces] = useState([]);
    const [loading, setLoading] = useState(true);
    const [totalPages, setTotalPages] = useState(0);
    const navigate = useNavigate();

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

    const availabilities = [
        "NOT_RELEASED",
        "AVAILABLE",
        "BLOCKED"
    ];

    const [selectedFilters, setSelectedFilters] = useState({
        spaceType: [...spaceTypes],
        spaceName: '',
        spaceLocation: '',
        spaceSizeLowerBound: 0,
        spaceSizeUpperBound: 100000,
        spacePriceLowerBound: 0,
        spacePriceUpperBound: 100000,
        availability: [...availabilities],
        type: 'ASC', // Default sorting type
        variable: 'PRICE', // Default sorting variable
        page: 0,
        size: 9
    });

    useEffect(() => {
        const fetchSpaces = async () => {
            setLoading(true);
            try {
                const token = localStorage.getItem('authToken');
                if (!token) throw new Error("No auth token found");

                const response = await axios.post('http://localhost:8080/api/space/my-spaces', selectedFilters, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    },
                    params: { page: selectedFilters.page, size: selectedFilters.size }
                });

                const spacesWithImages = await Promise.all(response.data.content.map(async space => {
                    if (space.spaceId) {
                        const imageUrl = await fetchImage(space.spaceId, token);
                        const canDeleteStatus = await fetchCanDelete(space.spaceId, token);
                        return { ...space, imageUrl, canDelete: canDeleteStatus };
                    }
                    return space;
                }));

                setSpaces(spacesWithImages);
                setTotalPages(response.data.totalPages);
            } catch (error) {
                console.error('Error fetching spaces:', error);
                toast.error('Error fetching spaces');
            }
            setLoading(false);
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

    const fetchCanDelete = async (spaceId, token) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/space/${spaceId}/can-delete`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching can-delete status:', error);
            return false;
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
            toast.success('Space deleted successfully');
        } catch (error) {
            console.error('Error deleting space:', error);
            toast.error('Error deleting space');
        }
    };

    const openSpacePage = (id) => {
        navigate(`/space/${id}/edit`);
    };

    const viewBookings = (spaceId) => {
        navigate(`/space/${spaceId}/bookings`);
    };

    return (
        <div className="container mx-auto p-4">
            <ToastContainer />
            <div className="flex flex-col lg:flex-row gap-4">
                <div className="w-full lg:w-1/4">
                    <div className="flex justify-between items-center mb-4">
                        <h1 className="text-xl font-semibold">Filters</h1>
                        <button onClick={() => setSelectedFilters({
                            spaceType: [],
                            spaceName: '',
                            spaceLocation: '',
                            spaceSizeLowerBound: 0,
                            spaceSizeUpperBound: 100000,
                            spacePriceLowerBound: 0,
                            spacePriceUpperBound: 100000,
                            availability: [],
                            type: 'ASC',
                            variable: 'PRICE',
                            page: 0,
                            size: 9
                        })} className="text-xs text-gray-400 underline hover:text-gray-700">Clear Filters</button>
                    </div>
                    <div className="mb-4">
                        <p className="text-sm font-semibold">Space Types</p>
                        <div className="flex flex-col">
                            {spaceTypes.map(spaceType => (
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
                                    <label htmlFor={spaceType} className="text-small font-default text-gray-600">{spaceType[0].toUpperCase() + spaceType.slice(1).replace("_", " ").toLowerCase()}</label>
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className="mb-4">
                        <p className="text-sm font-semibold">Space Name</p>
                        <Input
                            type="text"
                            name="spaceName"
                            value={selectedFilters.spaceName}
                            onChange={handleInputChange}
                            placeholder="Enter space name"
                        />
                    </div>

                    <div className="mb-4">
                        <p className="text-sm font-semibold">Space Location</p>
                        <Input
                            type="text"
                            name="spaceLocation"
                            value={selectedFilters.spaceLocation}
                            onChange={handleInputChange}
                            placeholder="Enter space location"
                        />
                    </div>

                    <div className="mb-4">
                        <p className="text-sm font-semibold">Space Size Range (sq m)</p>
                        <div className="flex flex-col gap-2">
                            <Input
                                type="number"
                                name="spaceSizeLowerBound"
                                value={selectedFilters.spaceSizeLowerBound}
                                onChange={handleInputChange}
                                placeholder="Min size"
                            />
                            <Input
                                type="number"
                                name="spaceSizeUpperBound"
                                value={selectedFilters.spaceSizeUpperBound}
                                onChange={handleInputChange}
                                placeholder="Max size"
                            />
                        </div>
                    </div>

                    <div className="mb-4">
                        <p className="text-sm font-semibold">Space Price Range ($)</p>
                        <div className="flex flex-col gap-2">
                            <Input
                                type="number"
                                name="spacePriceLowerBound"
                                value={selectedFilters.spacePriceLowerBound}
                                onChange={handleInputChange}
                                placeholder="Min price"
                            />
                            <Input
                                type="number"
                                name="spacePriceUpperBound"
                                value={selectedFilters.spacePriceUpperBound}
                                onChange={handleInputChange}
                                placeholder="Max price"
                            />
                        </div>
                    </div>

                    <div className="mb-4">
                        <p className="text-sm font-semibold">Availability</p>
                        <div className="flex flex-col">
                            {availabilities.map(avail => (
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
                                    <label htmlFor={avail} className="text-small text-gray-600">{avail[0].toUpperCase() + avail.slice(1).replace("_", " ").toLowerCase()}</label>
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className="mb-4">
                        <p className="text-sm font-semibold">Sort By</p>
                        <div className="flex flex-col">
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
                    </div>

                    <div className="mb-4">
                        <p className="text-sm font-semibold">Sort Direction</p>
                        <div className="flex flex-col">
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
                </div>

                <div className="flex-1">
                    {loading ? (
                        <div className="flex justify-center items-center">
                            <ClipLoader size={35} color={"#123abc"} loading={loading} />
                        </div>
                    ) : (
                        <>
                            {spaces.length === 0 ? (
                                <p>No spaces match the current filters.</p>
                            ) : (
                                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                                    {spaces.map(item => (
                                        <Card key={item.spaceId} className="w-full bg-default-900" isPressable onPress={() => openSpacePage(item.spaceId)}>
                                            <CardHeader className="pb-0 pt-2 flex-col">
                                                <p className="text-sm text-white font-bold mb-0.25">{item.spaceLocation}</p>
                                                <Divider className="text-default-700 w-full my-0.5"/>
                                                <div className="flex mb-0.5 justify-between">
                                                    <p className="text-sm text-white">{item.spaceSize} m<sup>2</sup></p>
                                                    <p className="text-sm text-white ml-8">${item.spacePrice}/mo</p> {/* Adjusted the margin-left */}
                                                </div>
                                            </CardHeader>
                                            <CardBody className="overflow-visible p-0">
                                                <img src={item.imageUrl} alt={item.spaceName}
                                                     className="object-cover w-full h-48"/>
                                            </CardBody>
                                            <CardFooter className="flex justify-between items-center p-2 gap-[5px]">
                                                <Button onClick={() => openSpacePage(item.spaceId)} className="bg-blue-500 text-white w-full font-bold">View Details</Button>
                                                <Button onClick={() => handleDelete(item.spaceId)} disabled={!item.canDelete} className={`w-full font-bold ${item.canDelete ? 'bg-red-500 text-white' : 'bg-gray-500 text-gray-300'}`}>Delete</Button>
                                                <Button onClick={() => viewBookings(item.spaceId)} className="bg-green-500 text-white w-full font-bold">Bookings</Button>
                                            </CardFooter>
                                        </Card>
                                    ))}
                                </div>
                            )}
                        </>
                    )}
                    <div className="flex justify-between mt-6">
                        <Button
                            onClick={() => handlePageChange(Math.max(0, selectedFilters.page - 1))}
                            disabled={selectedFilters.page === 0}
                            className="bg-black text-white"
                        >
                            Previous
                        </Button>
                        <span className="text-sm text-gray-600">Page {selectedFilters.page + 1} of {totalPages}</span>
                        <Button
                            onClick={() => handlePageChange(Math.min(totalPages - 1, selectedFilters.page + 1))}
                            disabled={selectedFilters.page === totalPages - 1}
                            className="bg-black text-white"
                        >
                            Next
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MySpacesPage;
