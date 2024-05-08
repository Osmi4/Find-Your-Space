import { useState } from "react";
import { useParams } from "react-router-dom";
import spaces from "../spaces";

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
          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            value={formData.firstName}
            onChange={handleChange}
            className="w-[220px] border-black border-2 py-[8px] pl-[15px] rounded-2xl"
          />
          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            value={formData.lastName}
            onChange={handleChange}
            className="w-[220px] border-black border-2 py-[8px] pl-[15px] rounded-2xl"
          />
        </div>

        <div className="row">
          <input
            type="text"
            name="address"
            placeholder="Address"
            value={formData.address}
            onChange={handleChange}
            className="border-black border-2 py-[8px] pl-[15px] w-full rounded-2xl"
          />
        </div>

        <div className="row">
          <input
            type="text"
            name="apartmentSuite"
            placeholder="Apartment/Suite"
            value={formData.apartmentSuite}
            onChange={handleChange}
            className="border-black border-2 py-[8px] pl-[15px] w-full rounded-2xl"
          />
        </div>

        <div className="row flex gap-[15px]">
          <input
            type="text"
            name="country"
            placeholder="Country"
            value={formData.country}
            onChange={handleChange}
            className="w-[140px] border-black border-2 py-[8px] pl-[15px] rounded-2xl"
          />
          <input
            type="text"
            name="zipCode"
            placeholder="Zipcode"
            value={formData.zipCode}
            onChange={handleChange}
            className="w-[140px] border-black border-2 py-[8px] pl-[15px] rounded-2xl"
          />
          <input
            type="text"
            name="city"
            placeholder="City"
            value={formData.city}
            onChange={handleChange}
            className="w-[140px] border-black border-2 py-[8px] pl-[15px] rounded-2xl"
          />
        </div>

        <div className="row">
          <input
            type="text"
            name="optional"
            placeholder="Optional"
            value={formData.optional}
            onChange={handleChange}
            className="border-black border-2 py-[8px] pl-[15px] w-full rounded-2xl"
          />
        </div>
        <div className="flex gap-[10px] my-[15px]">
            <input
                type="checkbox"
                checked={false}
            />
            <p className="text-default-500">Save contact information</p>
        </div>
        <div className="row bg-black text-center rounded-2xl">
          <button type="submit" className="text-white py-[10px] ">Continue to Payment</button>
        </div>
      </form>
      
    </div>
        <div className="mt-[100px] ml-[500px] text-2xl">
            <p>Chosen Location</p>
            <div className="flex">
                <img src={item.image} width={150} alt={item.title} className="rounded-xl mt-[30px]"/>
                <div className="font-semibold mt-[15px] ml-[10px]">
                    <p className="text-2xl mt-[20px]">{item.title}</p>
                    <p className="text-xs mt-[10px] font-medium">Duration: {days} days</p>
                    <p className="text-2xl mt-[10px]">${Math.ceil(price / 30 * parseInt(days))}</p>
                    <button className="ml-[200px] mt-[30px] text-xs font-medium underline">Remove</button>
                </div>
            </div>
            <input
            type="text"
            name="couponCode"
            placeholder="Enter coupon code here"
            value={formData.couponCode}
            onChange={handleChange}
            className="border-black border-2 py-[8px] pl-[15px] text-lg mt-[200px] w-[450px] rounded-2xl"
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