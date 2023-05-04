import "./ChatContent.scss";

interface msgType {
  msg : string;
  time : string;
}
export default function Mine({msg, time} : msgType) {

  return (
    <div className="chat-content">
      <div className="chat-box">
        <div className="message-box mine">
          <span className="time">{time}</span>
          <div className="message mine">
            {msg}
          </div>
        </div>
      </div>
    </div>
  );
}
