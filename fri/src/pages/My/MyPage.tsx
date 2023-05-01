import { useState } from "react";
import Nav from "../../components/navEgg"
import my from "../../assets/my_img.png"
import plus from "../../assets/plus_btn.png"
import egg from "../../assets/egg.png"
import logo from "../../assets/images/Logo.png"
import Back from "../../components/Back"
import "./my.scss"

function MyPage() {

  const [isnav, setIsnav] = useState(false);

  return (
    <div className="my">
      <Back />
      <img src={logo} alt="logo" />
      <p>마잉 페이지</p>
      <img src={my} alt="profile" id="profile"/>
      <img src={plus} alt="plus" id="plus"/>
      <p id="nick">나는야 감동란 될꺼야</p>
    
      <p id="egg_cnt">현재 달걀 개수</p>
      <div id="eggs">
        <img src={egg} alt="egg" />
      </div>
      <Nav isnav={isnav} setIsnav={setIsnav}/>
    </div>
  )
}
export default MyPage;