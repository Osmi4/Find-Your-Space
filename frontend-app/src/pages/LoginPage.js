import WordCard from '../WordCard';
import LoginForm from '../LoginForm';
import {useEffect, useState} from 'react';
import "../LoginPage.css"
import { useLocation } from 'react-router-dom';

const LoginPage = () => {   
    const location = useLocation();
    const [isRegister,setIsRegister]=useState(false);
    const onRegister=()=>{
        setIsRegister(true);
    }
    const onLogin=()=>{
        setIsRegister(false);
    }

    useEffect(()=>{
        if(location.pathname==="/signup"){
            setIsRegister(true);
        }
    
        if(location.pathname==="/login"){
            setIsRegister(false);
        }
    },[location.pathname]);

    return (<div class ="forms">
    {!isRegister && 
        <>
        <LoginForm isLogin={true}/> 
        <WordCard onClick={onRegister} isLogin={true}/>
        </>}
    {isRegister && 
        <>
        <WordCard onClick={onLogin} isLogin={false}/>
        <LoginForm isLogin={false}/></>}
        </div>);
  }

  export default LoginPage;