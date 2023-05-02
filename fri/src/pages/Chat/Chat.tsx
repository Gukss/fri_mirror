import { useEffect, useRef, useState, useCallback } from "react";
import ChatContent from "../../components/ChatContent/ChatContent";
import ChatFooter from "../../components/ChatFooter/ChatFooter";
import ChatNav from "../../components/ChatNav/ChatNav";
import ChatDetail from "../../components/ChatDetail/ChatDetail";

import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

import axios from "axios"
import "./Chat.scss";

interface IMessage {
  roomId : number;
  message :  string;
  memberId : number;
}

export default function Chat() {
  const client = useRef<StompJs.Client>();
  const [message, setMessage] = useState<IMessage[]>([]);
  const [isOpen, setIsOpen] = useState(false);

// 소켓객체와 connect되면 subscribe함수를 동작시켜서 서버에 있는 주소로 sub
  const subscribeChatting = async () => {
    console.log("채팅 구독");
    await client.current?.subscribe(
      `/sub/room/1`,
      // `/sub/room/${roomInfo.roomId}`,  // 이부분 나중에 실제 룸 넘버로 변경
      ({ body }) => {
        console.log(body);
        setMessage((prev) => [...prev, JSON.parse(body)]);
      }
    );
  };

  // 현재는 app컴포넌트 생성과 동시에 소켓 객체가 연결이 되고 sub로 구독함!
  // 이 코드를 방에 들어갈때 연결하면 됨!
  const connect = async () => {
    client.current = new StompJs.Client({
      webSocketFactory: () => new SockJS("https://k8b204.p.ssafy.io/api/ws-stomp"),
      // new SockJS('https://i8b202.p.ssafy.io/api/ws-stomp'), // proxy를 통한 접속
      connectHeaders: {},
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        console.log("커넥트 되는 시점");
        subscribeChatting();
      },
      onStompError: (frame) => {
        console.error(frame);
      },
    });
    console.log("connect");
    await stompActive();
  };

  const stompActive = async () => {
    if(client.current !== undefined) await client.current.activate();
  };

  // 웹 소켓 끊기.
  const disconnect = () => {
    console.log("disconnect");
    if(client.current !== undefined) client.current.deactivate();
  };

  // 방 시작 후 웹 소켓 연결
  useEffect(() => {
    console.log("방 처음");
    connect();
    return () => disconnect();
  }, []);

  const publishMessage = async (message: string) => {
    console.log("채팅을 입력해서 pub이벤트 발생!");
    console.log(message);
    if (!client.current?.connected) {
      return;
    }
    await client.current.publish({
      destination: "/pub/message",
      body: JSON.stringify({
        roomId: 1,
        message,
        memberId: 1,
        // 밑에 코드로 실제 값들을 넘겨줘야함
        // roomId: roomInfo.roomId,
        // message,
        // memberId: userInfo.memberId,
      }),
    });
  }
  // 채팅에 관한 pub이벤트가 발생하는 시점은 채팅을 입력했을때!!
  // const submitMessage = (e : EventListener) => {
  //   console.log("submitMessage");
  //   if (e.keyCode === 13) {
  //     e.preventDefault();

  //     publishMessage(e.target.value);

  //     e.target.value = "";
  //   }
  // };

  const handleOpenDetail = useCallback(() => {
    setIsOpen(true);
  }, [isOpen]);

  const handleCloseDetail = useCallback(() => {
    setIsOpen(false);
  }, [isOpen]);

  return (
    <div className="chatting-room">
      <div className="navbar">
        <ChatNav onOpen={handleOpenDetail} />
      </div>
      <div className="content">
        <ChatContent />
      </div>
      <div className="footer">
        <ChatFooter />
      </div>
      <ChatDetail isOpen={isOpen} onClose={handleCloseDetail} />
    </div>
  );
}