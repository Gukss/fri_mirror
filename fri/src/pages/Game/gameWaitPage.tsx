import { useState, useEffect, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
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
  gameRoomId : number;
  userList :  userInfo[];
}

export type userInfo = {
  userId: number;
  userProfile: string;
  nickname: string;
  ready: boolean;
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
  name : string;
  anonymousProfileImageUrl : string;
}

function GameWaiting() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const gameTime = queryParams.get("time");
  const client = useRef<StompJs.Client>();
  const [gameinfo, setGame] = useState<gameType | null>(null);
  const [isLgm, setIsLgm] = useState(true);
  const [ready, setIsready] = useState(false);
  const api_url = process.env.REACT_APP_REST_API;
  
  const gameRoomId = useSelector((state: RootState) => {
    return state.strr.gameRoomId;
  });
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  const profile = useSelector((state: RootState) => {
    return state.strr.anonymousProfileImageUrl;
  });
  const nickname = useSelector((state: RootState) => {
    return state.strr.nickname;
  })
  const [state, setState] = useState<gameMessage>({
    gameRoomId : Number(gameRoomId),
    userList : [{
      userId : userId,
      userProfile : profile,
      nickname : nickname,
      ready : false,
      result : 0.00
    },
  ]   
  })
  
  const goGame = async () => {
    try {
      await axios.get(api_url + "game-room/start");
      navigate(`game/${gameRoomId}?time=${gameTime}`);
    } catch (e) {
      console.log(e);
    }
  };

  const subscribeChatting = () => {
    console.log("게임 구독")
    client.current?.subscribe(
      `/sub/gameRoom/ready/${gameRoomId}`,
      ({ body }) => {
        console.log(body)
        state.userList.push(JSON.parse(body))
      }
    );
    publishInit();
    let flag = true
    for(let i = 0; i < state.userList.length; i++){
      if(state.userList[i].ready === false){
        flag = false;
        break;
      }
    }
    if(flag && gameinfo?.headCount === state.userList.length){
      goGame()
    }
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

  const publishMessage = async () => {
    if (!client.current?.connected) {
      return;
    }

    for(let i = 0; i < state.userList.length; i++){
      if(state.userList[i].userId === userId){
        state.userList[i].ready = true;
        break;
      }
    }

    client.current.publish({
      destination: "/pub/gameRoom/ready/"+ gameRoomId,
      body: JSON.stringify(state),
    });
  }

  const publishInit = async () => {
    if (!client.current?.connected) {
      return;
    }

    client.current.publish({
      destination:  "/pub/gameRoom/ready/"+ gameRoomId,
      body: JSON.stringify(state),
    });
  };

  const outGame = async () => {
    try {
      const header = {
        "Content-Type": "application/json",
        Authorization: Number(userId)
      };
      const res = await axios.patch(
        api_url + `game-room/${gameRoomId}/participation`,
        { participate: true },
        { headers: header }
      );
      console.log(res.data);
      dispatch(game("참여한 방이 없습니다."));
      navigate("/main");
    } catch (e) {
      console.log(e);
    }
  };

  // 방 시작 후 웹 소켓 연결
  useEffect(() => {
    const connect = async () => {
      try {
        client.current = new StompJs.Client({
          webSocketFactory: () => new SockJS("https://meetingfri.com/api/ws-stomp"),
          connectHeaders: {},
          reconnectDelay: 5000,
          heartbeatIncoming: 4000,
          heartbeatOutgoing: 4000,
          onConnect: () => {
            console.log("커넥트 되는 시점");
            subscribeChatting();
          },
          debug: (str) => {
            console.log(str);
          },
          onStompError: (frame) => {
            console.error(frame);
          }
        });
        await stompActive();
      } catch (e) {
        console.log;
      }
    };
    console.log("방 처음");
    connect()
    return () => disconnect();
  }, []);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setIsLgm((prev) => !prev);
    }, 500);

    return () => clearInterval(intervalId);
  }, []);

  useEffect(() => {
    const getData = async () => {
      const header = {
        "Content-Type" : "application/json",
        "Authorization" : userId
      }
      const res = await axios.get(api_url + "game-room/" + gameRoomId, {headers : header})
      setGame(res.data)
      console.log(res.data)
    }
    getData()
  }, [])

  return (
    <div className="wait_game">
      <img src={Back} alt="<" id="back" onClick={outGame} />
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
          {gameinfo?.participation.length} / {gameinfo?.headCount}
        </div>
        <div>참여 대기중...</div>
      </div>
      <div className="player-list">
        {/* 현재 참가중인 인원들 리스트 받아서 돌리기 */}
        {gameinfo?.participation.length
          ? gameinfo.participation.map((player: participationType, index) => (
              <div className="player" key={index}>
                <div className="player-profile">
                  <img
                    src={player?.anonymousProfileImageUrl}
                    alt="player-profile"
                    className="profile-img"
                  />
                </div>
                <div className="player-name">{player?.name}</div>
              </div>
            ))
          : null}
      </div>
      {ready ? (
        <div className="ready">다른 플레이어 기다리는 중...</div>
      ) : (
        <button
          className="ready-btn"
          onClick={() => {
            publishMessage();
            setIsready(true);
          }}
        >
          준비하기
        </button>
      )}
    </div>
  );
}
export default GameWaiting;
