import "./ChatContent.scss";
// import Egg from "../../assets/egg_fri.png";

interface msgType{
  msg : string;
  anonymousProfileImageId : string;
  time : string;
  nick : string;
}

export default function Others({msg, anonymousProfileImageId, time, nick} : msgType) {
   
  return (
    <div className="chat-content">
      <div className="profile">
        <img src="anonymousProfileImageId" alt="profile" />
      </div>
      <div className="chat-box">
        <div className="name">{nick}</div>
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
