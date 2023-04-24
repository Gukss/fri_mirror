import React, { useState, useEffect, useRef } from "react";

function game() {
  const [seconds, setSeconds] = useState<number>(3);
  const [timer, setTimer] = useState<number>(10);
  const [modal, setModal] = useState<boolean>(true);
  const [isLgm, setIsLgm] = useState(true);
  const [imagesLoaded, setImagesLoaded] = useState<boolean>(false);
  const start = "start!";
  const intervalRef = useRef<NodeJS.Timeout>();
  const timerRef = useRef<NodeJS.Timeout>();

  const [isRotated, setIsRotated] = useState(false);
  const [isJumping, setIsJumping] = useState(false);
  const [isEgg, setIsEgg] = useState(true);

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
    }
  }, [timer]);

  // 금만이
  useEffect(() => {
    const intervalId = setInterval(() => {
      setIsLgm((prev) => !prev);
    }, 320);

    return () => clearInterval(intervalId);
  }, []);

  // 버튼 클릭
  const handleClick = () => {
    setIsRotated(true);
    setIsJumping(true);

    setTimeout(() => {
      setIsEgg((prevState) => !prevState);
      setIsRotated(false);
      setIsJumping(false);
    }, 1000);
  };

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
          <span>4.3</span>초를 <br /> 맞춰주세요
        </div>
        <div className="game-btn" onClick={handleClick}>
          <img
            src="/assets/egg_fri.png"
            alt="fri-btn"
            className={`game-btn-img ${isRotated ? "rotate" : ""} ${
              isJumping ? "jump" : ""
            }`}
          />
        </div>
      </div>
    </>
  );
}

export default game;
