import { useNavigate } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector, useDispatch } from "react-redux";
import { useState, useEffect, useCallback } from "react";
import { game } from "../redux/user";
import axios from "axios";

interface roomType {
  id: number;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

interface Gamedetail {
  title: string;
  location: string;
  headCount: string;
  participationCount: number;
  participate: boolean;
  participation: { name: string; profileUrl: string }[];
}

function GameDetail({ id, open, setOpen }: roomType) {
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
  const [data, setData] = useState<Gamedetail>();

  // useCallback 추가
  const goGame = useCallback(async () => {
    try {
      const header = {
        "Content-Type": "application/json",
        Authorization: userId
      };
      const res = await axios.patch(
        api_url + `game-room/${id}/participation`,
        { participate: false },
        { headers: header }
      );
      dispatch(game(String(id)));
      navigate(`/wait/${id}`);
    } catch (e) {
      console.log();
    }
  }, []);

  useEffect(() => {
    const getData = async () => {
      console.log("dd");
      if (api_url === undefined) return;
      try {
        const header = {
          "Content-Type": "application/json",
          Authorization: userId
        };
        const res = await axios.get(api_url + "game-room/" + id, {
          headers: header
        });
        setData(res.data);
      } catch (e) {
        console.log();
      }
    };
    getData();
  }, []);

  const isPossible = useSelector((state: RootState) => {
    if (data === undefined) return;
    if (state.strr.gameRoomId === String(id)) {
      return "yourRoom";
    } else if (state.strr.gameRoomId !== "참여한 방이 없습니다.") {
      return "already";
    } else if (Number(data?.headCount) <= Number(data?.participationCount)) {
      return "tooMany";
    } else {
      return true;
    }
  });

  const handleDetailClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      // 클릭한 요소가 room_detail인 경우에만 처리
      setOpen(false);
    }
  };

  return (
    <>
      <div className="room_detail" onClick={handleDetailClick}>
        <div className="room_modal">
          <div className="title">
            <span>{data?.title}</span>
            <div
              style={{ color: "#FFC000" }}
              onClick={() => setOpen(false)}
              className="x-btn"
            >
              X
            </div>
          </div>
          <p className="place"># {data?.location}</p>
          <div className="soft">
            <div className="header">
              <div>참가자</div>
              <div className="total">
                {data?.participationCount}/{data?.headCount}
              </div>
            </div>
            {data?.participationCount === 0 ? (
              <div className="profile">
                <div>참여자가 없습니다.</div>
              </div>
            ) : (
              <div className="game-profile">
                {data?.participation.map((info: any) => (
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
    </>
  );
}
export default GameDetail;
