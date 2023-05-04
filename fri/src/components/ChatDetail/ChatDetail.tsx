import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import { meeting } from "../../redux/user";
import { useNavigate } from "react-router-dom";
import Close from "../../assets/back.png";
import Egg from "../../assets/egg_fri.png";
import axios from  "axios";
import "../../pages/Chat/Chat.scss";

export default function ChatDetail({ isOpen, onClose }: any) {
  const handleCloseModal = (event: React.MouseEvent<HTMLDivElement>) => {
    if (event.target === event.currentTarget) {
      onClose();
    }
  };

  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  })
  const roomId = useSelector((state: RootState) => {
    return state.strr.roomId;
  })
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const api_url = process.env.REACT_APP_REST_API;

  const outChat = async () => {
    const header = {
      "Content-Type" : "application/json",
      "Authorization" : userId
    }
    try {
			const res = await axios.patch(api_url + `user/room/${roomId}`, {"isParticipate" : true}, {headers : header})
			dispatch(meeting("참여한 방이 없습니다"))
			navigate("/main");
		}
		catch(e){console.log(e)}
  }

  return (
    <div
      className={`chat-detail ${isOpen ? "open" : ""}`}
      onClick={handleCloseModal}
    >
      <div className={`chat-modal ${isOpen ? "open" : ""}`}>
        <div className="close-btn" onClick={onClose}>
          <img src={Close} alt="close-btn" />
        </div>
        <div className="info">
          <div className="top">방제목</div>
          <div>궁동에서 술마실 사람?</div>
        </div>
        <div className="info">
          <div className="top">장소</div>
          <div>궁동</div>
        </div>
        <div className="info last">
          <div className="top">참여자</div>
          <div className="people">
            <div className="profile">
              <div className="img">
                <img src={Egg} alt="profile" />
              </div>
              <div className="name">김은영</div>
            </div>
            <div className="profile">
              <div className="img">
                <img src={Egg} alt="profile" />
              </div>
              <div className="name">김경아</div>
            </div>
            <div className="profile">
              <div className="img">
                <img src={Egg} alt="profile" />
              </div>
              <div className="name">김동국</div>
            </div>
            <div className="profile">
              <div className="img">
                <img src={Egg} alt="profile" />
              </div>
              <div className="name">김영만</div>
            </div>
            <div className="profile">
              <div className="img">
                <img src={Egg} alt="profile" />
              </div>
              <div className="name">배성현</div>
            </div>
            <div className="profile">
              <div className="img">
                <img src={Egg} alt="profile" />
              </div>
              <div className="name">허정범</div>
            </div>
            <div className="profile">
              <div className="img">
                <img src={Egg} alt="profile" />
              </div>
              <div className="name">누구인가?</div>
            </div>
          </div>
        </div>
        <div className="exit">
          <button onClick={outChat}>채팅방 나가기</button>
        </div>
      </div>
    </div>
  );
}
