import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import plus from "../assets/plus_btn.png"
import egg_nav from "../assets/egg_nav.png"
import yellow from "../assets/egg_yellow.png"

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
		if(eggCnt > 0)	navigate("/add" + "?tab=cate")
		else alert("현재 보유중인 달걀이 없어요.. 내일 다시 오셔야 할 듯..")
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
				<div id="meeting" style={isnav ? {bottom : "250px"} : {bottom : "-130px"}} onClick={() => navigate("/main")}>미팅</div>
				<div id="chat" style={isnav ? {bottom : "175px"} : {bottom : "-55px"}} onClick={goChat}>채팅</div>
				<div id="commu" style={isnav ? {bottom : "65px"} : {bottom : "-65px"}}>커뮤니티</div>
				<div id="game" style={isnav ? {bottom : "65px"} : {bottom : "-65px"}} onClick={goGame}>게임</div>
				<div id="my" style={isnav ? {bottom : "175px"} : {bottom : "-55px"}} onClick={() => navigate("/my")}>my</div>        
				<img src={egg_nav} alt="nav_egg" id="egg_nav" style={isnav ? {bottom : "10px"} : {bottom : "-300px"}} onClick={() => setIsnav(false)}/>
			</div>  
		</div>
	)
} export default Nav