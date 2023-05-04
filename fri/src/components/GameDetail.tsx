import { GameType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector } from "react-redux";
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
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  })
  
  useEffect(() => {
    const api_url = process.env.REACT_APP_REST_API;
    const getData = async () => {
      if(api_url === undefined) return;
      try {
        const header = {
          "Content-Type" : "application/json",
          "Authorization" : userId
        }
        await axios.get(api_url + "game-room/" + gameRoomId, {headers : header});
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
