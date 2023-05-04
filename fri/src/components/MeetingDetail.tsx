import { useEffect, useState } from "react";
import { MeetType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../redux/store";
import { meeting } from "../redux/user";
import axios from "axios";

interface roomType {
	room : MeetType;
	open : boolean;
	setOpen : React.Dispatch<React.SetStateAction<boolean>>;
}

function MeetingDetail({room, open, setOpen} : roomType) {
	const {headCount, location, major, nonMajor, roomId, title, roomCategory, is_com} = room; 
	const [data, setData] = useState({});
	const navigate = useNavigate();
	const dispatch = useDispatch();
	const userId = useSelector((state: RootState) => {
		return state.strr.userId;
	});
	const isRoom = useSelector((state: RootState) => {
		return state.strr.roomId;
	});
	const api_url = process.env.REACT_APP_REST_API;

	useEffect(() => {
		const getDetail = async () => {
			try {
				const header = {
					"Content-Type" : "application/json",
					"Authorization" : userId
				}
				const res = await axios.get(api_url + "room/" + roomId, {headers: header});
				setData(res?.data);
			}
			catch(e){console.log(e)}
		}
		getDetail();
	}, [])

	const goChat = async () => {
		if(isRoom !== "참여한 방이 없습니다"){
			alert("이미 다른 방에 참여중입니다.")
			return;
		}
		const header = {
			"Content-Type" : "application/json",
			"Authorization" : userId
		}
		try {
			const res = await axios.patch(api_url + `user/room/${roomId}`, {"isParticipate" : false}, {headers : header});
			dispatch(meeting(res.data.roomId))
			navigate(`/chatting/${res.data.roomId}`);
		}
		catch(e){console.log(e)}
	}

	return (
		<>
		{
			open ? 
			<div className="room_detail">
				<div className="room_modal">
				<div className="title"><span>{title}</span><span style={{color: "#FFC000"}} onClick={() => setOpen(false)}>X</span></div>
				<p className="place"># {location}</p>
				<p className="soft">전공 <span className="total">{major}/{headCount/2}</span></p>
				<p className="other">비전공 <span className="total">{nonMajor}/{headCount/2}</span></p>
				<div className="join_game" onClick={goChat}>참여하기</div>
			</div>
		</div>
		:
		null
		}
		</>
		
	)
}
export default MeetingDetail