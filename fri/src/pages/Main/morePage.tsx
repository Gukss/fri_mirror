import { useEffect, useState } from "react";
import axios from "axios";
import Room from "../../components/MeetingRoom";
import GameRoom from "../../components/GameRoom";
import Head from "../../components/LogoEgg";
import { useNavigate, useLocation } from "react-router-dom";
import Nav from "../../components/navEgg";
import "./more.scss"

function More() {
	const location = useLocation();
	const queryParams = new URLSearchParams(location.search);
	const category = queryParams.get("category");
	const region = queryParams.get("region");
	const text = queryParams.get("text");
	const navigate = useNavigate();
	const [isnav, setIsnav] = useState(false);
	const api_url = process.env.REACT_APP_REST_API;

	const [game, setGame] = useState([]);
	const [room, setRoom] = useState([]);

	useEffect(() => {
		const userId = 1;
		const getData = async () => {
			try {
				const res = await axios.get(api_url + `room/category?area=${region}&category=${category}`)
				if(category !== "BETTING") setRoom(res?.data)
				else setGame(res?.data)
			}
			catch(e){console.log(e)}
		}
		getData();
	}, [])

	return (
		<div className="more_room">
			<Head />
			<div className="text">{text}</div>
				<div className="room">
					{
						category === "BETTING" ?
						game.map((room, idx) => (
							<GameRoom key={idx} room={room} />
			  ))
						:
						room.map((room, idx) => (
							<Room key={idx} room={room} />
						))
					}
				</div>

			<Nav isnav={isnav} setIsnav={setIsnav} />
		</div>
	)
}
export default More;