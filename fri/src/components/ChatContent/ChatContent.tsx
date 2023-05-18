import { useEffect, useRef } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import Mine from "./Mine";
import Others from "./Others";
import { IMessage } from "../../pages/Chat/Chat";
import "./ChatContent.scss";

interface msgType {
  msg: IMessage[];
  contentRef: React.RefObject<HTMLDivElement>;
}

export default function ChatContent({ msg, contentRef }: msgType) {
  const user = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  const bottomRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (bottomRef.current) {
      bottomRef.current.scrollIntoView();
    }
  }, [msg]);

  return (
    <div className="chat-contents" ref={contentRef}>
      <div className="chat-alert">
        이곳은 채팅방입니다.
        <br /> 자유롭게 이야기 하시면 됩니다.
      </div>
      {msg.map((msg, index) =>
        Number(msg.memberId || msg.userId) === Number(user) ? (
          <Mine
            key={index}
            msg={msg.message}
            time={msg.createdAt || msg.time}
            bottomRef={bottomRef}
          />
        ) : Number(msg.memberId || msg.userId) === -1 ? (
          <div className="new-face" key={index}>
            {msg.message}
          </div>
        ) : (
          <Others
            key={index}
            msg={msg.message}
            anonymousProfileImageUrl={msg.anonymousProfileImageUrl}
            time={msg.createdAt || msg.time}
            nickname={msg.nickname}
            bottomRef={bottomRef}
          />
        )
      )}
    </div>
  );
}
