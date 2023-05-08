import { useState, useCallback, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { RootState } from "../../redux/store";
import { meeting, game } from "../../redux/user";
import { useSelector, useDispatch } from "react-redux";
import Categories from "./Categories/Categories";
import Back from "../../assets/back.png";
import Inputs from "./Inputs/Inputs";
import logo from "../../assets/small_logo.png";

import axios from "axios";
import "./Add.scss";

interface AddForm {
  cate: string;
  name: string;
  place: string;
  people: string;
  time: string;
  area: string;
}

interface AddFormMsg {
  name: string;
  place: string;
  people: string;
  area: string;
}

interface Error {
  cate: boolean;
  name: boolean;
  place: boolean;
  people: boolean;
  area: boolean;
}

export default function Add() {
  const navigate = useNavigate();
  const [searchparams, setSearchParmas] = useSearchParams();
  const tab = searchparams.get("tab");
  const cate = searchparams.get("cate");
  const api_url = process.env.REACT_APP_REST_API;
  const dispatch = useDispatch();
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  })

  const [form, setForm] = useState<AddForm>({
    cate: "",
    name: "",
    time: "내기 종료 후 5분뒤",
    place: "카페",
    people: "",
    area: ""
  });

  const [message, setMessage] = useState<AddFormMsg>({
    name: "",
    place: "",
    people: "",
    area: ""
  });

  const [error, setError] = useState<Error>({
    cate: false,
    name: false,
    place: false,
    people: false,
    area: false
  });

  useEffect(() => {
    if (tab === null || !["cate", "infos"].includes(tab)) {
      setSearchParmas("?tab=cate");
    }
  }, []);

  const createRoom = async (data: object, url: string) => {
    try {
      const header = {
        "Content-Type" : "application/json",
        "Authorization" : userId
      }
      const res = await axios.post(url, data, {headers : header});
      if(cate !== "bet"){
        dispatch(meeting(res.data.roomId));
        navigate(`/chatting/${res.data.roomId}`);
      }
      else {
        dispatch(game(res.data.gameRoomId));
        navigate(`/wait/${res.data.gameRoomId}?time=${res.data.randomTime}`);
      }
    } catch (e) {
      console.log(e);
    }
  };

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const isFormValid = Object.values(error).every((value) => value === true);
      if (isFormValid) {
        let data = {};
        let area = "";
        let url = api_url;
        if (form.area === "대전") area = "DAEJEON";
        else if (form.area === "서울") area = "SEOUL";
        else if (form.area === "광주") area = "GWANGJU";
        else if (form.area === "구미") area = "GUMI";
        else if (form.area === "부울경") area = "BUSAN";
        if (cate !== "bet") {
          let people = 0;
          url = url + "room";
          if (form.people === "2:2") people = 4;
          else if (form.people === "3:3") people = 6;
          else if (form.people === "4:4") people = 8;
          else if (form.people === "5:5") people = 10;
          else if (form.people === "6:6") people = 12;

          data = {
            title: form.name,
            headCount: people, // 내기방 제외 DB 저장 전에 x2
            roomCategory: cate,
            area: area,
            location: form.place
          };
        } else {
          url = api_url + "game-room";
          data = {
            title: form.name,
            headCount: Number(form.people),
            area: area,
            location: form.place
          };
        }
        createRoom(data, url);
      } else {
        alert("잘못된 입력입니다.");
      }
    },
    [form, error, message]
  );

  return (
    <div className="add-room">
      <img src={Back} alt="<" id="back" onClick={()=>navigate("/main")}/>
      <div className="add-container">
        <div className="small-logo">
          <img src={logo} alt="Logo" />
        </div>
        <div className="add-form">
          <form onSubmit={handleSubmit}>
            {tab === "cate" && (
              <Categories
                form={form}
                setForm={setForm}
                error={error}
                setError={setError}
              />
            )}
            {tab === "infos" && (
              <div>
                <Inputs
                  form={form}
                  setForm={setForm}
                  error={error}
                  setError={setError}
                  message={message}
                  setMessage={setMessage}
                />
                <button
                  className="add-btn"
                  id={
                    Object.values(error).every((value) => value === true)
                      ? "show"
                      : ""
                  }
                  type="submit"
                >
                  등록
                </button>
              </div>
            )}
          </form>
        </div>
      </div>
    </div>
  );
}
