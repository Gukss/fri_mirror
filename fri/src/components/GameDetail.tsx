import { GameType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import axios from "axios";

interface roomType {
  room: GameType;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

function GameDetail({ room, open, setOpen }: roomType) {
  const { gameRoomId, title, headCount, location } = room;
  const navigate = useNavigate();

  
  useEffect(() => {
    const api_url = process.env.REACT_APP_REST_API;
    const getData = async () => {
      if(api_url === undefined) return;
      try {
        const res = await axios.get(api_url + "game-room/" + gameRoomId);
        console.log(res.data)
      }
      catch(e){console.log(e)}
    }
    getData()
  }, [])
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
            <p className="place"># {location}</p>
            <p className="cnt">
              {" "}
              참가자{" "}
              <span>
                ( 0 / {headCount} )
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
