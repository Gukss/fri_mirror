import "./ChatContent.scss";
// import Egg from "../../assets/egg_fri.png";

interface msgType {
  msg: string;
  anonymousProfileImageUrl: string;
  time: string;
  nickname: string;
  bottomRef: React.RefObject<HTMLDivElement>;
}

export default function Others({
  msg,
  anonymousProfileImageUrl,
  time,
  nickname,
  bottomRef
}: msgType) {
  const dateString = time;
  const date = new Date(dateString);
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  const times = date.toLocaleTimeString().slice(0, -3);
  const urlRegex = /(https?:\/\/[^\s]+)/g;

  const checkUrl = (text: string) => {
    if (text.match(urlRegex)) {
      const textWithLinks = text.replace(urlRegex, (url) => {
        return `${'<a href="' + url + '" target="_blank">' + url + '</a>'}`;
      });
      return <div className="message mine" dangerouslySetInnerHTML={{ __html: textWithLinks }}></div>;
    } else {
      return <div className="message mine">{text}</div>;
    }
  };

  return (
    <div className="chat-content" ref={bottomRef}>
      <div className="profile">
        <img src={anonymousProfileImageUrl} alt="profile" />
      </div>
      <div className="chat-box">
        <div className="name">{nickname}</div>
        <div className="message-box">
          {checkUrl(msg)}
          <span className="time">
            {" "}
            {`${month}/${day}`}
            <br />
            {times}
          </span>
        </div>
      </div>
    </div>
  );
}
