import { useParams } from "react-router-dom";
import spaces from "../spaces";
import { Link } from "react-router-dom";
import { useState } from "react";
import {DateInput} from "@nextui-org/date-input";
import {CalendarDate} from "@internationalized/date";

const SpacePage = () => {
    let { id } = useParams();
    const item = spaces.find(item => item.id === parseInt(id));
    const [numberOfDays, setNumberOfDays] = useState(1);
    return (
        <div className="flex mt-[100px] gap-[100px]">
            <div key={item.id} className="ml-[250px]">
                <img src={item.image} width={680} alt={item.title} className="rounded-xl"/>
            </div>
            <div className="mt-[20px]">
                <h1 className="text-5xl font-semibold mb-[10px]">{item.title}</h1>
                <p className="text-3xl mb-[20px]">{item.price}</p>
                <p className="w-[500px] mb-[20px]">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                <div className="flex gap-[10px]">
                    <div className="flex bg-zinc-100 rounded-2xl gap-[50px] px-[10px] items-center border-black border-2 hover:border-sky-900">
                        <button className="text-neutral-500 text-xl" onClick={()=>setNumberOfDays((prev)=>Math.max(prev - 1, 1))}>-</button>
                        <p className="text-xl">{numberOfDays}</p>
                        <button className="text-neutral-500 text-xl" onClick={()=>setNumberOfDays((prev)=>prev + 1)}>+</button>
                    </div>
                    <div className="flex gap-[50px] mx-[20px] flex-wrap md:flex-nowrap border-black border-2 rounded-2xl hover:border-sky-900">
                        <DateInput placeholderValue={new CalendarDate(1995, 11, 6)} className="max-w-sm" size="lg"/>
                    </div>
                </div>
                <div className="flex gap-[70px]">
                    <p className="text-neutral-400 text-sm my-[15px]">Number of days</p>
                    <p className="text-neutral-400 text-sm my-[15px]">Starting Date</p>
                </div>
                <div className="flex gap-[10px]">
                    <Link className="bg-black text-white px-[70px] py-[12px] text-sm hover:bg-gray-700 rounded-2xl" to='reviews'>Reviews</Link>
                    <Link className="bg-sky-700 text-white px-[40px] py-[12px] text-sm hover:bg-sky-500 rounded-2xl" to={`checkout/${numberOfDays}`}>Rent Now</Link>
                </div>
                
            </div>
        </div>
        
    );
};

export default SpacePage;