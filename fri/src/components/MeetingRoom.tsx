import { MeetType } from "../pages/Main/mainPage";
import Room from "./MeetingDetail";
import { useState } from "react";

interface roomType {
  room: MeetType;
}
function MeetingRoom({ room }: roomType) {
  const [open, setOpen] = useState(false);
  const [is_full, setIsfull] = useState(false);
  const {
    headCount,
    location,
    major,
    nonMajor,
    roomId,
    title,
    roomCategory,
    is_com
  } = room;
  useState(() => {
    if ("is_com" in room) setIsfull(is_com);
  });
  return (
    <>
      {is_full ? (
        <div className="room_component">
          <div className="meeting_room_full">모집 완료</div>
        </div>
      ) : (
        <div className="room_component">
          <div className="meeting_room" onClick={() => setOpen(true)}>
            <p className="place"># {location}</p>
            <p className="title">{title}</p>
            <p className="other">
              전공
              <span className="total">
                {major}/{headCount / 2}
              </span>
            </p>
            <p className="other">
              비전공
              <span className="total">
                {nonMajor}/{headCount / 2}
              </span>
            </p>
          </div>
          {open ? <Room room={room} open={open} setOpen={setOpen} /> : null}
        </div>
      )}
    </>
  );
}
export default MeetingRoom;
