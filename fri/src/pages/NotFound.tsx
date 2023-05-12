import Not from "../assets/notfound.png"
import Num from "../assets/404.png"
import Logo from "../assets/small_logo.png"
import { useNavigate } from "react-router-dom"

export default function NotFound(){
    const navigate = useNavigate();
    return(
        <div className="notfound">
            <img src={Logo} alt="logo" id="not_logo"  onClick={()=> navigate("/main")}/>
            <div className="main">
                <img src={Num} alt="404" id="not_404"/>
                <img src={Not} alt="not" id="not_duck"  onClick={()=> navigate("/main")}/>
            </div>
        </div>
    )

}