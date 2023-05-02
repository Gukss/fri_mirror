import "./ChatContent.scss";
import Egg from "../../assets/egg_fri.png";

interface msgType{
  msg : string;
  profile : string;
  time : string;
  nick : string;
}

export default function Others({msg, profile, time, nick} : msgType) {
   
  return (
    <div className="chat-content">
      <div className="profile">
        <img src={Egg} alt="profile" />
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
