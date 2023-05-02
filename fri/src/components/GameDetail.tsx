import { GameType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";

interface roomType {
  room: GameType;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

function GameDetail({ room, open, setOpen }: roomType) {
  const { place, title, current_cnt, total_cnt } = room;
  const navigate = useNavigate();

  return (
    <>
      {open ? (
        <div className="room_detail">
          <div className="room_modal">
            <div className="title">
              <span>{title}</span>{" "}
              <span style={{ color: "#FFC000" }} onClick={() => setOpen(false)}>
                X
              </span>
            </div>
            <p className="place"># {place}</p>
            <p className="cnt">
              {" "}
              참가자{" "}
              <span>
                ( {current_cnt} / {total_cnt} )
              </span>
            </p>
            <div className="join_game" onClick={()=> navigate("/wait/1")}>참여하기</div>
          </div>
        </div>
      ) : null}
    </>
  );
}
export default GameDetail;
