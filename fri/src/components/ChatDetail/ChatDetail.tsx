import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import { meeting } from "../../redux/user";
import { useNavigate } from "react-router-dom";
import Close from "../../assets/x_btn.png";
import { HandleOutChatType } from "../../pages/Chat/Chat";
import axios from "axios";
import "../../pages/Chat/Chat.scss";
import { useEffect, useState } from "react";

interface ChildProps {
  outChatMsg: HandleOutChatType;
}

interface Roomdetail {
  roomId : number;
  title : string;
  location : string;
  roomCategory: string;
  headCount : number;
  isParticipate : boolean;
  participate: boolean;
  major: { name: string; url: string }[];
  nonMajor: { name: string; url: string }[];
}

export default function ChatDetail({ isOpen, onClose, outChatMsg }: any) {
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
  const [data, setData] = useState<Roomdetail>();
  const api_url = process.env.REACT_APP_REST_API;

  const out = () => {
    outChatMsg();
  };

  const outChat = async () => {
    out();
    const header = {
      "Content-Type": "application/json",
      Authorization: userId
    };
    try {
      await axios.patch(
        api_url + `user/room/${roomId}`,
        { participate: true },
        { headers: header }
      );
      dispatch(meeting("참여한 방이 없습니다."));
      navigate("/main");
    } catch (e) {
      console.log();
    }
  };

  useEffect(() => {
    const getData = async () => {
      try {
        const header = {
          "Content-Type": "application/json",
          Authorization: userId
        };
        const res = await axios.get(api_url + "room/" + roomId, {
          headers: header
        });
        setData(res.data);
      } catch (e) {
        console.log();
      }
    }
    getData()
  }, [])

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
          <div className="detail_info">{data?.title}</div>
        </div>
        <div className="info">
          <div className="top">장소</div>
          <div className="detail_info">{data?.location}</div>
        </div>
        <div className="info last">
          <div className="participate-box">
            <div className="top">참여자</div>
            <div className="sub">전공자</div>
            <div className="people">
              {data?.major.length === 0 ? (
                <div className="none">아직 전공자가 없어요</div>
              ) : (
                data?.major.map((person: any) => (
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
              {data?.nonMajor.length === 0 ? (
                <div className="none">비전공자가 없어요</div>
              ) : (
                data?.nonMajor.map((person: any) => (
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
