import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import { login } from "../../redux/user";
import ting from "../../assets/gif/tingtingting.gif";
import "./Onboarding.scss";
import axios from "axios";

export default function Onboarding() {
  // const dispatch = useDispatch();
  const navigate = useNavigate();
  // const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  // const gameRoomId = useSelector((state: RootState) => {
  //   return state.strr.gameRoomId;
  // });

  // 새로 정보 받아와서 업데이트 하고, 그 다음 넘어가자
  // useEffect(() => {
  //   const getNewInfo = async () => {
  //     const header = {
  //       "Content-Type": "application/json",
  //       Authorization: Number(userId)
  //     };
  //     const res = await axios.get(api_url + "user", {
  //       headers: header
  //     });
  //     // dispatch(login(res.data));
  //     const data = {
  //       anonymousProfileImageUrl: res.data.anonymousProfileImageUrl,
  //       gameRoomId: res.data.gameRoomId,
  //       heart: res.data.heart,
  //       location: res.data.area,
  //       major: res.data.major,
  //       name: res.data.name,
  //       nickname: res.data.nickname,
  //       roomId: res.data.roomId,
  //       userId: userId
  //     };
  //     dispatch(login(data));
  //   };
  //   getNewInfo();
  // }, []);

  const handleClick = () => {
    navigate("/login");
    // if (userId === 0) {
    //   navigate("/login");
    // } else {
    //   navigate("/main");
    // }
  };

  return (
    <div className="onboarding" onClick={handleClick}>
      <img id="gif" src={ting} alt="ting" className="onboarding-img" />
      <div className="onboarding-text"> 터치해 주세요.</div>
    </div>
  );
}
