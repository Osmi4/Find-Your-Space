import { credentials } from "../LoginForm";

const RegisterPage = () => { 
    return (
        <div className="mt-[150px]">   
        <form className="flex flex-col gap-[10px] items-center border-2 mx-[750px] py-[20px] border-black rounded-3xl bg-slate-100">
            <h1 className="text-center mb-[30px] text-3xl font-semibold underline">New user</h1>
            <input type="text" placeholder="Username" defaultValue={credentials.username} className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
            <input type="text" placeholder="First Name" className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
            <input type="text" placeholder="Last Name" className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
            <input type="email" placeholder="Email" className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
            <input type="text" placeholder="Bank Account No." className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
            <input type="password" placeholder="Password" defaultValue={credentials.password} className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
            <input type="password" placeholder="Confirm Password" defaultValue={credentials.password} className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
            <div>
                <input type="checkbox" />
                <label> I am an owner</label>
            </div>
            <button className="mt-[30px] rounded-3xl bg-black text-white px-[50px] py-[8px] font-semibold border-2 border-white">Register</button>
        </form>
        </div>
    );
}

export default RegisterPage;