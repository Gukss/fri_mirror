import { useEffect, useRef, useState, useCallback } from "react";
import ChatContent from "../../components/ChatContent/ChatContent";
// import ChatFooter from "../../components/ChatFooter/ChatFooter";
import Image from "../../assets/Add photo alternate.png";
import Up from "../../assets/Arrow upward.png";
import ChatNav from "../../components/ChatNav/ChatNav";
import ChatDetail from "../../components/ChatDetail/ChatDetail";

import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

import axios from "axios"
import "./Chat.scss";

export type IMessage = {
  roomId : number;
  message :  string;
  memberId : string;
  profile : string;
}

export default function Chat() {
  const client = useRef<StompJs.Client>();
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const btnRef = useRef<HTMLButtonElement>(null);
  const [message, setMessage] = useState<IMessage[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const [text, setText] = useState<string>("");

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

  const publishMessage = async (msg: string) => {
    console.log("채팅을 입력해서 pub이벤트 발생!");
    console.log(msg);
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
    const data = {
      roomId : 1, // roomId
      message :  msg,
      memberId : "true",
      profile : "string"
    }
    setMessage([data, ...message])  
  }

  function sendMessage(){
    publishMessage(text);    
  }

  console.log(message)
  // 채팅에 관한 pub이벤트가 발생하는 시점은 채팅을 입력했을때!!
  const submitMessage = useCallback(
    (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setText(e.target.value);
      
      const textarea = textareaRef.current;
      if (textarea) {
        textarea.classList.remove("autoHeight");
        if (textarea.scrollHeight > 40) {
          textarea.classList.add("autoHeight");
        }
      }
    },
    [text]
  );

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
        <ChatContent msg={message} />
      </div>
      <div className="footer">
      <div className="chat-footer">
      <div className="gallery">
        <img src={Image} alt="gallery" />
      </div>
      <div className="text-input">
        <textarea ref={textareaRef} onChange={submitMessage} />
        <button className="send-message" ref={btnRef} onClick={sendMessage}>
          <img src={Up} alt="up-arrow" />
        </button>
      </div>
    </div>
        {/* <ChatFooter msg={message} e={e}/> */}
      </div>
      <ChatDetail isOpen={isOpen} onClose={sendMessage} />
    </div>
  );
}