import "./ChatContent.scss";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import Mine from "./Mine";
import Others from "./Others";
import { IMessage } from  '../../pages/Chat/Chat'

interface msgType {
  msg : IMessage[];
}

export default function ChatContent({msg} : msgType) {
  
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  return (
    <div className="chat-contents">
      {
        msg.map((msg, index) => (
          msg.memberId === userId ?
          <Mine key={index} msg={msg.message} time={msg.times} />
          :
          <Others key={index} msg={msg.message} anonymousProfileImageId={msg.anonymousProfileImageId} time={msg.times} nick={msg.nick}/>
        ))
      }
    </div>
  );
}
