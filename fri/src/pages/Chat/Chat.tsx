import ChatContent from "../../components/ChatContent/ChatContent";
import ChatFooter from "../../components/ChatFooter/ChatFooter";
import ChatNav from "../../components/ChatNav/ChatNav";
import "./Chat.scss";

export default function Chat() {
  return (
    <div className="chatting-room">
      <div className="navbar">
        <ChatNav />
      </div>
      <div className="content">
        <ChatContent />
      </div>
      <div className="footer">
        <ChatFooter />
      </div>
    </div>
  );
}
