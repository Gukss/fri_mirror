import { useState, useEffect, useRef, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import { game } from "../../redux/user";
import Back from "../../assets/back.png";
import lgm from "../../assets/lgm.png";
import mgl from "../../assets/mgl.png";

import axios from "axios";
import "./game.scss";

import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

export type gameMessage = {
  gameRoomId: number;
  userList: userInfo[];
};

export type userInfo = {
  userId: number;
  userProfile: string;
  nickname: string;
  result: number;
};

export type gameType = {
  roomId: number;
  title: string;
  location: string;
  headCount: number;
  participation: [];
};

export type participationType = {
  name: string;
  anonymousProfileImageUrl: string;
  userId: number;
};

function GameWaiting() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  let gameTime: number;
  let totalCnt: number;
  const client = useRef<StompJs.Client>();
  const [gameinfo, setGame] = useState<gameType | null>(null);
  const [isLgm, setIsLgm] = useState(true);
  const [ready, setIsready] = useState(false);
  const api_url = process.env.REACT_APP_REST_API;
  const [isconnect, setIsconnect] = useState(false);

  const gameRoomId = useSelector((state: RootState) => {
    return state.strr.gameRoomId;
  });
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  const [state, setState] = useState<gameMessage>({
    gameRoomId: Number(gameRoomId),
    userList: []
  });

  const goGame = () => {
    navigate(
      `/game/${gameRoomId}?time=${gameTime}&head=${totalCnt}`
    );
  };

  const clickReady= async () => {
    try{
      const header = {
        "Content-Type": "application/json",
        Authorization: userId
      };
      const data = {        
        "gameRoomId": gameRoomId,
        "ready": true  
      }
      await axios.patch(api_url+'user/ready',data, {headers : header})
    }
    catch(e){console.log(e)}
  }

  const subscribeChatting = async () => {
    setIsconnect(true);
    client.current?.subscribe(
    `/sub/game-room/info/${gameRoomId}`,
    ({ body }) => {
      setState(JSON.parse(body))
    });
    client.current?.subscribe(
      `/sub/game-room/ready/${gameRoomId}`,
      ({ body }) => {
        if(JSON.parse(body)) goGame();
      });
  };

  const stompActive = () => {
    if (client.current !== undefined) {
      client.current.activate();
    }
  };

  // 웹 소켓 끊기.
  const disconnect = () => {
    if (client.current !== undefined) client.current.deactivate();
  };

  const publishInit = async () => {
    if (!client.current?.connected) {
      return;
    }
    setIsconnect(true);
    client.current.publish({
      destination: "/pub/game-room/info",
      body: JSON.stringify(state)
    });
  };

  useEffect(() => {
    const getData = async () => {
      const header = {
        "Content-Type": "application/json",
        Authorization: userId
      };
      const res = await axios.get(api_url + "game-room/" + gameRoomId, {
        headers: header
      });
      setGame(res.data);
      for (let i = 0; i < res.data.participation.length; i++) {
        const info = res.data.participation[i];
        const data = {
          userId: info.userId,
          userProfile: info.anonymousProfileImageUrl,
          nickname: info.name,
          ready: false,
          result: 0.0
        };
        state.userList.push(data);
      }
      totalCnt = res.data.headCount;
      gameTime = res.data.randomTime;
      await connect();
    };
    getData();
  }, []);

  const outGame = async () => {
    state.userList.filter((user) => user.userId !== userId);
    if (client.current === undefined) return;
    client.current.publish({
      destination: "/pub/game-room/ready",
      body: JSON.stringify(state)
    });
    try {
      const header = {
        "Content-Type": "application/json",
        Authorization: Number(userId)
      };
      await axios.patch(
        api_url + `game-room/${gameRoomId}/participation`,
        { participate: true },
        { headers: header }
      );
      dispatch(game("참여한 방이 없습니다."));
      navigate("/main");
    } catch (e) {
      console.log(e);
    }
  };
  // 방 시작 후 웹 소켓 연결
  const connect = async () => {
    try {
      client.current = new StompJs.Client({
        webSocketFactory: () =>
          new SockJS("https://meetingfri.com/api/ws-stomp"),
        connectHeaders: {},
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: () => {
          subscribeChatting();
          publishInit();
          setIsconnect(true);
        },
        debug: () => {
          null;
        },
        onStompError: () => {
          null;
        }
      });
      stompActive();
    } catch (e) {
      console.log(e);
    }
    return () => disconnect();
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      setIsLgm((prev) => !prev);
    }, 500);

    return () => clearInterval(intervalId);
  }, []);

  return (
    <div className="wait_game">
      {isconnect ? 
        <img
          src={Back}
          alt="<"
          id="back"
          onClick={outGame}
          style={isconnect ? { display: "block" } : { display: "none" }}
        /> : (
        null
      )}
      <div className="top">{gameinfo?.title}</div>
      <div>
        {isLgm ? (
          <img src={lgm} alt="lgm" className="wait_lgm" />
        ) : (
          <img src={mgl} alt="mgl" className="wait_lgm" />
        )}
        {isLgm ? (
          <img src={mgl} alt="mgl" className="wait_lgm" />
        ) : (
          <img src={lgm} alt="lgm" className="wait_lgm" />
        )}
        {isLgm ? (
          <img src={lgm} alt="lgm" className="wait_lgm" />
        ) : (
          <img src={mgl} alt="mgl" className="wait_lgm" />
        )}
        {isLgm ? (
          <img src={mgl} alt="mgl" className="wait_lgm" />
        ) : (
          <img src={lgm} alt="lgm" className="wait_lgm" />
        )}
      </div>
      <div className="info">
        {/* 현재 접속인원 / api 방 최대 인원 */}
        <div>
          {state.userList.length} / {gameinfo?.headCount}
        </div>
        <div>참여 대기중...</div>
      </div>
      <div className="player-list">
        {/* 현재 참가중인 인원들 리스트 받아서 돌리기 */}
        {state?.userList.length
          ? state.userList.map((player: userInfo, index) => (
              <div className="player" key={index}>
                <div className="player-profile">
                  <img
                    src={player?.userProfile}
                    alt="player-profile"
                    className="profile-img"
                  />
                </div>
                <div className="player-name">{player?.nickname}</div>
              </div>
            ))
          : null}
      </div>
      {isconnect ?
      <>
      {
        ready ? 
      <div className="isready-btn">준비완료</div> :
         
        <button
        className="ready-btn"
        onClick={() => {
          setIsready(true);
          clickReady();
        }}
      >
        준비하기
      </button>
       }
      </>        
       : (
        <div className="ready">다른 플레이어 기다리는 중...</div>
      )}
    </div>
  );
}
export default GameWaiting;