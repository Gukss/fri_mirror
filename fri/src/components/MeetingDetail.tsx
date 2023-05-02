import { useEffect, useState } from "react";
import {MeetType} from "../pages/Main/mainPage";
import axios from "axios";

interface roomType {
	room : MeetType;
	open : boolean;
	setOpen : React.Dispatch<React.SetStateAction<boolean>>;
}

function MeetingDetail({room, open, setOpen} : roomType) {
	const {headCount, location, major, nonMajor, roomId, title, roomCategory, is_com} = room; 
	const [data, setData] = useState({});

	const api_url = process.env.REACT_APP_REST_API;

	useEffect(() => {
		const getDetail = async () => {
			try {
				const res = await axios.get(api_url + "room/" + roomId);
				setData(res?.data);
			}
			catch(e){console.log(e)}
		}
		getDetail();
	}, [])

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
				<div className="join_game">참여하기</div>
			</div>
		</div>
		:
		null
		}
		</>
		
	)
}
export default MeetingDetail