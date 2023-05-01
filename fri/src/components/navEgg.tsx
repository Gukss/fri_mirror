import { useNavigate } from "react-router-dom";
import plus from "../assets/plus_btn.png"
import egg_nav from "../assets/egg_nav.png"
import yellow from "../assets/egg_yellow.png"

interface navType {
	isnav : boolean,
	setIsnav : React.Dispatch<React.SetStateAction<boolean>>;
}

function Nav({isnav, setIsnav} : navType) {

	const navigate = useNavigate();

	return (
		<>
			<img src={yellow} alt="nav" id="egg_yellow" style={isnav ? {bottom : "-150px"} : {bottom : "-30px"}} onClick={() => setIsnav(true)}/>
				<div className="nav_back" style={isnav ? {bottom : 0} : {bottom : "-100vh"}} onClick={() => setIsnav(false)}>
				<img src={plus} id="create" style={isnav ? {bottom : "190px"} : {bottom : "-190px"}} onClick={() => navigate("/add" + "?tab=cate")} />
				<div id="meeting" style={isnav ? {bottom : "130px"} : {bottom : "-130px"}} onClick={() => navigate("/main")}>미팅</div>
				<div id="chat" style={isnav ? {bottom : "55px"} : {bottom : "-55px"}} >채팅</div>
				<div id="my" style={isnav ? {bottom : "55px"} : {bottom : "-55px"}} onClick={() => navigate("/my")}>마잉</div>        
				<img src={egg_nav} alt="nav_egg" id="egg_nav" style={isnav ? {bottom : "-110px"} : {bottom : "-300px"}} onClick={() => setIsnav(false)}/>
			</div>  
		</>
	)
} export default Nav