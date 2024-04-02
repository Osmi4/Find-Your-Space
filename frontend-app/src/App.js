import {NextUIProvider} from "@nextui-org/react";
import { useNavigate} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import { Route,Routes } from 'react-router-dom';
import Nav from "./Nav";
import HomePage from "./pages/HomePage";

function App() {
  const navigate = useNavigate();

  return (
    <NextUIProvider navigate={navigate}>
      <Nav/>
        <div>
          <Routes>    
            <Route path="/" element={<HomePage/>}/>    
            <Route path="/login"  element={<LoginPage/>} />
            <Route path="/signup"  element={<LoginPage/>} />
          </Routes>
        </div>
        {/* put the footer */}
        <></> 
    </NextUIProvider>
  );
}

export default App;
