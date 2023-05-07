import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import Nav from "../../components/navEgg";
import plus from "../../assets/plus_btn.png";
import egg from "../../assets/egg.png";
import fri from "../../assets/egg_fri.png"
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
  anonymousProfileImageId : string,
  roomId : string,
  year : string
}

function MyPage() {

  const [isnav, setIsnav] = useState(false);
  const [data, setData] = useState<myData>();
  const [isget, setGet] = useState<boolean>(false);
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  })
  const egg_cnt = useSelector((state: RootState) => {
		return state.strr.heart
	})

	const heart = () => {
		const result = []
		for(let i = 0; i < egg_cnt; i++){
			result.push(<img src={egg} alt="egg" id="main_egg" key={i}/>)
		}
		return result;
	}

  useEffect(() => {
    const getData = async () => {
      const header = {
        "Content-Type" : "application/json",
        "Authorization" : Number(userId),
      }
      const res = await axios.get(api_url + "user", {headers : header});
      setData(res.data)
    }
    getData()
    if(data === undefined) return;
  }, [])
  return (
    <div className="my">
      <Back />
      <img src={logo} alt="logo" id="logo"/>
      <p>마잉 페이지</p>
      <img src={data?.anonymousProfileImageId} alt="profile" id="profile"/>
      <img src={plus} alt="plus" id="plus" onClick={() => setGet(true)}/>
      {
        isget ? 
        <input id="get_img" type="file" accept="image/*;capture=camera"/>
        :
        null
      }
      <p id="nick">{data?.nickname}</p>
    
      <p id="egg_cnt">내 알</p>
      <div id="eggs">
        {
          egg_cnt ? heart() : <img src={fri} alt="fri" id="fri" />
        }
      </div>
      <Nav isnav={isnav} setIsnav={setIsnav}/>
    </div>
  )
}
export default MyPage;