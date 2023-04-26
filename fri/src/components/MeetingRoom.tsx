import {MeetType} from '../pages/Main/mainPage'
import Room from './MeetingDetail'
import { useState } from 'react';

interface roomType {
  room : MeetType;
}
function MeetingRoom({room} : roomType) {
  const [open, setOpen] = useState(false)
  const {place, title, soft, total, other, is_com} = room 

  return(
    <>
    {
      is_com ?    
      <div className="room_component">
        <div className="meeting_room" onClick={()=> setOpen(true)}>
          <p className="place"># {place}</p>
          <p className="title">{title}</p>
          <p className="soft">전공 <span className="total">{soft}/{total}</span></p>
          <p className="other">비전공 <span className="total">{other}/{total}</span></p>
        </div>
      <Room room={room} open={open} setOpen={setOpen} />
      </div>
      :
      <div className="room_component">
        <div className="meeting_room_full">
          모집 완료
        </div>
      </div>
    }
    </>
  )
}
export default MeetingRoom
