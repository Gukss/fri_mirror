import { useEffect, useRef, useState, useCallback } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import ChatContent from "../../components/ChatContent/ChatContent";
// import Image from "../../assets/Add photo alternate.png";
import Up from "../../assets/Arrow upward.png";
import ChatNav from "../../components/ChatNav/ChatNav";
import ChatDetail from "../../components/ChatDetail/ChatDetail";
import Egg from "../../assets/egg_fri.png";
import lgm from "../../assets/lgm.png";
import mgl from "../../assets/mgl.png";

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
  createdAt: string;
  nickname: string;
  userId: number;
  time: string;
};

interface Roomdetail {
  title: string;
  location: string;
  isParticipate: boolean;
  majors: { name: string; url: string }[];
  nonMajors: { name: string; url: string }[];
}

export type HandleOutChatType = () => void;

export default function Chat() {
  const client = useRef<StompJs.Client>();
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const btnRef = useRef<HTMLButtonElement>(null);
  const contentRef = useRef<HTMLDivElement>(null);
  const [message, setMessage] = useState<IMessage[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const [text, setText] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(true);

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
    setLoading(false);
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

  const outChatMsg:HandleOutChatType = () => {
    if(client.current === undefined) return;
    client.current.publish({
      destination: "/pub/chatting",
      body: JSON.stringify({
        roomId: roomId,
        message: `${nick}님이 나갔습니다.`,
        memberId: -1,
        anonymousProfileImageUrl: "",
        time: "",
        nickname: nick
      })
  })
  }
  const pubInit = () => {
    if(client.current === undefined) return;
    const info = {
      roomId: roomId,
      message: `${nick}님이 입장했습니다.`,
      memberId: -1,
      anonymousProfileImageUrl: "",
      time: "",
      nickname: nick
    }
    if(!(JSON.stringify(info) in message))
    {
      client.current.publish({
      destination: "/pub/chatting",
      body: JSON.stringify(info)
  })}}

  // 방 시작 후 웹 소켓 연결
  useEffect(() => {
    const connect = async () => {
      setLoading(true);
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
            if(isuser === "false") pubInit();
          },
          debug: () => {
            null;
          },
          onStompError: (frame) => {
            console.error();
          }
        });
        await stompActive();
      } catch (e) {
        console.log();
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
      console.log();
    }
  };

  const publishMessage = async () => {
    const now = new Date();
    const options = {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
      hour12: false
    };
    const formattedTime = now.toLocaleString("en-US");
    // console.log(formattedTime); // 예시 출력: "2023-05-10 16:23:46" (로케일 및 시간대에 따라 다를 수 있음)
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
        anonymousProfileImageUrl: image,
        time: formattedTime,
        nickname: nick
      })
    });

    setText("");
  };

  useEffect(() => {
    const getChat = async () => {
      const header = {
        "Content-Type": "application/json",
        Authorization: userId
      };
      try {
        const res = await axios.get(api_url + `chatting/${roomId}`, {headers: header});
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
          "Authorization": userId
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
        console.log();
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

  // ====================================================

  if (loading) {
    return (
      <div className="chatting-room">
        <div className="chat-loading">
          <div className="wave">
            <div className="ball">
              <img src={Egg} alt="egg" />
            </div>
            <div className="ball">
              <img src={lgm} alt="egg" />
            </div>
            <div className="ball">
              <img src={Egg} alt="egg" />
            </div>
            <div className="ball">
              <img src={mgl} alt="egg" />
            </div>
            <div className="ball">
              <img src={Egg} alt="egg" />
            </div>
          </div>
          <div className="wave load">
            <div className="ball">로</div>
            <div className="ball">딩</div>
            <div className="ball">중</div>
            <div className="ball">.</div>
            <div className="ball">.</div>
            <div className="ball">.</div>
          </div>
        </div>
      </div>
    );
  } else {
    return (
      <div className="chatting-room">
        <div className="navbar">
          <ChatNav onOpen={handleOpenDetail} />
        </div>
        <div className="content">
          <ChatContent msg={message} contentRef={contentRef} />
        </div>
        <div className="footer">
          <div className="chat-footer">
            {/* <div className="gallery">
              <img src={Image} alt="gallery" />
            </div> */}
            <div className="text-input">
              <textarea
                ref={textareaRef}
                onChange={submitMessage}
                value={text}
              />
              {text !== "" ? (
                <button
                  className="send-message"
                  ref={btnRef}
                  onClick={publishMessage}
                >
                  <img src={Up} alt="up-arrow" />
                </button>
              ) : (
                ""
              )}
            </div>
          </div>
        </div>
        {isOpen ? <ChatDetail isOpen={isOpen} onClose={handleCloseDetail} data={data} outChatMsg={outChatMsg} /> : null}
      </div>
    );
  }
}
