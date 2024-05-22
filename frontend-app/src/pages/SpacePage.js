import { useParams } from "react-router-dom";
import spaces from "../spaces";
import { useState } from "react";
import {RangeCalendar, Button} from "@nextui-org/react";
import {today, getLocalTimeZone} from "@internationalized/date";
import { useNavigate } from "react-router-dom";

const SpacePage = () => {
    let now = today(getLocalTimeZone());
      
    let isDateUnavailable = (date) => date.compare(now) < 0
      
    let { id } = useParams();
    const item = spaces.find(item => item.id === parseInt(id));
    //const item = fetch(`http://localhost:8080/api/space/${id}`).then((response) => response.json());
    const [numberOfDays, setNumberOfDays] = useState(0);

    const calendarChangeHandler = (date) => {
    const startDate = new Date(date.start.year, date.start.month - 1, date.start.day);
    const endDate = new Date(date.end.year, date.end.month - 1, date.end.day);

    const differenceInTime = endDate - startDate;
    
    const differenceInDays = differenceInTime / (1000 * 3600 * 24);
    setNumberOfDays(differenceInDays + 1);
    };

    const navigate = useNavigate();

    return (
        <div className="flex mt-[100px] gap-[100px]">
            <div key={item.id} className="ml-[250px]">
                <img src={item.image} width={680} alt={item.title} className="rounded-xl"/>
            </div>
            <div className="mt-[20px]">
                <h1 className="text-5xl font-semibold mb-[10px]">{item.title}</h1>
                <p className="text-3xl mb-[20px]">{item.price}</p>
                <p className="w-[500px] mb-[20px]">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                <div className="flex gap-[10px] mx-[122px]">
                    <RangeCalendar aria-label="Date (No Selection)" isDateUnavailable={isDateUnavailable} onChange={calendarChangeHandler}/>
                </div>
                <div className="flex mt-[20px] gap-[20px] ml-[40px]">
                    <Button color={numberOfDays === 0 ? "danger" : "primary" } type="submit" className="w-[200px] text-[16px] py-[21px] font-semibold" onClick={()=> navigate(`checkout/${numberOfDays}`)} disabled={numberOfDays === 0}>
                        Rent Now
                    </Button>
                    <Button color="primary" type="submit" className="w-[200px] text-[16px] py-[21px] font-semibold bg-black" onClick={()=> navigate('reviews')}>
                        Reviews
                    </Button>
                </div>
                
            </div>
        </div>
        
    );
};

export default SpacePage;