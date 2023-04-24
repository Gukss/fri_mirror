import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

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
      <p onClick={() => navigate("/main")}>&#60;</p>
      <div className="top">커피 내기</div>
      <div>
        <img
          src={`/assets/${isLgm ? "lgm" : "mgl"}.png`}
          alt={isLgm ? "lgm" : "mgl"}
          className="wait_lgm"
        />
        <img
          src={`/assets/${!isLgm ? "lgm" : "mgl"}.png`}
          alt={!isLgm ? "lgm" : "mgl"}
          className="wait_lgm"
        />
        <img
          src={`/assets/${isLgm ? "lgm" : "mgl"}.png`}
          alt={isLgm ? "lgm" : "mgl"}
          className="wait_lgm"
        />
        <img
          src={`/assets/${!isLgm ? "lgm" : "mgl"}.png`}
          alt={!isLgm ? "lgm" : "mgl"}
          className="wait_lgm"
        />
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
              src="/assets/egg_fri.png"
              alt="player-profile"
              className="profile-img"
            />
          </div>
          <div className="player-name">나</div>
        </div>
        <div className="player">
          <div className="player-profile">
            <img
              src="/assets/egg_fri.png"
              alt="player-profile"
              className="profile-img"
            />
          </div>
          <div className="player-name">임슨이</div>
        </div>
      </div>
      {/* 참가하기 안눌렀을 때는 */}
      <button className="ready-btn">준비하기</button>
      {/* 참가하기 눌렀을 때는 */}
      <div className="ready">다른 플레이어 기다리는 중...</div>
    </div>
  );
}
export default GameWaiting;
