import "./ChatContent.scss";
import Mine from "./Mine";
import Others from "./Others";

export default function ChatContent() {
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
