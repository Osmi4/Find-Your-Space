import WordCard from '../WordCard';
import LoginForm from '../LoginForm';
import "../LoginPage.css"
import { useLocation } from 'react-router-dom';


const LoginPage = () => {   
    const location = useLocation();

    return (<div class ="forms">
        <LoginForm isLogin={true}/> 
        <WordCard />
        </div>)
  }

  export default LoginPage;