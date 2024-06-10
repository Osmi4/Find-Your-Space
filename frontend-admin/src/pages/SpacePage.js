import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Button, Input, Spinner } from '@nextui-org/react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const SpacePage = () => {
    const { spaceId } = useParams();
    const navigate = useNavigate();
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [space, setSpace] = useState(null);
    const [image, setImage] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSpace = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const [spaceResponse, imageResponse] = await Promise.all([
                    axios.get(`http://localhost:8080/api/space/${spaceId}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    }),
                    axios.get(`http://localhost:8080/api/space/${spaceId}/images`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                ]);
                setSpace(spaceResponse.data);
                setImage(imageResponse.data);
                setLoading(false);
            } catch (error) {
                setError("Unfortunately there was an error when trying to load the space information. Please try again later.");
                setLoading(false);
            }
        };

        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchSpace();
        }
    }, [spaceId, isAuthenticated, loginWithRedirect]);

    const handleDelete = async () => {
        try {
            const token = localStorage.getItem('authToken');
            await axios.delete(`http://localhost:8080/api/space/${spaceId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate('/spaces');
        } catch (error) {
            //setError("Unfortunately there was an error when trying to delete the space. Please try again later.");
            toast.error("Unfortunately there was an error when trying to delete the space. Please try again later.");
        }
    };

    if (loading) {
        return <div className='flex items-center justify-center h-[100vh]'><Spinner label="Loading..." color="primary" labelColor="primary" /></div>;
    }

    if (error) {
        return <div className='flex flex-col w-full h-[100vh] items-center'>
            <h1 className='text-9xl font-bold'>404</h1>
            <p className='text-gray-700'>{error}</p>
        </div>;
    }

    return (
        <div className="p-4 2xl:ml-[10vw] 2xl:mt-[2vh]">
            <ToastContainer />
            <h1 className="text-2xl font-semibold mb-4">Space Details</h1>
            {space && (
                <div className='flex justify-between flex-col 2xl:flex-row'>
                    <div className='2xl:w-[300px] w-full'>
                        <Input label="Space ID" value={space.spaceId} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Name" value={space.spaceName} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Location" value={space.spaceLocation} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Description" value={space.spaceDescription} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Size" value={`${space.spaceSize} m2`} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Price" value={`$${space.spacePrice}`} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Type" value={space.spaceType} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Availability" value={space.availability} isReadOnly variant="bordered" className="mb-4"/>
                        <Input label="Date Added" value={new Date(space.dateAdded).toLocaleDateString()} isReadOnly variant="bordered" className="mb-4"/>
                        
                    </div>
                    {image && (
                        <div className="2xl:mr-[20vw] flex flex-col justify-center items-center">
                            <img src={image} alt="Space" className="block 2xl:w-[40vw] w-[80vw] mx-[10vw] 2xl:mx-0 mt-1 border border-gray-300 rounded-md shadow-sm" />
                            <Button className="bg-red-500 text-white px-4 py-2 rounded-md shadow-md mt-[2vh] 2xl:w-[10vw]" onClick={handleDelete}>Delete Space</Button>
                        </div>
                    )}
                    
                </div>
            )}
        </div>
    );
};

export default SpacePage;
