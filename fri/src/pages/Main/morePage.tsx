import React, { useEffect, useState, useRef, useCallback } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import axios from "axios";
import Room from "../../components/MeetingRoom";
import GameRoom from "../../components/GameRoom";
import Head from "../../components/LogoEgg";
import { useNavigate, useLocation } from "react-router-dom";
import Nav from "../../components/navEgg";
import "./more.scss";

interface roomType {
  roomId: number;
  title: string;
  headCount: number;
  major: number;
  nonMajor: number;
  location: string;
  roomCategory: string;
  is_com: boolean;
}

interface gameroomType {
  gameRoomId: number;
  title: string;
  headCount: number;
  participationCount: number;
  location: string;
}

function More() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const category = queryParams.get("category");
  const region = queryParams.get("region");
  const text = queryParams.get("text");
  const [isnav, setIsnav] = useState(false);
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  
  const [game, setGame] = useState<gameroomType[]>([]);
  const [room, setRoom] = useState<roomType[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [page, setPage] = useState(1);

  const loadMoreData = async () => {
    setIsLoading(true);
    try {
      let res:any;
      const header = {
        "Content-Type": "application/json",
        Authorization: Number(userId)
      };
      if (category !== "BETTING") {
        res = await axios.get(api_url + `room/category?area=${region}&category=${category}&page=${page}`, { headers: header });
        setRoom((prevData) => [...prevData, ...res.data]);
      } else {
        res = await axios.get(api_url + `game-room/category?area=${region}&page=${page}`, { headers: header });
        setGame((prevData) => [...prevData, ...res.data]);
      }
      setIsLoading(false);
    } catch (e) {
      console.log(e);
      setIsLoading(false);
    }
  };

  const observer = useRef<IntersectionObserver | null>(null);

  const lastRoomElementRef = useCallback(
    (node:any) => {
      if (isLoading) return;
      if (observer.current) observer.current.disconnect();
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting) {
          setPage((prevPage) => prevPage + 1);
        }
      });
      if (node) observer.current.observe(node);
    },
    [isLoading]
  );

  useEffect(() => {
    setPage(1);
    setGame([]);
    setRoom([]);
  }, [category, region, text]);

  useEffect(() => {
    loadMoreData();
  }, [page]);

  return (
    <div className="more_room">
      <Head />
      <div className="text">{text}</div>
      <div className="room">
        <div className="room_container">
          {category === "BETTING"
            ? game.map((room, idx) => <GameRoom key={idx} room={room} />)
            : room.map((room, idx) => <Room key={idx} room={room} />)
          }
        <div></div>
        <div ref={lastRoomElementRef}></div>
        </div>
      </div>
      <Nav isnav={isnav} setIsnav={setIsnav} />
    </div>
  );
}
export default More;
