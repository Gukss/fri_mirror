import "./ChatContent.scss";

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

  return (
    <div className="chat-content" ref={bottomRef}>
      <div className="chat-box">
        <div className="message-box mine">
          <span className="time">
            {`${month}/${day}`}
            <br />
            {times}
          </span>
          <div className="message mine">{msg}</div>
        </div>
      </div>
    </div>
  );
}
