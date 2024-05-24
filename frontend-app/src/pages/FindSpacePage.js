import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Checkbox } from "@nextui-org/react";
import axios from "axios";

const FindSpacePage = () => {
    const navigate = useNavigate();

    const [spaces, setSpaces] = useState([]);
    const [filter] = useState({
        categories: ["Digital Billboards", "Print Media Spaces", "Transit Advertising", "Shopping Mall Spaces", "Online Platforms"],
        countries: ["Poland", "USA", "Japan", "Germany", "France"],
        cities: ["Poznan", "Wroclaw", "Krakow", "Warsaw", "Gdansk", "Lodz"]
    });

    const [selectedFilters, setSelectedFilters] = useState({
        categories: [],
        countries: [],
        cities: []
    });

    useEffect(() => {
        const fetchSpaces = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/space/all");
                setSpaces(response.data.content);
            } catch (error) {
                console.error("Error fetching spaces:", error);
            }
        };

        fetchSpaces();
    }, []);

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

    const filteredItems = spaces.filter(item => {
        const categoryMatch = selectedFilters.categories.length === 0 || selectedFilters.categories.includes(item.category);
        const countryMatch = selectedFilters.countries.length === 0 || selectedFilters.countries.includes(item.country);
        const cityMatch = selectedFilters.cities.length === 0 || selectedFilters.cities.includes(item.city);
        return categoryMatch && countryMatch && cityMatch;
    });

    const clearSelectedFilters = () => {
        setSelectedFilters({ categories: [], countries: [], cities: [] });
        navigate(0);
    };

    const openSpacePage = (id) => {
        navigate(`/space/${id}`);
    };

    return (
        <div className="flex mt-[30px] gap-[100px] ml-[100px]">
            <div className="flex-col w-[250px] ml-[40px] mr-[20px] mt-[40px]">
                <div className="flex gap-[20px]">
                    <h1 className="text-xl font-semibold">Filters</h1>
                    <button onClick={clearSelectedFilters} className="text-xs text-gray-400 underline hover:text-gray-700">Clear Filters</button>
                </div>
                <p className="text-sm font-semibold my-[10px]">Categories</p>
                <div className="flex flex-col">
                    {filter.categories.map(category => (
                        <Checkbox
                            key={category}
                            classNames={{
                                label: "text-small",
                            }}
                            className='mt-[1px]'
                            id={category}
                            isSelected={selectedFilters.categories.includes(category)}
                            onValueChange={() => handleCheckboxChange('categories', category)}
                        >
                            {category}
                        </Checkbox>
                    ))}
                </div>

                <p className="text-sm font-semibold my-[10px]">Countries</p>
                <div className="flex flex-col">
                    {filter.countries.map(country => (
                        <Checkbox
                            key={country}
                            classNames={{
                                label: "text-small",
                            }}
                            className='mt-[1px]'
                            id={country}
                            isSelected={selectedFilters.countries.includes(country)}
                            onValueChange={() => handleCheckboxChange('countries', country)}
                        >
                            {country}
                        </Checkbox>
                    ))}
                </div>

                <p className="text-sm font-semibold my-[10px]">Cities</p>
                <div className="flex flex-col">
                    {filter.cities.map(city => (
                        <Checkbox
                            key={city}
                            classNames={{
                                label: "text-small",
                            }}
                            className='mt-[1px]'
                            id={city}
                            isSelected={selectedFilters.cities.includes(city)}
                            onValueChange={() => handleCheckboxChange('cities', city)}
                        >
                            {city}
                        </Checkbox>
                    ))}
                </div>
            </div>

            <div className="flex w-full mt-[50px] gap-y-[20px] gap-x-[15px] flex-wrap">
                {filteredItems.map(item => (
                    <div key={item.id} onClick={() => openSpacePage(item.id)} >
                        <img src={item.image} width={300} alt={item.title} className="rounded-lg hover:scale-105 ease-out duration-200" />
                        <h1 className="font-bold text-sm mt-[10px] mb-[3px] hover:underline hover:cursor-pointer">{item.title}</h1>
                        <h2 className="text-xs">{item.price}</h2>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default FindSpacePage;
