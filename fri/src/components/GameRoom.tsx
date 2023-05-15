import { GameType } from "../pages/Main/mainPage";
import { useSelector} from "react-redux";
import { RootState } from "../redux/store";
import Room from "./GameDetail";
import { useState, useEffect } from "react";
import axios from "axios";

interface roomType {
  room: GameType;
}
function GameRoom({room} : roomType) {
  const [open, setOpen] = useState(false);
  const [data, setData] = useState<GameType>()
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  const getData = async () => {
    if (api_url === undefined) return;
    try {
      const header = {
        "Content-Type": "application/json",
        Authorization: userId
      };
      const res = await axios.get(api_url + "game-room/" + room.gameRoomId, {
        headers: header
      });
      setData(res.data);
    } catch (e) {
      console.log();
    }
  };
  useEffect(() => {
    getData();
  }, []);

  return (
    <div className="room_component">
      {/* {current_cnt === total_cnt ? (
        <div className="meeting_room_full">게임중</div>
      ) : ( */}
      <div className="game_room" onClick={() =>{getData(); setOpen(true)}}>
        <p className="place"># {data?.location}</p>
        <p className="title">{data?.title}</p>
        <p className="cnt">
          참가자
          <span>
            {data?.participationCount}/{data?.headCount}
          </span>
        </p>
      </div>
      {/* )} */}
      {
        open ? 
      <Room id={room.gameRoomId} open={open} setOpen={setOpen} /> : null
}
    </div>
  );
}
export default GameRoom;
