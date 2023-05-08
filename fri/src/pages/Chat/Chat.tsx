import { useEffect, useRef, useState, useCallback } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import ChatContent from "../../components/ChatContent/ChatContent";
import Image from "../../assets/Add photo alternate.png";
import Up from "../../assets/Arrow upward.png";
import ChatNav from "../../components/ChatNav/ChatNav";
import ChatDetail from "../../components/ChatDetail/ChatDetail";

import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

import axios from "axios";
import "./Chat.scss";
import { useLocation } from "react-router-dom";

export type IMessage = {
  roomId: string;
  message: string;
  memberId: number;
  anonymousProfileImageUrl: string;
  times: string;
  nickname: string;
  userId: number;
};

interface Roomdetail {
  title: string;
  location: string;
  isParticipate: boolean;
  majors: { name: string; url: string }[];
  nonMajors: { name: string; url: string }[];
}

export default function Chat() {
  const client = useRef<StompJs.Client>();
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const btnRef = useRef<HTMLButtonElement>(null);
  const [message, setMessage] = useState<IMessage[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const [text, setText] = useState<string>("");

  const [data, setData] = useState<Roomdetail>({
    title: "",
    location: "",
    isParticipate: false,
    majors: [],
    nonMajors: []
  });

  const api_url = process.env.REACT_APP_REST_API;
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const isuser = queryParams.get("isuser");
  const roomId = useSelector((state: RootState) => {
    return state.strr.roomId;
  });
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  const nick = useSelector((state: RootState) => {
    return state.strr.nickname;
  });
  const image = useSelector((state: RootState) => {
    return state.strr.anonymousProfileImageUrl;
  });

  // 소켓객체와 connect되면 subscribe함수를 동작시켜서 서버에 있는 주소로 sub
  const subscribeChatting = () => {
    client.current?.subscribe(`/sub/room/${roomId}`, ({ body }) => {
      setMessage((prev) => [...prev, JSON.parse(body)]);
    });
  };

  // 현재는 app컴포넌트 생성과 동시에 소켓 객체가 연결이 되고 sub로 구독함!
  // 이 코드를 방에 들어갈때 연결하면 됨!
  const stompActive = () => {
    if (client.current !== undefined) client.current.activate();
  };

  // 웹 소켓 끊기.
  const disconnect = () => {
    if (client.current !== undefined) client.current.deactivate();
  };

  // 방 시작 후 웹 소켓 연결
  useEffect(() => {
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
          },
          debug: () => {
            null;
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
    connect();
    return () => disconnect();
  }, []);

  const pushMsg = async () => {
    const data = {
      roomId: roomId,
      message: text
    };
    const header = {
      "Content-Type": "application/json",
      Authorization: userId
    };

    try {
      await axios.post(api_url + "chatting", data, { headers: header });
    } catch (e) {
      console.log(e);
    }
  };

  const publishMessage = async () => {
    const now = new Date();
    let h = String(now.getHours());
    const m = String(now.getMinutes());
    let day = "오전";
    if (13 <= Number(h) && Number(h) <= 24) {
      h = String(Number(h) - 12);
      day = "오후";
    }
    if (!client.current?.connected) {
      return;
    }
    pushMsg();
    client.current.publish({
      destination: "/pub/chatting",
      body: JSON.stringify({
        roomId: roomId,
        message: text,
        memberId: userId,
        anonymousProfileImageId: image,
        times: `${day} ${h}:${m}`,
        nick: nick
      })
    });
  };

  useEffect(() => {
    const getChat = async () => {
      try {
        const res = await axios.get(api_url + `chatting/${roomId}`);
        setMessage(res.data);
      } catch (e) {
        console.log(e);
      }
    };
    if (isuser === "true") getChat();
  }, []);

  useEffect(() => {
    const getDetail = async () => {
      try {
        const header = {
          "Content-Type": "application/json",
          Authorization: userId
        };
        const res = await axios.get(api_url + "room/" + roomId, {
          headers: header
        });
        const title = res.data.title;
        const location = res.data.location;
        const isParticipate = res.data.isParticipate;
        const majors = res.data.major;
        const nonMajors = res.data.nonMajor;
        setData({
          ...data,
          title: title,
          location: location,
          isParticipate: isParticipate,
          majors: majors,
          nonMajors: nonMajors
        });
      } catch (e) {
        console.log(e);
      }
    };
    getDetail();
  }, []);

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
            <button
              className="send-message"
              ref={btnRef}
              onClick={publishMessage}
            >
              <img src={Up} alt="up-arrow" />
            </button>
          </div>
        </div>
      </div>
      <ChatDetail isOpen={isOpen} onClose={handleCloseDetail} data={data} />
    </div>
  );
}
