import { useNavigate } from "react-router-dom";
import back from "../../assets/back.png";
import menu from "../../assets/Menu.png";

import "./ChatNav.scss";

export default function ChatNav({ onOpen }: any) {
  const navigate = useNavigate();

  return (
    <div className="chat-nav">
      <div className="nav-back">
        <img
          src={back}
          alt="arrow-back"
          onClick={() => {
            navigate(-1);
          }}
        />
      </div>
      <div>로고 or 방제목</div>
      <div className="nav-menu" onClick={onOpen}>
        <img src={menu} alt="menu" />
      </div>
    </div>
  );
}
