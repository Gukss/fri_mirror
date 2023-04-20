import React, { useState, useEffect, useRef } from 'react';

function game() {
  const [seconds, setSeconds] = useState<number>(3);
  const [modal, setModal] = useState<boolean>(true);
  const start = "start!"
  const intervalRef = useRef<NodeJS.Timeout>();

  useEffect(() => {
    intervalRef.current = setInterval(() => {
      setSeconds((seconds) => seconds - 1);
    }, 1000);

    return () => clearInterval(intervalRef.current!);
  }, []);

  useEffect(() => {
    if (seconds === -1) {
      clearInterval(intervalRef.current!);
      setModal(false)
    }
  }, [seconds]);

  return (
    <>
    {
      modal ?      
      <div className="game_3s_back">
      {seconds? <div>{seconds}</div> : <div style={{fontSize: "100px"}}>{start}</div>}
    </div> : null
    }
    </>
    )
}

export default game;