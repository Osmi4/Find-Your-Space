import React, {useState, useEffect} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import {RangeCalendar, Button} from "@nextui-org/react";
import {today, getLocalTimeZone} from "@internationalized/date";
import axios from "axios";
import {ClipLoader} from 'react-spinners';
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const SpacePage = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState(null);
    const [numberOfDays, setNumberOfDays] = useState(0);
    const [loading, setLoading] = useState(true);
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const now = today(getLocalTimeZone());
    const [isOwner, setIsOwner] = useState(false);
    const [userDetails, setUserDetails] = useState(null);
    const [isAvailable, setIsAvailable] = useState(true);


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
        const fetchUserDetails = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const userResponse = await axios.get('http://localhost:8080/api/user/my-details', {
                    headers: {Authorization: `Bearer ${token}`}
                });
                setUserDetails(userResponse.data);
            } catch (error) {
                console.error("Error fetching user details:", error);
                toast.error('Failed to fetch user details');
            }
        };

        fetchUserDetails();
    }, []);

    useEffect(() => {
        if (!userDetails) return;
        const fetchData = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const response = await axios.get(`http://localhost:8080/api/space/${id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                let spaceData = response.data;
                const imageUrl = await fetchImage(spaceData.spaceId, token);
                setIsOwner(spaceData.owner.userId === userDetails.userId);
                spaceData.image = imageUrl;
                setItem(spaceData);
                setLoading(false);
            } catch (error) {
                console.error("Error fetching space data", error);
                setLoading(false);
            }
        };
        if (userDetails) {
            fetchData();
        }
    }, [id, userDetails]);

    const isDateUnavailable = (date) => date.compare(now) < 0;

    const calendarChangeHandler = (date) => {
        const start = new Date(date.start.year, date.start.month - 1, date.start.day);
        const end = new Date(date.end.year, date.end.month - 1, date.end.day);

        const differenceInTime = end - start;
        const differenceInDays = differenceInTime / (1000 * 3600 * 24);

        setStartDate(start);
        setEndDate(end);
        setNumberOfDays(differenceInDays + 1);
        console.log(start, end);
        checkAvailability(start, end);
    };
    const checkAvailability = async (start, end) => {
        const token = localStorage.getItem('authToken');
        try {
            const dateRequest = {
                startDateTime: new Date(start),
                endDateTime: new Date(end)
            };
            const response = await axios.post(`http://localhost:8080/api/space/${id}/check-availability`, dateRequest, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                params: {
                    startDate: start.toISOString().substring(0, 10), // Ensure format is YYYY-MM-DD
                    endDate: end.toISOString().substring(0, 10)
                }
            });
            setIsAvailable(response.data);
            if (!response.data) {
                toast.error('Selected dates are not available. Please choose different dates.');
            }
        } catch (error) {
            console.error('Error checking availability:', error);
            toast.error('Failed to check availability');
        }
    };


    if (loading) {
        return (
            <div className="flex justify-center items-center h-screen">
                <ClipLoader size={35} color={"#123abc"} loading={loading}/>
            </div>
        );
    }

    if (!item) {
        return <div>Loading...</div>;
    }

    const handleOwnerClick = () => {
        navigate(`/message/${item.owner.userId}`);
    };

    const handleReportClick = () => {
        navigate(`/report/${id}`);
    };

    const handleRentClick = () => {
        if (numberOfDays === 0) {
            toast.error('Please select a valid date range.');
            return;
        }
        navigate(`/checkout/${id}/${startDate.toISOString()}/${endDate.toISOString()}`);
    };

    return (
        <div className="2xl:flex 2xl:mt-[10vh] mt-[20px] gap-[100px]">
            <ToastContainer/>
            <div key={item.id} className="2xl:ml-[13vw] flex flex-col items-center">
                <img src={item.image} alt={item.spaceName} className="rounded-xl 2xl:w-[45vw] w-[80vw]"/>
                <div className="mt-[2vh] flex flex-col text-center justify-center items-center gap-[1vh]">
                    <h2 className="text-xl font-semibold mb-[10px]">Owner Information</h2>
                    <div className="flex items-center justify-center gap-[10px] cursor-pointer"
                         onClick={handleOwnerClick}>
                        {item.owner.pictureUrl ? (
                            <img src={item.owner.pictureUrl} alt="Owner" className="w-[50px] h-[50px] rounded-full"/>
                        ) : (
                            <div
                                className="w-[50px] h-[50px] bg-gray-300 rounded-full flex items-center justify-center text-xl font-semibold">
                                {item.owner.firstName.charAt(0)}{item.owner.lastName.charAt(0)}
                            </div>
                        )}
                        <div>
                            <p className="text-lg font-medium">{item.owner.firstName} {item.owner.lastName}</p>
                            <p className="text-sm text-gray-500">{item.owner.email}</p>
                        </div>
                    </div>
                    <div className='flex gap-[20px] mx-[2vw] my-[20px]'>
                        <Button color="primary" type="submit"
                                className='font-semibold mt-[10px] py-[21px] w-[190px] text-[16px]'
                                onClick={handleOwnerClick}>Message</Button>
                        <Button color="danger" type="submit"
                                className="mt-[10px] w-[190px] text-[16px] py-[21px] font-semibold"
                                onClick={handleReportClick}>Report</Button>
                    </div>
                </div>
            </div>
            <div className="mt-[20px]">
                <h1 className="text-2xl 2xl:text-5xl font-semibold mb-[10px] text-center 2xl:w-[25vw]">{item.spaceName}</h1>
                <p className="text-2xl 2xl:text-3xl mb-[20px] text-center">{item.spacePrice}$ / month</p>
                <p className="2xl:w-[26vw] mx-[4vw] mb-[20px] 2xl:mx-0 text-left 2xl:text-center">{item.spaceDescription}</p>
                <div className="mx-[6vw] flex justify-center">
                    <RangeCalendar aria-label="Date (No Selection)" isDateUnavailable={isDateUnavailable}
                                   onChange={calendarChangeHandler}/>
                    {!isAvailable && (
                        <p className="text-red-500 text-center mt-2">
                            The selected dates are unavailable. Please select a different date range.
                        </p>
                    )}
                </div>
                <div className="flex my-[20px] gap-[20px] mx-[2vw]">
                    <Button
                        color={numberOfDays === 0 || !isAvailable ? "danger" : "primary"}
                        type="submit"
                        className="w-[200px] text-[16px] py-[21px] font-semibold"
                        onClick={handleRentClick}
                        disabled={numberOfDays === 0 || isOwner || !isAvailable}
                    >
                        Rent Now
                    </Button>
                    <Button color="primary" type="submit"
                            className="w-[200px] text-[16px] py-[21px] font-semibold bg-black"
                            onClick={() => navigate('reviews')}>
                        Reviews
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default SpacePage;
