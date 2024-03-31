import WordCard from './WordCard';
import LoginForm from './LoginForm';
import {useState} from 'react';

function App() {
  const [isRegister,setIsRegister]=useState(false);
  const onRegister=()=>{
    setIsRegister(true);
  }
  const onLogin=()=>{
    setIsRegister(false);
  }

  return (
  <div class ="forms">
    {!isRegister && 
    <><LoginForm isLogin={true}/> 
    <WordCard onClick={onRegister} isLogin={true}/></>}
    {isRegister && 
    <><WordCard onClick={onLogin} isLogin={false}/>
    <LoginForm isLogin={false}/></>}
  </div>
  );
}

export default App;
