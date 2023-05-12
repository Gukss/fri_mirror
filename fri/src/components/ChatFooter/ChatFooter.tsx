import { useState, useCallback, useRef } from "react";
import "./ChatFooter.scss";
import Image from "../../assets/Add photo alternate.png";
import Up from "../../assets/Arrow upward.png";
import { IMessage } from "../../pages/Chat/Chat";

interface msgType {
  msg: IMessage[];
  e: string;
}

export default function ChatFooter({ msg, e }: msgType) {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const btnRef = useRef<HTMLButtonElement>(null);
  const [roomId, message, memberId, profile] = msg;
  const [text, setText] = useState<string>("");

  const handleChange = useCallback(
    (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setText(e.target.value);

      const textarea = textareaRef.current;
      if (textarea) {
        textarea.classList.remove("autoHeight");
        if (textarea.scrollHeight > 40) {
          textarea.classList.add("autoHeight");
        }
      }
    },
    [text]
  );

  return (
    <div className="chat-footer">
      {/* <div className="gallery">
        <img src={Image} alt="gallery" />
      </div> */}
      <div className="text-input">
        <textarea ref={textareaRef} onChange={handleChange}>
          {text}
        </textarea>
        <button className="send-message">
          <img src={Up} alt="up-arrow" />
        </button>
      </div>
    </div>
  );
}
