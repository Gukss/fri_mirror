import "./ChatContent.scss";
import React from "react";

interface msgType {
  msg: string;
  time: string;
  bottomRef: React.RefObject<HTMLDivElement>;
}

export default function Mine({ msg, time, bottomRef }: msgType) {
  const dateString = time;
  const date = new Date(dateString);
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  const times = date.toLocaleTimeString().slice(0, -3);
  const urlRegex = /(https?:\/\/[^\s]+)/g;

  const checkUrl = (text: string) => {
    if (text.match(urlRegex)) {
      const textWithLinks = text.replace(urlRegex, (url) => {
        return `${'<a href="' + url + '" target="_blank">' + url + "</a>"}`;
      });
      return (
        <div
          className="message mine"
          dangerouslySetInnerHTML={{ __html: textWithLinks }}
        ></div>
      );
    } else {
      return <div className="message mine">{text}</div>;
    }
  };

  return (
    <div className="chat-content" ref={bottomRef}>
      <div className="chat-box">
        <div className="message-box mine">
          <span className="time">
            {`${month}/${day}`}
            <br />
            {times}
          </span>
          {checkUrl(msg)}
        </div>
      </div>
    </div>
  );
}
