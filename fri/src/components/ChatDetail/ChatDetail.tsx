import Close from "../../assets/Close.png";
import Egg from "../../assets/egg_fri.png";
import "../../pages/Chat/Chat.scss";

export default function ChatDetail({ isOpen, onClose }: any) {
  const handleCloseModal = (event: React.MouseEvent<HTMLDivElement>) => {
    if (event.target === event.currentTarget) {
      onClose();
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
              <div className="name">이름</div>
            </div>
          </div>
        </div>
        <div className="exit">
          <button>채팅방 나가기</button>
        </div>
      </div>
    </div>
  );
}
