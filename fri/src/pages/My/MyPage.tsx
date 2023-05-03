import { useState, useEffect } from "react";
import Nav from "../../components/navEgg";
import my from "../../assets/my_img.png";
import plus from "../../assets/plus_btn.png";
import egg from "../../assets/egg.png";
import logo from "../../assets/images/Logo.png";
import Back from "../../components/Back";
import axios from "axios";
import "./my.scss"

interface myData {
  area: string,
  email : string,
  gameRoomId : string,
  major : boolean,
  name : string,
  nickname : string,
  profileUrl : string,
  roomId : string,
  year : string
}

function MyPage() {

  const [isnav, setIsnav] = useState(false);
  const [data, setData] = useState<myData>();
  const [isget, setGet] = useState<boolean>(false);
  const api_url = process.env.REACT_APP_REST_API;

  useEffect(() => {
    const getData = async () => {
      const res = await axios.get(api_url + "user", {headers : {"Authorization" : 1}});
      setData(res.data)
    }
    getData();
  }, [])

  // function getImg() {
    
  // }

  return (
    <div className="my">
      <Back />
      <img src={logo} alt="logo" />
      <p>마잉 페이지</p>
      <img src={my} alt="profile" id="profile"/>
      <img src={plus} alt="plus" id="plus" onClick={() => setGet(true)}/>
      {
        isget ? 
        <input id="get_img" type="file" accept="image/*;capture=camera"/>
        :
        null
      }
      <p id="nick">{data?.nickname}</p>
    
      <p id="egg_cnt">현재 달걀 개수</p>
      <div id="eggs">
        <img src={egg} alt="egg" />
      </div>
      <Nav isnav={isnav} setIsnav={setIsnav}/>
    </div>
  )
}
export default MyPage;