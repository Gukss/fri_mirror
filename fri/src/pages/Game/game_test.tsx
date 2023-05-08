import { useState, useEffect, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import axios from "axios";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

export type userInfo = {
  gameRoomId : number;
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

function Test() {
  const client = useRef<StompJs.Client>();
  const api_url = process.env.REACT_APP_REST_API;

  const [gameinfo, setGame] = useState<gameType | null>(null);
  
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
  const [state, setState] = useState<userInfo[]>([])

  // 소켓객체와 connect되면 subscribe함수를 동작시켜서 서버에 있는 주소로 sub
const subscribeChatting = async (): Promise<void> => {
  console.log("채팅 구독");
  if(client.current === undefined) return
  await client.current.subscribe(
  "/sub/game-room/ready/" + gameRoomId,
  ({ body }: StompJs.Message) => {
  console.log(body);
  setState((pre: userInfo[]) => [...pre, JSON.parse(body)]);
  }
  );
  };
  
  // 현재는 app컴포넌트 생성과 동시에 소켓 객체가 연결이 되고 sub로 구독함!
  // 이 코드를 방에 들어갈때 연결하면 됨!
  const connect = async (): Promise<void> => {
  client.current = new StompJs.Client({
  webSocketFactory: () => new SockJS("https://meetingfri.com/api/ws-stomp"),
  connectHeaders: {},
  debug: (str: string) => {
  console.log(str);
  },
  reconnectDelay: 5000,
  heartbeatIncoming: 4000,
  heartbeatOutgoing: 4000,
  onConnect: () => {
  console.log("커넥트 되는 시점");
  subscribeChatting();
  },
  onStompError: (frame: StompJs.Frame) => {
  console.error(frame);
  },
  });
  console.log("connect");
  await stompActive();
  };
  
  const stompActive = async (): Promise<void> => {
  if(client.current !== undefined) await client.current.activate();
  };
  
  // 웹 소켓 끊기.
  const disconnect = (): void => {
  console.log("disconnect");
  if(client.current !== undefined) client.current.deactivate();
  };
  
  // 방 시작 후 웹 소켓 연결
  useEffect(() => {
  console.log("방 처음");
  connect();
  return () => disconnect();
  }, []);
  
  const publishMessage = async (): Promise<void> => {
  console.log("채팅을 입력해서 pub이벤트 발생!");
  console.log(state);
  if (client.current === undefined || !client.current.connected) {
  return;
  }
  await client.current.publish({
    destination: "/pub/game-room/ready",
    body: JSON.stringify({
    gameRoomId: gameRoomId,
    userId : userId,
    userProfile: profile,
    ready : false,
    result : 0.00
  // 밑에 코드로 실제 값들을 넘겨줘야함
  // roomId: roomInfo.roomId,
  // message,
  // memberId: userInfo.memberId,
  }),
  });
  };

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
  return(
    <div >
    <h1 onClick={publishMessage}>클릭</h1>      
    </div>
  )
}
export default Test;