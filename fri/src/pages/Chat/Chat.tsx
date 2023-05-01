import { useState, useCallback } from "react";
import ChatContent from "../../components/ChatContent/ChatContent";
import ChatFooter from "../../components/ChatFooter/ChatFooter";
import ChatNav from "../../components/ChatNav/ChatNav";
import ChatDetail from "../../components/ChatDetail/ChatDetail";

import "./Chat.scss";

export default function Chat() {
  const [isOpen, setIsOpen] = useState(false);

  const handleOpenDetail = useCallback(() => {
    setIsOpen(true);
  }, [isOpen]);

  const handleCloseDetail = useCallback(() => {
    setIsOpen(false);
  }, [isOpen]);

  console.log(isOpen);

  return (
    <div className="chatting-room">
      <div className="navbar">
        <ChatNav onOpen={handleOpenDetail} />
      </div>
      <div className="content">
        <ChatContent />
      </div>
      <div className="footer">
        <ChatFooter />
      </div>
      <ChatDetail isOpen={isOpen} onClose={handleCloseDetail} />
    </div>
  );
}
