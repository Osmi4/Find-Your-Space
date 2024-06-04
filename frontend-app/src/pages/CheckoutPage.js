import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Input, Button, Card, Textarea } from '@nextui-org/react';
import { ClipLoader } from 'react-spinners';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const CheckoutPage = () => {
    let { id, startDate, endDate } = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const [formData, setFormData] = useState({
        description: '',
    });

    useEffect(() => {
        const fetchSpaceDetails = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.get(`http://localhost:8080/api/space/${id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setItem(response.data);
                setLoading(false);
            } catch (error) {
                console.error("Error fetching space details:", error);
                setError('Error fetching space details');
                setLoading(false);
            }
        };

        fetchSpaceDetails();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const token = localStorage.getItem('authToken');
            const startDateTime = new Date(startDate);
            const endDateTime = new Date(endDate);

            const bookingData = {
                spaceId: id,
                startDateTime,
                endDateTime,
                description: formData.description,
            };

            const response = await axios.post(`http://localhost:8080/api/booking`, bookingData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            console.log('Booking request successful:', response.data);
            toast.success('Booking request successful');

            setTimeout(() => {
                navigate(`/find`);
            }, 2000); // Wait for 2 seconds before navigating
        } catch (error) {
            console.error("Error requesting booking:", error);
            toast.error('Error requesting booking. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    if (loading && !item) {
        return (
            <div className="flex justify-center items-center h-screen">
                <ClipLoader size={35} color={"#123abc"} loading={loading} />
            </div>
        );
    }

    if (!item) return <div>{error || 'Loading...'}</div>;

    const startDateTime = new Date(startDate);
    const endDateTime = new Date(endDate);
    const differenceInTime = endDateTime - startDateTime;
    const numberOfDays = Math.ceil(differenceInTime / (1000 * 3600 * 24));
    const price = item.spacePrice;
    const totalPrice = Math.ceil(price * numberOfDays);

    return (
        <div className="flex flex-col xl:flex-row xl:mt-[70px] xl:mx-[100px] mt-[4vh] p-4 gap-[5vw]">
            <ToastContainer />
            <Card className="mb-4 xl:mb-0 xl:w-1/2 p-4">
                <h1 className="text-2xl xl:text-4xl font-semibold mb-4">Checkout</h1>
                <form onSubmit={handleSubmit} className="flex flex-col gap-4">
                    <Textarea
                        name="description"
                        label="Description"
                        placeholder="Enter booking description"
                        variant="bordered"
                        value={formData.description}
                        onChange={handleChange}
                        className="w-full"
                    />
                    <Button color="primary" variant="flat" type="submit" className="w-full text-lg py-4">
                        Request a Booking
                    </Button>
                </form>
            </Card>
            <Card className="xl:w-1/2 p-4">
                <p className="text-gray-700 font-bold text-xl">Chosen Location</p>
                <hr className="border-gray-400 my-2" />
                <div className="flex flex-col xl:flex-row items-center">
                    <div className="font-semibold mt-4 xl:mt-0 xl:ml-4 text-center xl:text-left">
                        <p className="text-2xl">{item.spaceName}</p>
                        <p className="text-xs mt-2 font-medium">Duration: {numberOfDays} days</p>
                        <p className="text-xl mt-2">Price per day: ${Math.ceil(price)}</p>
                    </div>
                </div>
                <hr className="border-gray-400 my-4" />
                <div className="flex justify-between">
                    <p className="text-lg font-medium">Total</p>
                    <p className="text-lg font-medium">${totalPrice}</p>
                </div>
            </Card>
        </div>
    );
};

export default CheckoutPage;
