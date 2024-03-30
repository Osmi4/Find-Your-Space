function LoginForm(){
    return (
    <div class="wrapper">
        <form action="">
            <h1>Login</h1>

    <div class = "input-box">
      <input type="text" placeholder="Username" required/>
      <i class='bx bxs-user'/>
    </div>

    <div class = "input-box">
      <input type="password" placeholder="Password" required/>
      <i class='bx bxs-lock-alt'/>
    </div>

    <div class = "remember-forgot">
      <label><input type = "checkbox"/>Remember me</label>
      <a href="#">Forgot password?</a>
    </div>

    <button type="submit" class="btn">Login</button>

        </form>
    </div>
    )
}

export default LoginForm;