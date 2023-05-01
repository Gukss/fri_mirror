import { useNavigate } from "react-router-dom";
import back from "../../assets/Arrow back ios.png";
import menu from "../../assets/Menu.png";

import "./ChatNav.scss";

export default function ChatNav() {
  const navigate = useNavigate();

  return (
    <div className="chat-nav">
      <div className="nav-back">
        <img src={back} alt="arrow-back" />
      </div>
      <div>로고 or 방제목</div>
      <div className="nav-menu">
        <img src={menu} alt="menu" />
      </div>
    </div>
  );
}
