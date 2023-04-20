import Topheader from '../components/LogoEgg'
import { useState } from 'react'
import data from '../components/data/main_dummy.json'
import Meeting from '../components/MeetingRoom'
import Game from '../components/GameRoom'
import Nav from '../components/navEgg'

export type MeetType = {
  "place" : string,
  "title" : string,
  "soft" : number,
  "other" : number,
  "total" : number,
  "is_com" : boolean
}

export type GameType = {
  "place" : string,
  "title" : string,
  "current_cnt" : number,
  "total_cnt" : number
}

const Main: React.FC = () => {
  const [region, setRegion] = useState(1);
  const [isnav, setIsnav] = useState(false);
  const [game, setGame] = useState(data.game);
  const [dinner, setDinner] = useState(data.dinner);
  const [drink, setDrink] = useState([]);
  const [play, setPlay] = useState(data.play);
  const [exercise, setExercise] = useState(data.exercise);
  const [study, setStudy] = useState(data.study);
  const [etc, setEtc]= useState(data.etc);

  return(
    <div className="mainpage">
      <Topheader />
      <ul className="main_region">
        <li id={region == 1 ? "select" : "seoul"} onClick={()=>setRegion(1)}>서울</li>
        <li id={region == 2 ? "select" : "daejeon"} onClick={()=>setRegion(2)}>대전</li>
        <li id={region == 3 ? "select" : "gumi"} onClick={()=>setRegion(3)}>구미</li>
        <li id={region == 4 ? "select" : "gwangju"} onClick={()=>setRegion(4)}>광주</li>
        <li id={region == 5 ? "select" :  "buulgyeong"} onClick={()=>setRegion(5)}>부울경</li>
      </ul>
      <div className="region_bar"></div>
    
      <div className="main_category">
        <p id="category_title"># 내기 할 사람<span>더보기</span></p>
        {
          game.length ? 
            <div className="meeting_block">{ game.map((room, idx) => <Game key={idx} room={room} />)}</div>
          : <div className="meeting_block"><div className="empty_category">아직 방이 없음요..<br />방좀 만들어 주라요</div></div>
        }
        <p id="category_title"># 술 마실 사람<span>더보기</span></p>
        {
          drink.length ?  
          <div className="meeting_block">{ drink.map((room, idx) =><Meeting key={idx} room={room} />)}</div>
          : <div className="meeting_block"><div className="empty_category">아직 방이 없음요..<br />방좀 만들어 주라요</div></div>
        }
        <p id="category_title"># 밥 먹을 사람<span>더보기</span></p>
        {
          dinner.length ?
          <div className="meeting_block">{ dinner.map((room, idx) => <Meeting key={idx} room={room} />)}</div>
          : <div className="meeting_block"><div className="empty_category">아직 방이 없음요..<br />방좀 만들어 주라요</div></div>
        }
        <p id="category_title"># 놀 사람<span>더보기</span></p>
        {
          play.length ? 
          <div className="meeting_block">{ play.map((room, idx) => <Meeting key={idx} room={room} />)}</div>
          : <div className="meeting_block"><div className="empty_category">아직 방이 없음요..<br />방좀 만들어 주라요</div></div>
        }
        <p id="category_title"># 운동 할 사람<span>더보기</span></p>
        {
          exercise.length ?
          <div className="meeting_block">{ exercise.map((room, idx) => <Meeting key={idx} room={room} />)}</div>
          : <div className="meeting_block"><div className="empty_category">아직 방이 없음요..<br />방좀 만들어 주라요</div></div>
        }
        <p id="category_title"># 공부 할 사람<span>더보기</span></p>
        {
          study.length?
          <div className="meeting_block">{ study.map((room, idx) => <Meeting key={idx} room={room} />)}</div>
          : <div className="meeting_block"><div className="empty_category">아직 방이 없음요..<br />방좀 만들어 주라요</div></div>
        }
        <p id="category_title"># 뭐라도 할 사람<span>더보기</span></p>
        {
          etc.length ?
          <div className="meeting_block">{ etc.map((room, idx) => <Meeting key={idx} room={room} />)}</div>
          : <div className="meeting_block"><div className="empty_category">아직 방이 없음요..<br />방좀 만들어 주라요</div></div>
        }
      </div>
      <Nav isnav={isnav} setIsnav={setIsnav} />
    </div>
  )
}
export default Main