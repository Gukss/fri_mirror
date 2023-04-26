import { Navigate, useNavigate } from "react-router-dom";

interface navType {
	isnav : boolean,
	setIsnav : React.Dispatch<React.SetStateAction<boolean>>;
}

function Nav({isnav, setIsnav} : navType) {

	const navigate = useNavigate();

	return (
		<>
			<img src="/assets/egg_yellow.png" alt="nav" id="egg_yellow" style={isnav ? {bottom : "-150px"} : {bottom : "-30px"}} onClick={() => setIsnav(true)}/>
				<div className="nav_back" style={isnav ? {bottom : 0} : {bottom : "-100vh"}} onClick={() => setIsnav(false)}>
				<img src="/assets/plus_btn.png" id="create" style={isnav ? {bottom : "190px"} : {bottom : "-190px"}} onClick={() => navigate("/add" + "?tab=cate")} />
				<div id="meeting" style={isnav ? {bottom : "130px"} : {bottom : "-130px"}} onClick={() => navigate('/main')}>미팅</div>
				<div id="chat" style={isnav ? {bottom : "55px"} : {bottom : "-55px"}} >채팅</div>
				<div id="my" style={isnav ? {bottom : "55px"} : {bottom : "-55px"}} onClick={() => navigate("/my")}>마잉</div>        
				<img src="/assets/egg_nav.png" alt="nav_egg" id="egg_nav" style={isnav ? {bottom : "-110px"} : {bottom : "-300px"}} onClick={() => setIsnav(false)}/>
			</div>  
		</>
	)
} export default Nav