import { GameType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector, useDispatch } from "react-redux";
import { useState, useEffect, useCallback } from "react";
import { game } from "../redux/user";
import axios from "axios";

interface roomType {
  room: GameType;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

interface Gamedetail {
  participationCount: number;
  participate: boolean;
  participation: { name: string; profileUrl: string }[];
}

function GameDetail({ room, open, setOpen }: roomType) {
  const { gameRoomId, title, headCount, location, participationCount } = room;
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  const gameId = useSelector((state: RootState) => {
    return state.strr.gameRoomId;
  });

  // game detail data
  const [data, setData] = useState<Gamedetail>({
    participationCount: 0,
    participate: false,
    participation: []
  });

  // useCallback 추가
  const goGame = useCallback(async () => {
    try {
      const header = {
        "Content-Type": "application/json",
        Authorization: userId
      };
      const res = await axios.patch(
        api_url + `game-room/${gameRoomId}/participation`,
        { participate: false },
        { headers: header }
      );
      dispatch(game(String(room.gameRoomId)));
      navigate(`/wait/${room.gameRoomId}`);
    } catch (e) {
      console.log(e);
    }
  }, []);

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
        const participationCount = res.data.participationCount;
        const participate = res.data.participate;
        const participation = res.data.participation;
        setData({
          ...data,
          participationCount: participationCount,
          participate: participate,
          participation: participation
        });
      } catch (e) {
        console.log(e);
      }
    };
    getData();
  }, []);

  const isPossible = useSelector((state: RootState) => {
    if (state.strr.gameRoomId === String(gameRoomId)) {
      return "yourRoom";
    } else if (state.strr.gameRoomId !== "참여한 방이 없습니다.") {
      return "already";
    } else if (headCount <= participationCount) {
      return "tooMany";
    } else {
      return true;
    }
  });
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
                  {data.participationCount}/{headCount}
                </div>
              </div>
              {data.participationCount === 0 ? (
                <div className="profile">
                  <div>참여자가 없습니다.</div>
                </div>
              ) : (
                <div className="game-profile">
                  {data.participation.map((info: any) => (
                    <div key={info.name} className="info">
                      <div className="profile-img">
                        <img src={info.anonymousProfileImageUrl} alt="프로필" />
                      </div>
                      <div className="name">{info.name}</div>
                    </div>
                  ))}
                </div>
              )}
            </div>
            {isPossible === "yourRoom" ? (
              <div
                className="join_game"
                onClick={goGame}
                // 참여자가 총인원보다 작을때는 누를 수 있지만, 같거나 클 때는 참여가 불가능함
              >
                참여하기
              </div>
            ) : isPossible === "already" ? (
              <div
                className="join_game"
                style={{
                  background: "#ffefbe"
                }}
                onClick={() => {
                  navigate(`/wait/${gameId}`);
                }}
              >
                이미 게임에 참여중이에요!
                <br /> 내 게임방으로
              </div>
            ) : isPossible === "tooMany" ? (
              <div
                className="join_game"
                style={{
                  background: "#ffefbe"
                }}
              >
                참여할 수 없습니다!
              </div>
            ) : (
              <div className="join_game" onClick={goGame}>
                참여하기
              </div>
            )}
          </div>
        </div>
      ) : null}
    </>
  );
}
export default GameDetail;
