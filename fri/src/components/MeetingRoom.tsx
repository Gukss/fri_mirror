import { MeetType } from "../pages/Main/mainPage";
import Room from "./MeetingDetail";
import { useSelector} from "react-redux";
import { RootState } from "../redux/store";
import { useEffect, useState } from "react";
import axios from "axios";

interface roomType {
  room: MeetType;
}

interface Roomdetail {
  roomId : number;
  title : string;
  location : string;
  roomCategory: string;
  headCount : number;
  isParticipate : boolean;
  participate: boolean;
  major: { name: string; url: string }[];
  nonMajor: { name: string; url: string }[];
}

function MeetingRoom({ room }: roomType) {
  const [open, setOpen] = useState(false);
  const [data, setData] = useState<Roomdetail>()
  const [is_full, setIsfull] = useState(false);
  const {
    headCount,
    is_com
  } = room;
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  useState(() => {
    if ("is_com" in room) setIsfull(is_com);
  });

  const getDetail = async () => {
    try {
      const header = {
        "Content-Type": "application/json",
        Authorization: userId
      };
      const res = await axios.get(api_url + "room/" + room.roomId, {
        headers: header
      });
      setData(res.data);
    } catch (e) {
      console.log();
    }
  };

  return (
    <>
      {is_full ? (
        <div className="room_component">
          <div className="meeting_room_full">모집 완료</div>
        </div>
      ) : (
        <div className="room_component">
          <div className="meeting_room" onClick={() => {getDetail(); setOpen(true)}}>
            <p className="place"># {room?.location}</p>
            <p className="title">{room?.title}</p>
            <p className="other">
              전공
              <span className="total">
                {room.major}/{headCount / 2}
              </span>
            </p>
            <p className="other">
              비전공
              <span className="total">
                {room.nonMajor}/{headCount / 2}
              </span>
            </p>
          </div>
          {open ? <Room id={room.roomId} headCount={room.headCount} open={open} setOpen={setOpen} /> : null}
        </div>
      )}
    </>
  );
}
export default MeetingRoom;
