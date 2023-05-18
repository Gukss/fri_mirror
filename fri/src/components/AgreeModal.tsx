import { useNavigate } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector } from "react-redux";
import Agree from "./Agree"
import axios from "axios";
import "../style_key.scss";

interface modalType {
  page : string;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

function AgreeModal({setOpen, page} : modalType) {
  const navigate = useNavigate();
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  })

  function movePage(check : boolean) {
    if(page === "login"){
      const header = {
        "Content-Type" : "application/json",
        "Authorization" : userId
      }
      const data = {
        "emailAgreement" : check
      }
      try {
        axios.patch(api_url + "user/certified/agreement", data, {headers : header})
        if(check) navigate("/main")
        else alert("그동안 저희 서스를 이용해 주셔서 감사합니다. \n 개인정보는 안전하게 삭제되었습니다.")
      }
      catch(e){console.log(e)}
    }
    else if(page === "signup") {
      if(check) navigate("/signup")
      else alert("마음이 바뀌시면 다시 찾아주세요..")
    }
  }
  
  return (
    <div className="agree_background">
      <div className="agree_modal">
        <div className="agree_title">이용약관 및 개인정보처리방침</div>
        <div className="agree_content">
          <Agree />
        </div>
        <div className="Agree_info">비동의 시 서비스 이용이 불가합니다.</div>
        <div className="modal_btn">
          <div className="ok_btn" onClick={() => {movePage(true); setOpen(false)}}>동의</div>
          <div className="no_btn" onClick={() => {movePage(false); setOpen(false)}}>비동의</div>
        </div>
      </div>
    </div>
  )
}
export default AgreeModal;