import "./ChatContent.scss";
// import Egg from "../../assets/egg_fri.png";

interface msgType{
  msg : string;
  anonymousProfileImageUrl : string;
  time : string;
  nickname : string;
}

export default function Others({msg, anonymousProfileImageUrl, time, nickname} : msgType) {
  return (
    <div className="chat-content">
      <div className="profile">
        <img src={anonymousProfileImageUrl} alt="profile" />
      </div>
      <div className="chat-box">
        <div className="name">{nickname}</div>
        <div className="message-box">
          <div className="message">
            {msg}
          </div>
          <span className="time">{time}</span>
        </div>
      </div>
    </div>
  );
}
