import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Back from "../../components/Back"
import lgm from "../../assets/lgm.png"
import mgl from "../../assets/mgl.png"
import egg from "../../assets/egg_fri.png"
import "./game.scss"

function GameWaiting() {
  const navigate = useNavigate();
  const [isLgm, setIsLgm] = useState(true);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setIsLgm((prev) => !prev);
    }, 500);

    return () => clearInterval(intervalId);
  }, []);

  return (
    <div className="wait_game">
      <Back />
      <div className="top">커피 내기</div>
      <div>
        {
            isLgm ? <img src={lgm} alt="lgm" className="wait_lgm" /> : <img src={mgl} alt="mgl" className="wait_lgm" />
          }
        {
            isLgm ? <img src={mgl} alt="mgl" className="wait_lgm" /> : <img src={lgm} alt="lgm" className="wait_lgm" />
          }
        {
            isLgm ? <img src={lgm} alt="lgm" className="wait_lgm" /> : <img src={mgl} alt="mgl" className="wait_lgm" />
          }
        {
            isLgm ? <img src={mgl} alt="mgl" className="wait_lgm" /> : <img src={lgm} alt="lgm" className="wait_lgm" />
          }
      </div>
      <div className="info">
        {/* 현재 접속인원 / api 방 최대 인원 */}
        <div>2 / 5</div>
        <div>참여 대기중...</div>
      </div>
      <div className="player-list">
        {/* 현재 참가중인 인원들 리스트 받아서 돌리기 */}
        <div className="player">
          <div className="player-profile">
            <img
              src={egg}
              alt="player-profile"
              className="profile-img"
            />
          </div>
          <div className="player-name">나</div>
        </div>
        <div className="player">
          <div className="player-profile">
            <img
              src={egg}
              alt="player-profile"
              className="profile-img"
            />
          </div>
          <div className="player-name">임슨이</div>
        </div>
      </div>
      {/* 참가하기 안눌렀을 때는 */}
      <button className="ready-btn" onClick={()=>navigate("/game/1")}>준비하기</button>
      {/* 참가하기 눌렀을 때는 */}
      <div className="ready">다른 플레이어 기다리는 중...</div>
    </div>
  );
}
export default GameWaiting;
