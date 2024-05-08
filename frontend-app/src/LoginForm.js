import { useNavigate } from "react-router-dom";

export const credentials = {username:"",password:""};

const LoginForm=({isLogin})=>{

  const navigate = useNavigate();
  const signupHandler=()=>{
    navigate('/signup/full');
    credentials.username = document.querySelector("input[type='text']").value;
    credentials.password = document.querySelector("input[type='password']").value;
  }
  
  return (
  <div class="wrapper">
  <form action="">
    {isLogin && <h1>Login</h1>}
    {!isLogin && <h1>Register</h1>}
  <div class = "input-box">
    <input type="text" placeholder="Username" required/>
    <i class='bx bxs-user'/>
  </div>

  <div class = "input-box">
    <input type="password" placeholder="Password" required/>
    <i class='bx bxs-lock-alt'/>
  </div>
  {
    !isLogin && <div class = "input-box">
    <input type="password" placeholder="Confirm Password" required/>
    <i class='bx bxs-lock-alt'/>
    </div>
  }
  {isLogin && <div class = "remember-forgot">
    <label><input type = "checkbox"/>Remember me</label>
    <a href="#">Forgot password?</a>
  </div>}

  {isLogin && <button type="submit" class="btn">Login</button>}
  {!isLogin && <button type="submit" class="btninv" onClick={signupHandler}>Register</button>}
  </form>
  </div>
    )
}

export default LoginForm;