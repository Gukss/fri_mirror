import {MeetType} from '../pages/Main/mainPage'

interface roomType {
	room : MeetType;
	open : boolean;
	setOpen : React.Dispatch<React.SetStateAction<boolean>>;
}

function MeetingDetail({room, open, setOpen} : roomType) {
	const {place, title, soft, total, other, is_com} = room; 

	return (
		<>
		{
			open ? 
			<div className="room_detail">
				<div className="room_modal">
				<div className="title"><span>{title}</span><span style={{color: "#FFC000"}} onClick={() => setOpen(false)}>X</span></div>
				<p className="place"># {place}</p>
				<p className="soft">전공 <span className="total">{soft}/{total}</span></p>
				<p className="other">비전공 <span className="total">{other}/{total}</span></p>
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