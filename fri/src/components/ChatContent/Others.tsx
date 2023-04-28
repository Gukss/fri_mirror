import "./ChatContent.scss";

import Egg from "../../assets/egg_fri.png";

export default function Others() {
  return (
    <div className="chat-content">
      <div className="profile">
        <img src={Egg} alt="profile" />
      </div>
      <div className="chat-box">
        <div className="name">배성현</div>
        <div className="message-box">
          <div className="message">
            메세지 보내기 메세지 보내기 메세지 보내기 메세지 보내기메세지
            보내기메세지 보내기메세지 보내기메세지 보내기
          </div>
          <span className="time">오전 10:48</span>
        </div>
      </div>
    </div>
  );
}
