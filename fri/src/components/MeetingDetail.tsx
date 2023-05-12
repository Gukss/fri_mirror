import { useEffect, useState } from "react";
import { MeetType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../redux/store";
import { meeting, useegg } from "../redux/user";
import axios from "axios";

interface roomType {
  room: MeetType;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

interface Roomdetail {
  participate: boolean;
  majors: { name: string; url: string }[];
  nonMajors: { name: string; url: string }[];
}

function MeetingDetail({ room, open, setOpen }: roomType) {
  const { headCount, location, major, nonMajor, roomId, title } = room;
  const [isOk, setOk] = useState(false);
  const [modal, setModal] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });
  const isRoom = useSelector((state: RootState) => {
    return state.strr.roomId;
  });
  const eggCnt = useSelector((state:RootState) => {
    return state.strr.heart;
  })

  const isPossible = useSelector((state: RootState) => {
    if (state.strr.roomId !== "참여한 방이 없습니다.") {
      return "already";
    } else if (state.strr.major === false && nonMajor >= headCount / 2) {
      return "full";
    } else if (state.strr.major === true && major >= headCount / 2) {
      return "full";
    } else {
      return true;
    }
  });

  const api_url = process.env.REACT_APP_REST_API;

  // room detail 정보
  const [data, setData] = useState<Roomdetail>({
    participate: false,
    majors: [],
    nonMajors: []
  });

  useEffect(() => {
    const getDetail = async () => {
      try {
        const header = {
          "Content-Type": "application/json",
          Authorization: userId
        };
        const res = await axios.get(api_url + "room/" + roomId, {
          headers: header
        });
        const participate = res.data.isParticipate;
        const majors = res.data.major;
        const nonMajors = res.data.nonMajor;
        setData({
          ...data,
          participate: participate,
          majors: majors,
          nonMajors: nonMajors
        });
      } catch (e) {
        console.log();
      }
    };
    getDetail();
  }, []);

  const goChat = async () => {
    setModal(true);
    if(eggCnt === 0){
      alert("알이 없어요... 내일 다시 오셔야 할듯..")
    }
    else setModal(true);
    if (isRoom !== "참여한 방이 없습니다.") {
      alert("이미 다른 방에 참여중입니다.");
      return;
    }
    const header = {
      Authorization: userId
    };
    try {
      const res = await axios.patch(
        api_url + `user/room/${roomId}`,
        { participate: false },
        { headers: header }
      );
      dispatch(meeting(res.data.roomId));
      dispatch(useegg(1));
      navigate(`/chatting/${res.data.roomId}?isuser=false`);
    } catch (e) {
      console.log();
    }
  };

  return (
    <>
      {open ? (
        <div className="room_detail">
          <div className="room_modal">
            <div className="title">
              <span>{title}</span>
              <span style={{ color: "#FFC000" }} onClick={() => setOpen(false)}>
                X
              </span>
            </div>
            <div className="place"># {location}</div>
            <div className="soft">
              <div className="header">
                <div>전공</div>
                <div className="total">
                  {major} / {headCount / 2}
                </div>
              </div>
              <div className="profile">
                {major === 0 ? (
                  <div>참여자가 없습니다.</div>
                ) : (
                  data.majors.map((info: any) => (
                    <div key={info.name} className="info">
                      <div className="profile-img">
                        <img src={info.anonymousProfileImageUrl} alt="프로필" />
                      </div>
                      <div className="name-chat">{info.name}</div>
                    </div>
                  ))
                )}
              </div>
            </div>
            <div className="soft">
              <div className="header">
                <div>비전공</div>
                <div className="total">
                  {nonMajor} / {headCount / 2}
                </div>
              </div>
              <div className="profile">
                {nonMajor === 0 ? (
                  <div>참여자가 없습니다.</div>
                ) : (
                  data.nonMajors.map((info: any) => (
                    <div key={info.name} className="info">
                      <div className="profile-img">
                        <img src={info.anonymousProfileImageUrl} alt="프로필" />
                      </div>
                      <div className="name-chat">{info.name}</div>
                    </div>
                  ))
                )}
              </div>
            </div>
            {isPossible === "already" ? (
              <div
                className="join_game"
                style={{
                  background: "#ffefbe"
                }}
                onClick={() => {
                  navigate(`/chatting/${isRoom}?isuser=true`);
                }}
              >
                이미 다른 채팅방에 들어가 있어요!
                <br /> 내 채팅방으로
              </div>
            ) : isPossible === "full" ? (
              <div
                className="join_game"
                style={{
                  background: "#ffefbe"
                }}
              >
                방이 꽉 찼어요ㅜㅜ
              </div>
            ) : (
              <div className="join_game" onClick={() => setModal(true)}>
                참여하기
              </div>
            )}
          </div>
        </div>
      ) : null}
      <div className="modal-back" style={modal ? {top : 0} : {bottom : "-100vh"}}>
      <div className="checkmodal" style={modal ? {bottom : "40vh"} : {bottom : "-20vh"}}>
        <div className="check-text">
          입장료 : 달걀 1개
        </div>
        <div className="button">
          <button 
            className="ok-btn" 
            onClick={() => {goChat(); setModal(false)}}
          >지불</button>
          <button className="no-btn" onClick={() => {setModal(false)}}>X</button>
			</div>
		</div>
    </div>
    </>
  );
}
export default MeetingDetail;
