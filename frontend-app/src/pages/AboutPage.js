import {Card, CardFooter, Image, CardHeader, Button} from "@nextui-org/react";
import Aleks from "../images/Aleks.jpg";
import Hubert from "../images/Hubert.jpg";
import Osman from "../images/Osman.jpg";
import Wojtek from "../images/Wojtek.jpg";
import Jakub from "../images/Jakub.jpg";
import Linkedin from "../images/linkedin.webp";

const AboutPage = () => {
    return (
        <div>
            <h1 className="text-2xl lg:text-4xl font-semibold text-center mt-[35px]">About Us</h1>
            <p className="text-center text-xs sm:text-sm md:text-md lg:text-xl mt-[1vh] mx-[25vw] text-gray-500">
                FindYourSpace makes advertisements simple. 
                Forget about long meetings and lengthy contracts. 
                Need a place for your billboard? Company posters on bus stops? 
                FindYourSpace enables you to rent these spaces with a few simple clicks.</p>

            <hr className="border-blue-600 w-[85vw] mt-[1.5vh] border-1 mx-[7.5vw]"></hr>
            <h1 className="text-2xl lg:text-4xl font-semibold text-center mt-[1.5vh]">Meet Our Team</h1>
            <p className="text-center text-xs sm:text-sm md:text-md lg:text-xl mt-[1vh] mx-[26vw] text-gray-500">
                We are a young group of students from the Warsaw University of Technology, and each of us tries to bring benefits to the project with their unique set of skills. 
            </p>
            <div className="flex flex-col xl:flex-row justify-center mt-[2vh] gap-[2vw]">
                <div className="flex flex-col items-center gap-[10px]">
                <Card
                isFooterBlurred
                radius="none"
                className="border-2 border-blue-600 w-[200px] h-[240px] bg-black"
                >
                <CardHeader className="py-0 bg-black text-white">
                <h4 className="font-bold text-md">Aleksander Klepka</h4>
                </CardHeader>
                <Image
                alt="Aleksander Klepka"
                className="object-cover rounded-none"
                height={250}
                src={Aleks}
                width={250}
                />
                <CardFooter 
                className="justify-center before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[200px] shadow-small z-10">
                <p className="text-tiny text-white">Python Developer</p>
                </CardFooter>
                </Card>

                <Button isIconOnly className="rounded-full" onClick={() => window.open('https://www.linkedin.com/in/aleksanderklepka/')}  >
                <Image
                alt="linkedin"
                className="object-cover"
                src={Linkedin}
                />
                </Button>    
                </div>

                <div className="flex flex-col items-center gap-[10px]">
                <Card
                isFooterBlurred
                radius="none"
                className="border-2 border-blue-600 w-[200px] h-[240px] bg-black"
                >
                <CardHeader className="py-0 bg-black text-white">
                <h4 className="font-bold text-large">Hubert Jaczyński</h4>
                </CardHeader>
                <Image
                alt="Hubert Jaczyński"
                className="object-cover rounded-none"
                height={250}
                src={Hubert}
                width={250}
                />
                <CardFooter 
                className="justify-center before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[200px] shadow-small z-10">
                <p className="text-tiny text-white">Python Developer</p>
                </CardFooter>
                </Card>

                <Button isIconOnly className="rounded-full" onClick={()=> window.open('https://www.linkedin.com/in/hubert-jaczy%C5%84ski-83517619b/')}>
                <Image
                alt="linkedin"
                className="object-cover"
                src={Linkedin}
                />
                </Button> 
                </div>

                <div className="flex flex-col items-center gap-[10px]">
                <Card
                isFooterBlurred
                radius="none"
                className="border-2 border-blue-600 w-[200px] h-[240px] bg-black"
                >
                <CardHeader className="py-0 bg-black text-white">
                <h4 className="font-bold text-large">Osman Aliyev</h4>
                </CardHeader>
                <Image
                alt="Osman Aliyev"
                className="object-cover rounded-none"
                height={250}
                src={Osman}
                width={250}
                />
                <CardFooter 
                className="justify-center before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[200px] shadow-small z-10">
                <p className="text-tiny text-white">Frontend Developer</p>
                </CardFooter>
                </Card>

                <Button isIconOnly className="rounded-full" onClick={() => window.open('https://www.linkedin.com/in/osman-aliyev/')}>
                <Image
                alt="linkedin"
                className="object-cover"
                src={Linkedin}
                />
                </Button>    
                </div>

                <div className="flex flex-col items-center gap-[10px]">
                <Card
                isFooterBlurred
                radius="none"
                className="border-2 border-blue-600 w-[200px] h-[240px] bg-black"
                >
                <CardHeader className="py-0 bg-black text-white">
                <h4 className="font-bold text-large">Wojciech Basiński</h4>
                </CardHeader>
                <Image
                alt="Wojciech Basiński"
                className="object-cover rounded-none"
                height={250}
                src={Wojtek}
                width={250}
                />
                <CardFooter 
                className="justify-center before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[200px] shadow-small z-10">
                <p className="text-tiny text-white">Backend Developer</p>
                </CardFooter>
                </Card>

                <Button isIconOnly className="rounded-full" onClick={() => window.open('https://www.linkedin.com/in/wojciech-basinski/')}>
                <Image
                alt="linkedin"
                className="object-cover"
                src={Linkedin}
                />
                </Button> 
                </div>

                <div className="flex flex-col items-center gap-[10px]">
                <Card
                isFooterBlurred
                radius="none"
                className="border-2 border-blue-600 w-[200px] h-[240px] bg-black"
                >
                <CardHeader className="py-0 bg-black text-white">
                <h4 className="font-bold text-large">Jakub Oganowski</h4>
                </CardHeader>
                <Image
                alt="Jakub Oganowski"
                className="object-cover rounded-none"
                height={250}
                src={Jakub}
                width={250}
                />
                <CardFooter 
                className="justify-center before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[200px] shadow-small z-10">
                <p className="text-tiny text-white">Backend Developer</p>
                </CardFooter>
                </Card>
                
                <Button isIconOnly className="rounded-full" onClick={() => window.open('https://www.linkedin.com/in/jakub-oganowski-ab9434244/')}>
                <Image
                alt="linkedin"
                className="object-cover"
                src={Linkedin}
                />
                </Button> 
                </div>
            </div>  
            <hr className="border-blue-600 w-[85vw] mt-[25px] border-1 mx-[7.5vw]"></hr>
        </div>
    );
};

export default AboutPage;