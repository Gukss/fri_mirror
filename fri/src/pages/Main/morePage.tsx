import data from '../../components/data/meeting_dummy.json'
import game_data from '../../components/data/game_dummy.json'
import Room from '../../components/MeetingRoom'
import GameRoom from '../../components/GameRoom'
import Head from "../../components/LogoEgg"
import { useNavigate, useLocation } from 'react-router-dom'
import { useState } from 'react';
import Nav from '../../components/navEgg'
import './more.scss'

function More() {
	const location = useLocation();
	const queryParams = new URLSearchParams(location.search)
	const category = queryParams.get('category')
	const text = queryParams.get('text')
	const navigate = useNavigate();
	const [isnav, setIsnav] = useState(false);

	const [game, setGame] = useState(game_data)
	const [room, setRoom] = useState(data)

	return (
		<div className='more_room'>
			<Head />
			<div className='text'>{text}</div>
			<div className="room">
				{
					category === "game" ?
					game.map((room, idx) => (
						<GameRoom key={idx} room={room} />
          ))
					:
					room.map((meeting, idx) => (
						<Room key={idx} room={meeting} />
					))
				}
			</div>
			<Nav isnav={isnav} setIsnav={setIsnav} />
		</div>
	)
}
export default More;