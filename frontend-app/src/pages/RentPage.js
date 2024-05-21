import { useState } from 'react';   
import {Checkbox, Input, Textarea, Button} from "@nextui-org/react";

const RentPage = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        address: '',
        apartmentSuite: '',
        city: '',
        country: '',
        zipCode: '',
        optional: '',
        rentCity: '',
        rentZipCode: '',
        rentAddress: '',
        price: '',
        duration: '',
        additionalInfo: ''
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
        <div className="flex mt-[150px] ml-[350px]">
        <div className="">
            <h1 className="text-4xl font-semibold mb-[70px]">Rent your space</h1>
        <div className='flex gap-x-[495px]'>
            <h2 className="text-xl mb-[20px]">Personal Information</h2>
            <h2 className="text-xl mb-[20px]">Information about advertised space</h2>
        </div>
      <form onSubmit={handleSubmit} className="w-[450px] flex gap-x-[250px]">
        <div className="flex flex-col gap-y-[10px]">
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
            value={formData.firstName}
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
            label="ZIP code"
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
            className='mt-[5px]'
        >
            Save Contact Information
        </Checkbox>
        </div>
        <div className="flex flex-col gap-y-[10px]">
            <div className="row flex gap-[10px]">
                <Input
                name="rentCity"
                label="City"
                placeholder="Enter city of the space"
                variant="bordered"
                value={formData.rentCity}
                onChange={handleChange}
                className="w-[220px]"
                />
                <Input
                name="rentZipCode"
                label="ZIP Code"
                placeholder="Enter ZIP code of the space"
                variant="bordered"
                value={formData.rentZipCode}
                onChange={handleChange}
                className="w-[220px]"   
                />
            </div>
            <div className="row">
                <Input
                name="rentAddress"  
                label="Address"
                placeholder="Enter address of the space"
                variant='bordered'
                value={formData.rentAddress}
                onChange={handleChange}
                className="w-full"
                />
            </div>
            <div className="row">
                <Input
                name="price"  
                label="Price (per month) in $"
                placeholder="Enter price of the space"
                variant='bordered'
                value={formData.rentPrice}
                onChange={handleChange}
                className="w-full"
                />
            </div>
            <div className="row">
                <Input
                name="duration"  
                label="Active Rent Duration (months)"
                placeholder="Enter active duration of the rent"
                variant='bordered'
                value={formData.rentDuration}
                onChange={handleChange}
                className="w-full"
                />
            </div>
            <div className="row">
                <Textarea
                name="additionalInfo"  
                label="Additional Information"
                placeholder="Enter additional information about the space"
                variant='bordered'
                value={formData.rentAdditionalInfo}
                onChange={handleChange}
                className="w-full"
                />
            </div>
            <Button color="primary" variant='bordered' type="submit" className="w-[100%] h-[45px] text-[14px] font-semibold bg-black">
                Add pictures of the advertised space
            </Button>
        </div>
      </form>
    </div>
    </div>
    )
}

export default RentPage;