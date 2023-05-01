import { useState, useCallback, useRef } from "react";
import "./ChatFooter.scss";
import Image from "../../assets/Add photo alternate.png";
import Up from "../../assets/Arrow upward.png";

export default function ChatFooter() {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const btnRef = useRef<HTMLButtonElement>(null);

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
      <div className="gallery">
        <img src={Image} alt="gallery" />
      </div>
      <div className="text-input">
        <textarea ref={textareaRef} onChange={handleChange} />
        <button className="send-message">
          <img src={Up} alt="up-arrow" />
        </button>
      </div>
    </div>
  );
}
