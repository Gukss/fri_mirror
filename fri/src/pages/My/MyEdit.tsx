import { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../redux/store";
import { changeNick, changeProfile } from "../../redux/user";
import Back from "../../components/Back";
import logo from "../../assets/images/Logo.png";
import "./my.scss";
import axios from "axios";

interface Image {
  anonymousImageId: number;
  anonymousImageUrl: string;
}

type Images = Image[];

interface Edit {
  nickname: string;
}

interface Error {
  nickname: boolean;
}

export default function MyEdit() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const api_url = process.env.REACT_APP_REST_API;
  const profileImage = useSelector((state: RootState) => {
    return state.strr.anonymousProfileImageUrl;
  });
  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });

  const nickname = useSelector((state: RootState) => {
    return state.strr.nickname;
  });
  // 이미지 리스트
  const [images, setImages] = useState<Images>([
    {
      anonymousImageId: 0,
      anonymousImageUrl: ""
    }
  ]);
  // 선택되는 이미지 ID
  const [selected, setSelected] = useState<number>(0);

  // nickname
  const [form, setForm] = useState<Edit>({
    nickname: nickname
  });
  const [message, setMessage] = useState<Edit>({
    nickname: ""
  });
  const [error, setError] = useState<Error>({
    nickname: true
  });

  const setMessageColor = (color: string, name: string) => {
    const messageElement = document.getElementById(`${name}message`);
    const inputElement = document.getElementById(name);
    if (messageElement && inputElement) {
      messageElement.style.color = color;
      inputElement.style.border = `2px solid ${color}`;
      inputElement.style.borderRadius = "0.5rem";
    }
  };

  const handleInput = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const { name, value } = e.target;
      setForm({ ...form, [name]: value });

      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "nickname");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "" });
        setMessageColor("#ffc000", "nickname");
        setError({ ...error, [name]: true });
      }
    },
    [form]
  );

  const handleBlur = (e: React.FocusEvent<HTMLInputElement, Element>) => {
    const { name, value } = e.target;

    if (name === "nickname") {
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "nickname");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "" });
        setMessageColor("#ffc000", "nickname");
        setError({ ...error, [name]: true });
      }
    }
  };

  useEffect(() => {
    const getImg = async () => {
      const header = {
        "Content-Type": "application/json",
        Authorization: Number(userId)
      };
      const res = await axios.get(api_url + "user/profile-image", {
        headers: header
      });
      console.log(res.data.anonymousImages);
      res.data.anonymousImages.map((image: any) => {
        if (image.anonymousImageUrl === profileImage) {
          setSelected(image.anonymousImageId);
        }
      });
      setImages(res.data.anonymousImages);
    };
    getImg();
  }, []);

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const isFormValid = Object.values(error).every((value) => value === true);

      if (isFormValid) {
        const data = {
          anonymousProfileImageId: selected,
          nickname: form.nickname
        };
        const submit = async () => {
          const header = {
            "Content-Type": "application/json",
            Authorization: Number(userId)
          };
          try {
            const res = await axios.patch(api_url + "user", data, {
              headers: header
            });
            console.log(res);
            alert("변경되었습니다!");
            dispatch(changeNick(res.data.nickname));
            dispatch(changeProfile(res.data.anonymousProfileImageUrl));
            navigate("/my");
          } catch (err) {
            alert("이미 사용중인 아이디입니다ㅠㅠ");
            setMessage({
              ...message,
              nickname: "이미 사용중인 아이디입니다ㅜㅜ"
            });
            setMessageColor("red", "nickname");
            setError({ ...error, nickname: false });
          }
        };
        submit();
      }
    },
    [form, error, message]
  );

  return (
    <div className="my">
      <Back />
      <div className="my-box">
        <div>
          <img src={logo} alt="logo" id="logo" />
        </div>
        <div className="edit-box">
          <div className="edit-title">프로필 이미지 수정</div>
          <div className="select-img">
            {images.length === 1 ? (
              <div className="profile-img-box ">로딩중</div>
            ) : (
              images.map((image: any, index: number) => {
                return (
                  // className="profile-img-box"
                  <div
                    key={index}
                    className={`profile-img-box ${
                      selected === image.anonymousImageId ? "checked" : ""
                    }`}
                  >
                    <img
                      src={image.anonymousImageUrl}
                      alt="profile-img"
                      onClick={() => {
                        setSelected(image.anonymousImageId);
                      }}
                    />
                  </div>
                );
              })
            )}
          </div>
          <div className="edit-title">닉네임수정</div>
          <form className="signup-form edit" onSubmit={handleSubmit}>
            <div className="signup-box">
              <div className="signup-input">
                <input
                  className="nicknameInput"
                  placeholder="닉네임을 입력해주세요."
                  type="text"
                  id="nickname"
                  name="nickname"
                  value={form.nickname}
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="nicknamemessage" className="message">
                {message.nickname}
              </div>
            </div>
            <div className="signup-box">
              <button
                className="signup-btn"
                style={{
                  pointerEvents: Object.values(error).every(
                    (value) => value === true
                  )
                    ? "auto"
                    : "none",
                  background: Object.values(error).every(
                    (value) => value === true
                  )
                    ? "#ffce3c"
                    : "#ffefbe"
                }}
              >
                수정
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
