import { useNavigate, Link } from "react-router-dom";
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
    <Link className="onboarding" onClick={handleClick} to="/login">
      <img
        id="gif"
        src="/assets/gif/tingtingting.gif"
        alt="ting"
        className="onboarding-img"
      />
      <div className="onboarding-text">아무 곳이나 터치해 주세요.</div>
    </Link>
  );
}
