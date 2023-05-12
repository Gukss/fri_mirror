import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { login, logout } from "../../redux/user";
import { RootState } from "../../redux/store";
import { useNavigate } from "react-router-dom";
import Nav from "../../components/navEgg";
import egg from "../../assets/egg.png";
import fri from "../../assets/egg_fri.png";
import logo from "../../assets/images/Logo.png";
import Back from "../../components/Back";
import axios from "axios";
import "./my.scss";

interface myData {
  area: string;
  email: string;
  gameRoomId: string;
  major: boolean;
  name: string;
  nickname: string;
  anonymousProfileImageId: string;
  roomId: string;
  year: string;
}

function MyPage() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isnav, setIsnav] = useState(false);
  const [data, setData] = useState<myData>();
  const api_url = process.env.REACT_APP_REST_API;
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  const egg_cnt = useSelector((state: RootState) => {
    return state.strr.heart;
  });

  const profileImage = useSelector((state: RootState) => {
    return state.strr.anonymousProfileImageUrl;
  });
  const nickname = useSelector((state: RootState) => {
    return state.strr.nickname;
  });

  const heart = () => {
    const result = [];
    for (let i = 0; i < egg_cnt; i++) {
      result.push(<img src={egg} alt="egg" id="main_egg" key={i} />);
    }
    return result;
  };

  useEffect(() => {
    const getData = async () => {
      const header = {
        "Content-Type": "application/json",
        Authorization: Number(userId)
      };
      const res = await axios.get(api_url + "user", { headers: header });
      setData(res.data);
    };
    getData();
  }, []);

  return (
    <div className="my">
      <Back />
      <div className="my-box">
        <div>
          <img src={logo} alt="logo" id="logo" />
        </div>

        <div className="img-box">
          <div className="img">
            <img
              src={data?.anonymousProfileImageId}
              alt="profile"
              id="profile"
            />
          </div>
        </div>
        <div className="my-infos">
          <div className="info">
            <div className="info-title">이름</div>
            <div className="name">{data?.name}</div>
          </div>
          <div className="info">
            <div className="info-title">닉네임</div>
            <div className="nickname">
              <div>{data?.nickname}</div>
            </div>
          </div>
          <div className="info">
            <div className="info-title">내 알</div>
            <div id="eggs">
              {egg_cnt ? heart() : <img src={fri} alt="fri" id="fri" />}
            </div>
          </div>
          <div>
            <div className="info">
              <div className="upcoming">출시 예정</div>
              <div className="info-title">소개팅</div>
              <div className="check-box">
                <input type="checkbox" disabled />
              </div>
            </div>
          </div>
          <div className="info">
            <div
              className="logout"
              onClick={() => {
                dispatch(logout());
                navigate("/login");
              }}
            >
              로그아웃
            </div>
            {/* 클릭시 프로필 수정 페이지 이동해야함 */}
            <div
              className="edit"
              onClick={() => {
                navigate("/my/edit");
              }}
            >
              프로필 수정
            </div>
          </div>
        </div>
      </div>

      <Nav isnav={isnav} setIsnav={setIsnav} />
    </div>
  );
}
export default MyPage;
