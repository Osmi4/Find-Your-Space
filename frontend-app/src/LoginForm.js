const LoginForm=({isLogin})=>{
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
  {!isLogin && <button type="submit" class="btninv">Register</button>}
  </form>
  </div>
    )
}

export default LoginForm;