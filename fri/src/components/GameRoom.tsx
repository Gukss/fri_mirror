import { GameType } from "../pages/Main/mainPage";
import Room from "./GameDetail";
import { useState } from "react";

interface roomType {
  room: GameType;
}
function GameRoom({ room }: roomType) {
  const [open, setOpen] = useState(false);
  const { gameRoomId, title, headCount, location } = room;

  return (
    <div className="room_component">
      {/* {current_cnt === total_cnt ? (
        <div className="meeting_room_full">게임중</div>
      ) : ( */}
        <div className="game_room" onClick={() => setOpen(true)}>
          <p className="place"># {location}</p>
          <p className="title">{title}</p>
          <p className="cnt">
            {" "}
            참가자{" "}
            <span>
              0/{headCount}
            </span>
          </p>
        </div>
      {/* )} */}
      <Room room={room} open={open} setOpen={setOpen} />
    </div>
  );
}
export default GameRoom;
