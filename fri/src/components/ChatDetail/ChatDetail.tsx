import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import { meeting } from "../../redux/user";
import { useNavigate } from "react-router-dom";
import Close from "../../assets/x_btn.png";
import Egg from "../../assets/egg_fri.png";
import axios from "axios";
import "../../pages/Chat/Chat.scss";

export default function ChatDetail({ isOpen, onClose, data }: any) {
  const handleCloseModal = (event: React.MouseEvent<HTMLDivElement>) => {
    if (event.target === event.currentTarget) {
      onClose();
    }
  };

  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  const roomId = useSelector((state: RootState) => {
    return state.strr.roomId;
  });
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const api_url = process.env.REACT_APP_REST_API;

  const outChat = async () => {
    const header = {
      "Content-Type": "application/json",
      Authorization: userId
    };
    try {
      const res = await axios.patch(
        api_url + `user/room/${roomId}`,
        { participate: true },
        { headers: header }
      );
      dispatch(meeting("참여한 방이 없습니다."));
      navigate("/main");
    } catch (e) {
      console.log(e);
    }
  };

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
          <div>{data.title}</div>
        </div>
        <div className="info">
          <div className="top">장소</div>
          <div>{data.location}</div>
        </div>
        <div className="info last">
          <div className="participate-box">
            <div className="top">참여자</div>
            <div className="sub">전공자</div>
            <div className="people">
              {data.majors.length === 0 ? (
                <div className="none">아직 전공자가 없어요 :(</div>
              ) : (
                data.majors.map((person: any) => (
                  <div className="profile" key={person.name}>
                    <div className="img">
                      <img
                        src={person.anonymousProfileImageUrl}
                        alt="profile"
                      />
                    </div>
                    <div className="name">{person.name}</div>
                  </div>
                ))
              )}
            </div>
            <div className="sub">비전공자</div>
            <div className="people">
              {data.nonMajors.length === 0 ? (
                <div className="none">비전공자가 없어요 :(</div>
              ) : (
                data.nonMajors.map((person: any) => (
                  <div className="profile" key={person.name}>
                    <div className="img">
                      <img
                        src={person.anonymousProfileImageUrl}
                        alt="profile"
                      />
                    </div>
                    <div className="name">{person.name}</div>
                  </div>
                ))
              )}
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
