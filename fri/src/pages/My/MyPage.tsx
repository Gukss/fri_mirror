import { useState } from "react";
import Nav from "../../components/navEgg"
import "./my.scss"

function MyPage() {

  const [isnav, setIsnav] = useState(false);

  return (
    <div className="my">
      <img src="/assets/images/Logo.png" alt="logo" />
      <p>마잉 페이지</p>
      <img src="/assets/my_img.png" alt="profile" id="profile"/>
      <img src="/assets/plus_btn.png" alt="plus" id="plus"/>
      <p id="nick">나는야 감동란 될꺼야</p>
    
      <p id="egg_cnt">현재 달걀 개수</p>
      <div id="eggs">
        <img src="assets/egg.png" alt="egg" />
      </div>
      <Nav isnav={isnav} setIsnav={setIsnav}/>
    </div>
  )
}
export default MyPage;