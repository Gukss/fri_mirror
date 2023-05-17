import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import plus from "../assets/plus_btn.png"
import egg_nav from "../assets/egg_nav.png"
import yellow from "../assets/egg_yellow.png"
import Com from "../assets/community.png"
import Met from  "../assets/mymeet.png"
import Gam from "../assets/mygame.png"
import Pro from "../assets/profiletxt.png"

interface navType {
	isnav : boolean,
	setIsnav : React.Dispatch<React.SetStateAction<boolean>>;
}

function Nav({isnav, setIsnav} : navType) {
	const navigate = useNavigate();
	const roomId = useSelector((state: RootState) => {
		return state.strr.roomId;
	});
	const gameRoomId = useSelector((state: RootState) => {
		return state.strr.gameRoomId;
	});
	const eggCnt = useSelector((state: RootState) => {
		return state.strr.heart;
	})

	function checkEgg(){
		if(roomId !== "참여한 방이 없습니다." && gameRoomId !== "참여한 방이 없습니다." ){
			alert("나는 게임도 미팅도 참여중")
			return
		}
		else navigate("/add?tab=cate")
	}

	function goChat() {
		if(roomId === "참여한 방이 없습니다."){
			alert("현재 참여중인 방이 없습니다.")
			return;
		}
		else navigate(`/chatting/${roomId}?isuser=true`);
	};

	function goGame() {
		if(gameRoomId === "참여한 방이 없습니다."){
			alert("현재 참여중인 방이 없습니다.")
			return;
		}
		else navigate(`/wait/${gameRoomId}`);
	};

	return (
		<div className="nav-background">
			<img src={yellow} alt="nav" id="egg_yellow" style={isnav ? {bottom : "-150px"} : {bottom : "-30px"}} onClick={() => setIsnav(true)}/>
				<div className="nav_back" style={isnav ? {bottom : 0} : {bottom : "-100vh"}} onClick={() => setIsnav(false)}>
				<img src={plus} id="create" style={isnav ? {bottom : "320px"} : {bottom : "-190px"}} onClick={()=>checkEgg()} />
				<div id="home" style={isnav ? {bottom : "270px"} : {bottom : "-130px"}} onClick={() => navigate("/main")}>홈</div>
				<div id="meet" style={isnav ? {bottom : "255px"} : {bottom : "-55px"}} onClick={goChat}><img src={Met} alt="com" /></div>
				<div id="commu" style={isnav ? {bottom : "90px"} : {bottom : "-90px"}}><img src={Com} alt="com" /></div>
				<div id="my" style={isnav ? {bottom : "100px"} : {bottom : "-65px"}} onClick={() => navigate("/my")}><img src={Pro} alt="com" /></div>
				<div id="game" style={isnav ? {bottom : "240px"} : {bottom : "-55px"}} onClick={goGame}><img src={Gam} alt="com" /></div>        
				<img src={egg_nav} alt="nav_egg" id="egg_nav" style={isnav ? {bottom : "10px"} : {bottom : "-300px"}} onClick={() => setIsnav(false)}/>
			</div>  
		</div>
	)
} export default Nav