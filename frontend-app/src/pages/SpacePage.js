import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { RangeCalendar, Button } from "@nextui-org/react";
import { today, getLocalTimeZone } from "@internationalized/date";
import axios from "axios";

const SpacePage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [item, setItem] = useState(null);
    const [numberOfDays, setNumberOfDays] = useState(0);
    const now = today(getLocalTimeZone());

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

                spaceData.image = imageUrl;
                setItem(spaceData);
            } catch (error) {
                console.error("Error fetching space data", error);
            }
        };

        fetchData();
    }, [id]);

    const isDateUnavailable = (date) => date.compare(now) < 0;

    const calendarChangeHandler = (date) => {
        const startDate = new Date(date.start.year, date.start.month - 1, date.start.day);
        const endDate = new Date(date.end.year, date.end.month - 1, date.end.day);

        const differenceInTime = endDate - startDate;

        const differenceInDays = differenceInTime / (1000 * 3600 * 24);
        setNumberOfDays(differenceInDays + 1);
    };

    if (!item) {
        return <div>Loading...</div>;
    }

    return (
        <div className="2xl:flex 2xl:mt-[10vh] mt-[20px] gap-[100px]">
            <div key={item.id} className="2xl:ml-[13vw] ml-[25vw]">
                <img src={item.image} alt={item.spaceName} className="rounded-xl w-[50vw] 2xl:w-[34vw]"/>
            </div>
            <div className="mt-[20px]">
                <h1 className="text-2xl 2xl:text-5xl font-semibold mb-[10px] text-center 2xl:w-[25vw]">{item.spaceName}</h1>
                <p className="text-2xl 2xl:text-3xl mb-[20px] text-center">{item.spacePrice}$ / month</p>
                <p className="2xl:w-[26vw] mx-[4vw] mb-[20px] 2xl:mx-0 text-left 2xl:text-center">{item.spaceDescription}</p>
                <div className="mx-[6vw] flex justify-center">
                    <RangeCalendar aria-label="Date (No Selection)" isDateUnavailable={isDateUnavailable}
                                   onChange={calendarChangeHandler}/>
                </div>
                <div className="flex mt-[20px] gap-[20px] mx-[2vw] mb-[20px]">
                    <Button color={numberOfDays === 0 ? "danger" : "primary"} type="submit"
                            className="w-[200px] text-[16px] py-[21px] font-semibold"
                            onClick={() => navigate(`checkout/${numberOfDays}`)} disabled={numberOfDays === 0}>
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
