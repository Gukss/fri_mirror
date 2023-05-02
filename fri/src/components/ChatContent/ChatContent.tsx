import "./ChatContent.scss";
import Mine from "./Mine";
import Others from "./Others";
import { IMessage } from  '../../pages/Chat/Chat'

interface msgType {
  msg : IMessage[];
}

export default function ChatContent({msg} : msgType) {
  
  const [roomId, message, memberId, profile] = msg;
  
  return (
    <div className="chat-contents">
      <Others />
      <Others />
      <Mine />
      <Mine />
      <Mine />
    </div>
  );
}
