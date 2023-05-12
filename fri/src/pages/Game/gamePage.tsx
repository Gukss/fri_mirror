import { useState, useEffect, useRef, useCallback } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import lgm from "../../assets/lgm.png";
import mgl from "../../assets/mgl.png";
import egg from "../../assets/egg_fri.png";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import { game } from "../../redux/user";
import axios from "axios";

import "./game.scss";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

export type userInfo = {
  gameRoomId: number;
  userId: number;
  userProfile: string;
  nickname: string;
  ready: boolean;
  gameTime: number;
};

export type partici = {
  userId: number;
  name: string;
  profileUrl: string;
};

const GameMain = (): JSX.Element => {
  const navigate = useNavigate();
  const client = useRef<StompJs.Client>();
  const [seconds, setSeconds] = useState<number>(3);
  const [timer, setTimer] = useState<number>(10);
  const [modal, setModal] = useState<boolean>(true);
  const [isLgm, setIsLgm] = useState(true);
  const [imagesLoaded, setImagesLoaded] = useState<boolean>(false);
  const start = "start!";
  const intervalRef = useRef<NodeJS.Timeout>();
  const timerRef = useRef<NodeJS.Timeout>();
  const lgmRef = useRef<NodeJS.Timeout>();
  const resultRef = useRef<HTMLDivElement>(null);
  const location = useLocation();
  const dispatch = useDispatch();
  const queryParams = new URLSearchParams(location.search);
  const gameTime = queryParams.get("time");
  const totalCnt = queryParams.get("head");
  const [area, setArea] = useState<string>("");
  const [flip, setFlip] = useState<boolean>(false);
  const [result, setResult] = useState<boolean>(false);
  const [looser, setLooser] = useState("");
  const [isStart, setStart] = useState(false);
  const res: userInfo[] = [];
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
  const [nickname, setNick] = useState<string>("");

  const [state, setState] = useState<userInfo[]>([]);

  const resultSet = async () => {
    if (Number(totalCnt) === res.length) {
      setState(res);
      const num = res.length;
      setLooser(res[num - 1].nickname);
    }
  };

  const resultSort = async () => {
    res.sort((a: userInfo, b: userInfo): number => {
      return (
        Math.abs(Number(gameTime) - a.gameTime) -
        Math.abs(Number(gameTime) - b.gameTime)
      );
    });
    await resultSet();
  };

  const outGame = async () => {
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

  const publishMessage = async (time: number): Promise<void> => {
    if (!client.current?.connected) {
      return;
    }
    client.current.publish({
      destination: "/pub/game-room/stop",
      body: JSON.stringify({
        gameRoomId: gameRoomId, // 필요한지 모르겟음
        userId: userId,
        gameTime: time,
        anonymousProfileImageId: profile,
        nickname: nickname
      })
    });
  };

  const subscribeChatting = async (): Promise<void> => {
    console.log("연결")
    client.current?.subscribe(
      `/sub/game-room/stop/${gameRoomId}`,
      ({ body }) => {
        res.push(JSON.parse(body));
        setState((prev) => [...prev, JSON.parse(body)]);
        if (res.length === Number(totalCnt)) resultSort();
      }
    );
  };

  const stompActive = async (): Promise<void> => {
    if (client.current !== undefined) {
      client.current.activate();
    }
  };

  // 웹 소켓 끊기.
  const disconnect = () => {
    if (client.current !== undefined) client.current.deactivate();
  };

  // 방 시작 후 웹 소켓 연결
  const connect = async () => {
    try{
      client.current = new StompJs.Client({
      webSocketFactory: () =>
        new SockJS("https://meetingfri.com/api/ws-stomp"),
        connectHeaders: {},
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      onConnect: () => {
        subscribeChatting();
        setStart(true);
      },
      debug: () => {
        null;
      },
      onStompError: () => {
        null;
      }
    });
    await stompActive();
  }
  catch(e){console.log}
  return() => disconnect();
  };

  // 소켓 연결 시점
  useEffect(() => {
    const getData = async () => {
      try {
        const header = {
          "Content-Type": "application/json",
          Authorization: userId
        };
        const res = await axios.get(api_url + "game-room/" + gameRoomId, {
          headers: header
        });
        setArea(res.data.location);
        res.data.participation.map((player: partici) => {
          if (player.userId === userId) setNick(player.name);
        });
      } catch (e) {
        console.log(e);
      }
    };
    getData();
    connect();
  }, []);

  // 이미지 로딩
  useEffect(() => {
    // 이미지 로딩
    const lgmImage = new Image();
    const mglImage = new Image();
    lgmImage.onload = () => {
      mglImage.onload = () => {
        setImagesLoaded(true);
      };
      mglImage.src = "/assets/mgl.png";
    };
    lgmImage.src = "/assets/lgm.png";
  }, []);
  // 시작 3초
  useEffect(() => {
    if (imagesLoaded && isStart) {
      intervalRef.current = setInterval(() => {
        setSeconds((seconds) => seconds - 1);
      }, 1000);
    }

    return () => clearInterval(intervalRef.current);
  }, [imagesLoaded, isStart]);

  useEffect(() => {
    if (seconds === -1) {
      clearInterval(intervalRef.current);
      setModal(false);
    }
  }, [seconds]);

  // 10초타이머
  useEffect(() => {
    if (!modal) {
      timerRef.current = setInterval(() => {
        setTimer((seconds) => seconds - 0.01);
      }, 10);
    }

    return () => clearInterval(timerRef.current);
  }, [modal]);

  useEffect(() => {
    if (timer < 0.01) {
      clearInterval(timerRef.current);
      clearInterval(lgmRef.current);
      publishMessage(0.0);
      setResult(true);
    }
  }, [timer]);

  // 금만이
  useEffect(() => {
    if (!modal) {
      lgmRef.current = setInterval(() => {
        setIsLgm((prev) => !prev);
      }, 200);
    }

    return () => clearInterval(lgmRef.current);
  }, [modal]);

  // 버튼 클릭
  const handleClick = () => {
    setFlip(true);
    clearInterval(timerRef.current);
    clearInterval(lgmRef.current);
    publishMessage(Number(timer.toFixed(2)));
    setResult(true);
  };

  // 모달 바깥쪽 클릭하면
  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (
        resultRef.current &&
        !resultRef.current.contains(event.target as Node)
      ) {
        navigate("/main");
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [resultRef]);

  // 소켓에서 뭐 주면 결과 모달 띄우기

  return (
    <>
      {modal ? (
        <div className="game_3s_back">
          {seconds ? (
            <div>{seconds}</div>
          ) : (
            <div style={{ fontSize: "100px" }}>{start}</div>
          )}
        </div>
      ) : null}
      {result ? (
        <div className="game-result-back">
          <div className="game-result" ref={resultRef}>
            <div className="header">결과</div>
            <div className="name">{looser} 잘 먹을게요~~</div>
            <div className="time">5분 뒤 {area}에서 만나요</div>
            <div className="result-table">
              {state.map((player, index) => (
                <div className="result" key={index}>
                  <div className="number">{index + 1}등</div>
                  <div className="name">{player.nickname}</div>
                  <div className="time">{player.gameTime}</div>
                </div>
              ))}
            </div>
            <button
              className="result-btn"
              onClick={() => {
                outGame();
              }}
            >
              나가기
            </button>
          </div>
        </div>
      ) : null}
      <div className="game">
        <div className="timer">
          {" "}
          {isLgm ? (
            <img src={lgm} alt="lgm" className="wait_lgm" />
          ) : (
            <img src={mgl} alt="mgl" className="wait_lgm" />
          )}
          {"  "}
          {timer.toFixed(2)}
          {"  "}
          {isLgm ? (
            <img src={lgm} alt="lgm" className="wait_lgm" />
          ) : (
            <img src={mgl} alt="mgl" className="wait_lgm" />
          )}
        </div>
        <div className="game-content">
          <span>{gameTime}</span>초에 <br /> 프라이를 눌러주세요.
        </div>
        <div className="game-btn">
          <img
            src={egg}
            alt="fri-btn"
            className={flip ? "flip" : ""}
            onClick={handleClick}
          />
        </div>
      </div>
    </>
  );
}

export default GameMain;
