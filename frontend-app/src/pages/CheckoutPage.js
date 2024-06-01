import { useState } from "react";
import { useParams } from "react-router-dom";
import spaces from "../spaces";
import {Checkbox, Input, Button} from "@nextui-org/react";

const CheckoutPage = () =>{
    let { id, days } = useParams();
    const item = spaces.find(item => item.id === parseInt(id));
    const regex = /\d+/;

    const price = item.price.match(regex)[0]; 

    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        address: '',
        apartmentSuite: '',
        city: '',
        country: '',
        zipCode: '',
        optional: '',
        couponCode: '',
      });

      const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
      };

      const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Form submitted:', formData);
      };

    return (
      <div className="flex flex-col xl:flex-row xl:mt-[70px] xl:ml-[200px] mt-[4vh]">
        <div className="">
            <h1 className="text-2xl xl:text-4xl font-semibold xl:mb-[30px] mb-[1vh] ml-[1vw] lg:text-left text-center">Checkout</h1>
            <div className="flex items-center xl:mb-[50px] mb-[2vh] lg:ml-[100px] justify-center xl:justify-normal">
                <p className="font-semibold text-lg">Adress</p>
                <hr className="border-black w-[70px] mx-[20px]"></hr>
                <p className="text-lg">Payment</p>
            </div>
        <h2 className="text-lg xl:mb-[20px] ml-[1vw] text-gray-700 font-bold text-center xl:text-left mb-[2vh]">Shipping Information</h2>
      <form onSubmit={handleSubmit} className="xl:w-[450px] flex flex-col gap-y-[10px] mx-[1vw]">
      <div className="row flex gap-[10px]">
          <Input
            autoFocus
            name="firstName"
            label="First Name"
            placeholder="Enter your first name" 
            variant="bordered"
            value={formData.firstName}
            onChange={handleChange}
            className="w-[1/2]"
            />
            <Input
            name="lastName"
            label="Last Name"
            placeholder="Enter your last name"
            variant="bordered"
            value={formData.lastName}
            onChange={handleChange}
            className="w-[1/2]"   
            />
        </div>

        <div className="row">
          <Input
            name="address"  
            label="Address"
            placeholder="Enter your address"
            variant='bordered'
            value={formData.address}
            onChange={handleChange}
            className="w-full"
          />
        </div>

        <div className="row">
          <Input
            name="apartmentSuite"
            label="Apartment/Suite"
            placeholder="Enter your apartment/suite"
            variant='bordered'
            value={formData.apartmentSuite}
            onChange={handleChange}
            className="w-full"
          />
        </div>

        <div className="row flex gap-[15px]">
          <Input
            name="country"
            label="Country"
            variant='bordered'
            value={formData.country}
            onChange={handleChange}
            className="w-[1/3]"
          />
          <Input
            name="zipCode"
            label="Zipcode"
            variant='bordered'
            value={formData.zipCode}
            onChange={handleChange}
            className="w-[1/3]"
          />
          <Input
            name="city"
            label="City"
            variant='bordered'
            value={formData.city}
            onChange={handleChange}
            className="w-[1/3]"
          />
        </div>

        <div className="row">
          <Input
            name="optional"
            label="Optional"
            placeholder="Enter additional information (optional)"
            variant='bordered'
            value={formData.optional}
            onChange={handleChange}
            className="w-full"
          />
        </div>
        <Checkbox
            classNames={{
            label: "text-small",
            }}
            className='my-[5px]'
        >
            Save Contact Information
        </Checkbox>
        <Button color="primary" variant='bordered' type="submit" className="w-[100%] text-[16px] py-[20px] font-semibold bg-black">
          Continue to Payment
        </Button>
      </form>
      
    </div>
        <div className="xl:mt-[100px] xl:ml-[500px] mt-[4vh] xl:text-2xl text-xl text-center xl:text-left ">
            <p className="text-gray-700 font-bold">Chosen Location</p>
            <hr className="border-black xl:w-[450px] w-[90vw] mt-[15px] border-1 mx-[5vw] xl:mx-0"></hr>
            <div className="flex flex-col xl:flex-row items-center xl:items-normal">
                <img src={item.image} alt={item.title} className="rounded-xl mt-[30px] xl:w-[150px] xl:h-[150px] w-[50vw] h-[50vw]"/>
                <div className="font-semibold mt-[15px] ml-[10px]">
                    <p className="text-2xl mt-[20px]">{item.title}</p>
                    <p className="text-xs mt-[10px] font-medium">Duration: {days} days</p>
                    <p className="text-2xl mt-[10px]">${Math.ceil(price / 30 * parseInt(days))}</p>
                    <button className="xl:ml-[200px] xl:mt-[30px] text-xs font-medium underline">Remove</button>
                </div>
            </div>
            <Input
            name="couponCode"
            label="Coupon Code"
            placeholder="Enter coupon code here"
            variant='bordered'
            value={formData.couponCode}
            onChange={handleChange}
            className="xl:mt-[200px] xl:w-[450px] mt-[5vh] mx-[1vw] xl:mx-0"
          />
          <hr className="border-black xl:w-[450px] w-[95vw] mx-[2.5vw] xl:mx-0 mt-[15px] border-1"></hr>
        <div className="flex xl:gap-[385px] mb-[2vh]">
            <p className="text-sm font-medium ml-[2.5vw] xl:ml-0">Total</p>
            <p className="text-sm font-medium ml-[77.5vw] xl:ml-0">${Math.ceil(price / 30 * parseInt(days))}</p>
        </div>
        </div>
    </div>
    )
}

export default CheckoutPage;