import { GameType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector, useDispatch } from "react-redux";
import { useState, useEffect } from "react";
import { game } from "../redux/user";
import axios from "axios";


interface roomType {
  room: GameType;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

interface Gamedetail {
  participantCount: number;
  participate: boolean;
  participation: { name: string; profileUrl: string }[];
}

function GameDetail({ room, open, setOpen }: roomType) {
  const { gameRoomId, title, headCount, location } = room;
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  // game detail data
  const [data, setData] = useState<Gamedetail>({
    participantCount: 0,
    participate: false,
    participation: []
  });

  const goGame = async () => {
    try {
      const header = {
        "Content-Type" : "application/json",
        "Authorization" : userId
      }
      const res = await axios.patch(api_url + `game-room/${gameRoomId}/participation`, {"isParticipate" : false}, {headers : header})
      console.log(res.data)
      dispatch(game(String(room.gameRoomId)))
      navigate(`/wait/${room.gameRoomId}?time=${res.data.randomTime}`)    
    }
    catch(e){console.log(e)}
  }

  useEffect(() => {
    const getData = async () => {
      if (api_url === undefined) return;
      try {
        const header = {
          "Content-Type": "application/json",
          Authorization: userId
        };
        const res = await axios.get(api_url + "game-room/" + gameRoomId, {
          headers: header
        });
        const participantCount = res.data.participantCount;
        const participate = res.data.participate;
        const participation = res.data.participation;
        setData({
          ...data,
          participantCount: participantCount,
          participate: participate,
          participation: participation
        });
      } catch (e) {
        console.log(e);
      }
    };
    getData();
  }, []);
  return (
    <>
      {open ? (
        <div className="room_detail">
          <div className="room_modal">
            <div className="title">
              <span>{title}</span>
              <span style={{ color: "#FFC000" }} onClick={() => setOpen(false)}>
                X
              </span>
            </div>
            <p className="place"># {location}</p>
            <div className="soft">
              <div className="header">
                <div>참가자</div>
                <div className="total">
                  {data.participantCount}/{headCount}
                </div>
              </div>
              <div className="game-profile">
                {data.participantCount === 0 ? (
                  <div>참여자가 없습니다.</div>
                ) : (
                  data.participation.map((info: any) => (
                    <div key={info.name} className="info">
                      <div className="profile-img">{info.url}</div>
                      <div className="name">{info.name}</div>
                    </div>
                  ))
                )}
              </div>
            </div>
            <div className="join_game" onClick={goGame}>
              참여하기
            </div>
          </div>
        </div>
      ) : null}
    </>
  );
}
export default GameDetail;
