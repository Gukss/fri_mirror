import Topheader from "../../components/LogoEgg";
import { useEffect, useState } from "react";
import { createSearchParams, useNavigate } from "react-router-dom";
import data from "../../components/data/main_dummy.json";
import Meeting from "../../components/MeetingRoom";
import Game from "../../components/GameRoom";
import Nav from "../../components/navEgg";
import axios from "axios";
import "./main.scss";

export type MeetType = {
  place: string;
  title: string;
  soft: number;
  other: number;
  total: number;
  is_com: boolean;
};

export type GameType = {
  place: string;
  title: string;
  current_cnt: number;
  total_cnt: number;
};

const Main: React.FC = () => {
  const [region, setRegion] = useState("SEOUL");
  const [isnav, setIsnav] = useState(false);
  const [tdata, setTdata] = useState({});
  const [game, setGame] = useState(data.game);
  const [dinner, setDinner] = useState(data.dinner);
  const [drink, setDrink] = useState([]);
  const [play, setPlay] = useState(data.play);
  const [exercise, setExercise] = useState(data.exercise);
  const [study, setStudy] = useState(data.study);
  const [etc, setEtc] = useState(data.etc);

  const api_url = process.env.REACT_APP_REST_API;

  const navigate = useNavigate();

  function go_more(category : string) {
    const categories = {
      "game" : "오늘 지금 당장 내기 할 사람",
      "drink" : "이번주 금요일 술 마실 사람",
      "dinner" : "이번주 금요일 밥 먹을 사람",
      "play" : "이번주 금요일 놀 사람",
      "exercise" : "이번주 금요일 운동 할 사람",
      "study" : "이번주 금요일 공부 할 사람",
      "etc" : "이번주 금요일 뭐라도 할 사람"
    }

    let temp = "";
    if(category === "game") temp = categories.game
    else if(category === "drink") temp = categories.drink
    else if(category === "dinner") temp = categories.dinner
    else if(category === "play") temp = categories.play
    else if(category === "exercise") temp = categories.exercise
    else if(category === "study") temp = categories.study
    else if(category === "etc") temp = categories.etc
    navigate({pathname : "/more", search : createSearchParams({"region" : region, "category" : category, "text" : temp}).toString()})
    }


  useEffect(() => {
    const getData = async () => {
      const userId = 1;
      try{
        const res = await axios.get(api_url + `room?area=${region}`, {headers : {'userId' : userId}})
        console.log(res.data)
        setTdata(res.data)
      }
      catch(e) {console.log(e)}
    }
    getData()
  }, [tdata])

  return (
    <div className="mainpage">
      <Topheader />
      <ul className="main_region">
        <li id={region == "SEOUL" ? "select" : "seoul"} onClick={() => setRegion("SEOUL")}>서울</li>
        <li id={region == "DAEJEON" ? "select" : "daejeon"} onClick={() => setRegion("DAEJEON")}>대전</li>
        <li id={region == "GUMI" ? "select" : "gumi"} onClick={() => setRegion("GUMI")}>구미</li>
        <li id={region == "GWANJU" ? "select" : "gwangju"} onClick={() => setRegion("GWANJU")}>광주</li>
        <li id={region == "BUSAN" ? "select" : "buulgyeong"} onClick={() => setRegion("BUSAN")}>부울경</li>
      </ul>
      <div className="region_bar"></div>

      <div className="main_category">
        <p id="category_title"># 내기 할 사람<span onClick={()=>go_more("game")}>더보기</span></p>
        {game.length ? (
          <div className="meeting_block">
            {game.map((room, idx) => (
              <Game key={idx} room={room} />
            ))}
          </div>
        ) : (
          <div className="meeting_block">
            <div className="empty_category">
              아직 방이 없음요..
              <br />
              방좀 만들어 주라요
            </div>
          </div>
        )}
        <p id="category_title"># 술 마실 사람<span onClick={()=>go_more("drink")}>더보기</span></p>
        {drink.length ? (
          <div className="meeting_block">
            {drink.map((room, idx) => (
              <Meeting key={idx} room={room} />
            ))}
          </div>
        ) : (
          <div className="meeting_block">
            <div className="empty_category">
              아직 방이 없음요..
              <br />
              방좀 만들어 주라요
            </div>
          </div>
        )}
        <p id="category_title"># 밥 먹을 사람<span  onClick={()=>go_more("dinner")}>더보기</span></p>
        {dinner.length ? (
          <div className="meeting_block">
            {dinner.map((room, idx) => (
              <Meeting key={idx} room={room} />
            ))}
          </div>
        ) : (
          <div className="meeting_block">
            <div className="empty_category">
              아직 방이 없음요..
              <br />
              방좀 만들어 주라요
            </div>
          </div>
        )}
        <p id="category_title"># 놀 사람<span onClick={()=>go_more("play")}>더보기</span></p>
        {play.length ? (
          <div className="meeting_block">
            {play.map((room, idx) => (
              <Meeting key={idx} room={room} />
            ))}
          </div>
        ) : (
          <div className="meeting_block">
            <div className="empty_category">
              아직 방이 없음요..
              <br />
              방좀 만들어 주라요
            </div>
          </div>
        )}
        <p id="category_title"># 운동 할 사람<span onClick={()=>go_more("exercise")}>더보기</span></p>
        {exercise.length ? (
          <div className="meeting_block">
            {exercise.map((room, idx) => (
              <Meeting key={idx} room={room} />
            ))}
          </div>
        ) : (
          <div className="meeting_block">
            <div className="empty_category">
              아직 방이 없음요..
              <br />
              방좀 만들어 주라요
            </div>
          </div>
        )}
        <p id="category_title"># 공부 할 사람<span onClick={()=>go_more("study")}>더보기</span></p>
        {study.length ? (
          <div className="meeting_block">
            {study.map((room, idx) => (
              <Meeting key={idx} room={room} />
            ))}
          </div>
        ) : (
          <div className="meeting_block">
            <div className="empty_category">
              아직 방이 없음요..
              <br />
              방좀 만들어 주라요
            </div>
          </div>
        )}
        <p id="category_title">
          # 뭐라도 할 사람<span onClick={()=>go_more("etc")}>더보기</span></p>
        {etc.length ? (
          <div className="meeting_block">
            {etc.map((room, idx) => (
              <Meeting key={idx} room={room} />
            ))}
          </div>
        ) : (
          <div className="meeting_block">
            <div className="empty_category">
              아직 방이 없음요..
              <br />
              방좀 만들어 주라요
            </div>
          </div>
        )}
      </div>
      <Nav isnav={isnav} setIsnav={setIsnav} />
    </div>
  );
};
export default Main;
