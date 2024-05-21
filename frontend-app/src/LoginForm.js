import {Input} from "@nextui-org/react";
import {MailIcon} from './MailIcon.jsx';
import {LockIcon} from './LockIcon.jsx';
import {Checkbox} from "@nextui-org/react";
import {Link} from "@nextui-org/react";
import {Button} from "@nextui-org/react";

const LoginForm=()=>{
  return (
  <div class="wrapper">
  <form action="">
  <h1>Login</h1>

  {/* <div class = "input-box">
    <input type="text" placeholder="Username" required/>
    <i class='bx bxs-user'/>
  </div>

  <div class = "input-box">
    <input type="password" placeholder="Password" required/>
    <i class='bx bxs-lock-alt'/>
  </div> */}
  <div className="flex flex-col gap-4 mt-[20px] mb-[5px]">
  <Input
    label="Email"
    placeholder="Enter your email"
    variant="bordered"
    endContent={
      <MailIcon className="text-2xl text-default-200 pointer-events-none flex-shrink-0" />
    }
  />
  <Input
    label="Password"
    placeholder="Enter your password"
    variant="bordered"
    endContent={
      <LockIcon className="text-2xl text-default-200 pointer-events-none flex-shrink-0" />
    }
  />
  </div>
  
  <div className="flex py-2 px-1 justify-between mb-[5px]">
  <Checkbox
    classNames={{
      label: "text-small",
    }}
  >
    Remember me
  </Checkbox>
  <Link color="primary" href="#" size="sm">
    Forgot password?
  </Link>
</div>
<Button color="primary" variant="bordered" type="submit" className="bg-black w-[100%] h-[45px] text-[16px] font-semibold">
  Log In
</Button>
  </form>
  </div>
    )
}

export default LoginForm;