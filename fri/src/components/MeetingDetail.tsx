import { useEffect, useState } from "react";
import { MeetType } from "../pages/Main/mainPage";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../redux/store";
import { meeting, useegg } from "../redux/user";
import axios from "axios";

interface roomType {
  headCount : number;
  id: number;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

interface Roomdetail {
  roomId : number;
  title : string;
  location : string;
  roomCategory: string;
  headCount : number;
  isParticipate : boolean;
  participate: boolean;
  major: { name: string; url: string }[];
  nonMajor: { name: string; url: string }[];
}

function MeetingDetail({ id, headCount, open, setOpen }: roomType) {
  const [data, setData] = useState<Roomdetail>()
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
  const eggCnt = useSelector((state: RootState) => {
    return state.strr.heart;
  });

  const isPossible = useSelector((state: RootState) => {
    if(data === undefined) return;
    if (state.strr.roomId !== "참여한 방이 없습니다.") {
      return "already";
    } else if (state.strr.major === false && data?.nonMajor.length >= data?.headCount / 2) {
      return "full";
    } else if (state.strr.major === true && data?.major.length >= data?.headCount / 2) {
      return "full";
    } else {
      return true;
    }
  });

  const api_url = process.env.REACT_APP_REST_API;

  useEffect(() => {
    const getDetail = async () => {
      try {
        const header = {
          "Content-Type": "application/json",
          Authorization: userId
        };
        const res = await axios.get(api_url + "room/" + id, {
          headers: header
        });
        setData(res.data);
      } catch (e) {
        console.log();
      }
    };
    getDetail();
  }, []);

  const goChat = async () => {
    setModal(true);
    if (eggCnt === 0) {
      alert("알이 없어요... 내일 다시 오셔야 할듯..");
    } else setModal(true);
    if (isRoom !== "참여한 방이 없습니다.") {
      alert("이미 다른 방에 참여중입니다.");
      return;
    }
    const header = {
      Authorization: userId
    };
    try {
      const res = await axios.patch(
        api_url + `user/room/${id}`,
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

  const handleDetailClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      // 클릭한 요소가 room_detail인 경우에만 처리
      setOpen(false);
    }
  };

  return (
    <>
      {open ? (
        <div className="room_detail" onClick={handleDetailClick}>
          <div className="room_modal">
            <div className="title">
              <span>{data?.title}</span>
              <span style={{ color: "#FFC000" }} onClick={() => setOpen(false)}>
                X
              </span>
            </div>
            <div className="place"># {data?.location}</div>
            <div className="soft">
              <div className="header">
                <div>전공</div>
                <div className="total">
                  {data?.major.length} / {headCount / 2}
                </div>
              </div>
              <div className="profile">
                {data?.major.length === 0 ? (
                  <div>참여자가 없습니다.</div>
                ) : (
                  data?.major.map((info: any) => (
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
                  {data?.nonMajor.length} / {headCount / 2}
                </div>
              </div>
              <div className="profile">
                {data?.nonMajor.length === 0 ? (
                  <div>참여자가 없습니다.</div>
                ) : (
                  data?.nonMajor.map((info: any) => (
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
      <div
        className="modal-back"
        style={modal ? { top: 0 } : { bottom: "-100vh" }}
      >
        <div
          className="checkmodal"
          style={modal ? { bottom: "40vh" } : { bottom: "-20vh" }}
        >
          <div className="check-text">입장료 : 달걀 1개</div>
          <div className="button">
            <button
              className="ok-btn"
              onClick={() => {
                goChat();
                setModal(false);
              }}
            >
              지불
            </button>
            <button
              className="no-btn"
              onClick={() => {
                setModal(false);
              }}
            >
              X
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
export default MeetingDetail;
