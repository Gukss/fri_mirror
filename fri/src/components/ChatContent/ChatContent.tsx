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
  
  const user = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  return (
    <div className="chat-contents">
      {
        msg.map((msg, index) => (
          Number(msg.memberId || msg.userId) === Number(user) ?
          <Mine key={index} msg={msg.message} time={msg.times} />
          :
          <Others key={index} msg={msg.message} anonymousProfileImageUrl={msg.anonymousProfileImageUrl} time={msg.times} nickname={msg.nickname}/>
        ))
      }
    </div>
  );
}
