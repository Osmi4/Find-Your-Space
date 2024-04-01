const WordCard = ({onClick,isLogin}) =>{
    return (
        <div class={isLogin?"wordcard":"wordcardinv"}>
            {isLogin && 
            <>
            <h1>Welcome to SpaceSearch!</h1>
            <div class = "signup">
                <p>If you are new here press to <button onClick={onClick}>Register</button></p>
            </div>
            </>}
            {!isLogin &&<>
            <h1>We are happy to see you!</h1>
            <div class = "signup">
                <p>If you already have an account press to <button onClick={onClick}>Login</button></p>
            </div>
            </>}
        </div>
    )   
}

export default WordCard;