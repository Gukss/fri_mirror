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
      {
        msg.map((msg, index) => (
          msg.memberId === "true" ?
          <Mine key={index} msg={msg.message} time={msg.time} />
          :
          <Others key={index} msg={msg.message} profile={msg.profile} time={msg.time} nick={msg.memberId}/>
        ))
      }
    </div>
  );
}
