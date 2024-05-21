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
        <div className="flex mt-[70px] ml-[200px]">
        <div className="">
            <h1 className="text-4xl font-semibold mb-[30px]">Checkout</h1>
            <div className="flex items-center mb-[50px] ml-[100px]">
                <p className="font-semibold text-lg">Adress</p>
                <hr className="border-black w-[70px] mx-[20px]"></hr>
                <p className="text-lg">Payment</p>
            </div>
        <h2 className="text-xl mb-[20px]">Shipping Information</h2>
      <form onSubmit={handleSubmit} className="w-[450px] flex flex-col gap-y-[10px]">
      <div className="row flex gap-[10px]">
          <Input
            autoFocus
            name="firstName"
            label="First Name"
            placeholder="Enter your first name"
            variant="bordered"
            value={formData.firstName}
            onChange={handleChange}
            className="w-[220px]"
            />
            <Input
            name="lastName"
            label="Last Name"
            placeholder="Enter your last name"
            variant="bordered"
            value={formData.lastName}
            onChange={handleChange}
            className="w-[220px]"   
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
            className="w-[140px]"
          />
          <Input
            name="zipCode"
            label="Zipcode"
            variant='bordered'
            value={formData.zipCode}
            onChange={handleChange}
            className="w-[140px]"
          />
          <Input
            name="city"
            label="City"
            variant='bordered'
            value={formData.city}
            onChange={handleChange}
            className="w-[140px]"
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
        <div className="mt-[100px] ml-[500px] text-2xl">
            <p>Chosen Location</p>
            <hr className="border-black w-[450px] mt-[15px] border-1"></hr>
            <div className="flex">
                <img src={item.image} width={150} alt={item.title} className="rounded-xl mt-[30px]"/>
                <div className="font-semibold mt-[15px] ml-[10px]">
                    <p className="text-2xl mt-[20px]">{item.title}</p>
                    <p className="text-xs mt-[10px] font-medium">Duration: {days} days</p>
                    <p className="text-2xl mt-[10px]">${Math.ceil(price / 30 * parseInt(days))}</p>
                    <button className="ml-[200px] mt-[30px] text-xs font-medium underline">Remove</button>
                </div>
            </div>
            <Input
            name="couponCode"
            label="Coupon Code"
            placeholder="Enter coupon code here"
            variant='bordered'
            value={formData.couponCode}
            onChange={handleChange}
            className="mt-[200px] w-[450px]"
          />
          <hr className="border-black w-[450px] mt-[15px] border-1"></hr>
        <div className="flex gap-[385px]">
            <p className="text-sm font-medium">Total</p>
            <p className="text-sm font-medium">${Math.ceil(price / 30 * parseInt(days))}</p>
        </div>
        </div>
    </div>
    )
}

export default CheckoutPage;