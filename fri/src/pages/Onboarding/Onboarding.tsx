import { useNavigate } from "react-router-dom";
import gif from "../../assets/gif/tingtingting.gif";
import "./Onboarding.scss";

export default function Onboarding() {
  const navigate = useNavigate();

  const handleClick = () => {
    const img = document.getElementById("gif");
    if (img) {
      img.classList.add("hidden");
      setTimeout(() => {
        navigate("/login");
      }, 1000); // 0.5초 후 이동
    }
  };

  return (
    <div className="onboarding" onClick={handleClick}>
      <img id="gif" src={gif} alt="ting" className="onboarding-img" />
      <div className="onboarding-text"> 터치해 주세요.</div>
    </div>
  );
}
