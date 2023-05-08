import { GameType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector } from "react-redux";
import { useState, useEffect } from "react";
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
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  // game detail data
  const [data, setData] = useState<Gamedetail>({
    participantCount: 0,
    participate: false,
    participation: []
  });

  useEffect(() => {
    const api_url = process.env.REACT_APP_REST_API;
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
        console.log("게임방 정보", res.data);
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
              {data.participantCount === 0 ? (
                <div className="profile">
                  <div>참여자가 없습니다.</div>
                </div>
              ) : (
                <div className="game-profile">
                  {data.participation.map((info: any) => (
                    <div key={info.name} className="info">
                      <div className="profile-img">{info.url}</div>
                      <div className="name">{info.name}</div>
                    </div>
                  ))}
                </div>
              )}
            </div>
            <div
              className="join_game"
              onClick={() => navigate("/wait/1")}
              // 참여자가 총인원보다 작을때는 누를 수 있지만, 같거나 클 때는 참여가 불가능함
              style={{
                pointerEvents:
                  data.participantCount <= headCount ? "auto" : "none",
                background:
                  data.participantCount <= headCount ? "#ffce3c" : "#ffefbe"
              }}
            >
              참여하기
            </div>
          </div>
        </div>
      ) : null}
    </>
  );
}
export default GameDetail;
