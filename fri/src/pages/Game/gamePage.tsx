import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

function game() {
  const navigate = useNavigate();
  const [seconds, setSeconds] = useState<number>(3);
  const [timer, setTimer] = useState<number>(10);
  const [modal, setModal] = useState<boolean>(true);
  const [isLgm, setIsLgm] = useState(true);
  const [imagesLoaded, setImagesLoaded] = useState<boolean>(false);
  const start = "start!";
  const intervalRef = useRef<NodeJS.Timeout>();
  const timerRef = useRef<NodeJS.Timeout>();
  const lgmRef = useRef<NodeJS.Timeout>();
  const resultRef = useRef<HTMLDivElement>(null);

  const [flip, setFlip] = useState<boolean>(false);
  const [result, setResult] = useState<boolean>(false);

  // 이미지 로딩
  useEffect(() => {
    // 이미지 로딩
    const lgmImage = new Image();
    const mglImage = new Image();
    lgmImage.onload = () => {
      mglImage.onload = () => {
        setImagesLoaded(true);
      };
      mglImage.src = "/assets/mgl.png";
    };
    lgmImage.src = "/assets/lgm.png";
  }, []);

  // 시작 3초
  useEffect(() => {
    if (imagesLoaded) {
      intervalRef.current = setInterval(() => {
        setSeconds((seconds) => seconds - 1);
      }, 1000);
    }

    return () => clearInterval(intervalRef.current);
  }, [imagesLoaded]);

  useEffect(() => {
    if (seconds === -1) {
      clearInterval(intervalRef.current);
      setModal(false);
    }
  }, [seconds]);

  // 10초타이머
  useEffect(() => {
    if (!modal) {
      timerRef.current = setInterval(() => {
        setTimer((seconds) => seconds - 0.01);
      }, 10);
    }

    return () => clearInterval(timerRef.current);
  }, [modal]);

  useEffect(() => {
    if (timer < 0.01) {
      clearInterval(timerRef.current);
      clearInterval(lgmRef.current);
      // 여기서도 보내기
      console.log("여기서 소켓으로 점수 보내기", timer.toFixed(2));
    }
  }, [timer]);

  // 금만이
  useEffect(() => {
    if (!modal) {
      lgmRef.current = setInterval(() => {
        setIsLgm((prev) => !prev);
      }, 200);
    }

    return () => clearInterval(lgmRef.current);
  }, [modal]);

  // 버튼 클릭
  const handleClick = () => {
    setFlip(true);
    clearInterval(timerRef.current);
    clearInterval(lgmRef.current);
    console.log("여기서 소켓으로 점수 보내기", timer.toFixed(2));
    setResult(true);
  };

  // 모달 바깥쪽 클릭하면
  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (
        resultRef.current &&
        !resultRef.current.contains(event.target as Node)
      ) {
        navigate("/main");
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [resultRef]);

  // 소켓에서 뭐 주면 결과 모달 띄우기

  return (
    <>
      {modal ? (
        <div className="game_3s_back">
          {seconds ? (
            <div>{seconds}</div>
          ) : (
            <div style={{ fontSize: "100px" }}>{start}</div>
          )}
        </div>
      ) : null}
      {result ? (
        <div className="game-result-back">
          <div className="game-result" ref={resultRef}>
            <div className="header">결과</div>
            <div className="name">
              대전 2반 배성현 <br /> 잘먹겠습니다~
            </div>
            <div className="time">5분 뒤 카페에서 만나요</div>
            <div className="result-table">
              {/* 받아온 데이터로 띄우기 */}
              <div>1등</div>
              <div>배성현</div>
              <div>4.35</div>
              <div>2등</div>
              <div>1</div>
              <div>1</div>
              <div>3등</div>
              <div>1</div>
              <div>1</div>
              <div>4등</div>
              <div>1</div>
              <div>1</div>
              <div>5등</div>
              <div>1</div>
              <div>1</div>
              <div>6등</div>
              <div>1</div>
              <div>1</div>
              <div>7등</div>
              <div>1</div>
              <div>1</div>
              <div>8등</div>
              <div>1</div>
              <div>1</div>
              <div>9등</div>
              <div>1</div>
              <div>1</div>
              <div>10등</div>
              <div>1</div>
              <div>1</div>
              <div>11등</div>
              <div>1</div>
              <div>1</div>
              <div>12등</div>
              <div>1</div>
              <div>1</div>
            </div>
            <button
              className="result-btn"
              onClick={() => {
                navigate("/main");
              }}
            >
              나가기
            </button>
          </div>
        </div>
      ) : null}
      <div className="game">
        <div className="timer">
          {" "}
          <img
            src={`/assets/${!isLgm ? "lgm" : "mgl"}.png`}
            alt={!isLgm ? "lgm" : "mgl"}
            className="wait_lgm"
          />
          {"  "}
          {timer.toFixed(2)}
          {"  "}
          <img
            src={`/assets/${isLgm ? "lgm" : "mgl"}.png`}
            alt={isLgm ? "lgm" : "mgl"}
            className="wait_lgm"
          />
        </div>
        <div className="game-content">
          <span>4.3</span>초에 <br /> 프라이를 눌러주세요.
        </div>
        <div className="game-btn">
          <img
            src="/assets/egg_fri.png"
            alt="fri-btn"
            className={flip ? "flip" : ""}
            onClick={handleClick}
          />
        </div>
      </div>
    </>
  );
}

export default game;
