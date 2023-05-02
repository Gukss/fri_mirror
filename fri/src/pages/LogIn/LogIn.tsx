import { useState, useCallback, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import { useDispatch } from "react-redux";
import { login } from "../../redux/user";

import logo from "../../assets/images/Logo.png";
import Back from "../../components/Back";
import "./LogIn.scss";

interface SingInForm {
  id: string;
  password: string;
}

function preloadImage(src: string) {
  const img = new Image();
  img.src = src;
}

export default function LogIn() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [form, setForm] = useState<SingInForm>({ id: "", password: "" });

  useEffect(() => {
    preloadImage("/assets/images/Logo.png");
  }, []);

  const handleInput = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  }, []);

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      // 로그인 axios
      // 로그인 받고 dispatch
      dispatch(
        login({
          userId: 1,
          location: "SEOUL",
          heart: 5,
          roomId: 1,
          gameRoomId: "참여한 방이 없습니다"
        })
      );
      navigate("/main");

      // 초기화
      setForm({ id: "", password: "" });
    },
    [form]
  );

  return (
    <div className="login">
      <Back />
      <div className="login-container">
        <div>
          <img
            src={logo}
            alt="Logo"
            className="logo"
            // ref={logoRef}
          />
        </div>
        <div
          className="login-form"
          // ref={contentRef}
        >
          <form onSubmit={handleSubmit}>
            <div className="login-box">
              <div className="login-input">
                <input
                  className="emailInput"
                  placeholder="아이디"
                  type="text"
                  name="id"
                  onChange={handleInput}
                />
              </div>
            </div>
            <div className="login-box">
              <input
                className="passwordInput"
                placeholder="비밀번호"
                type="password"
                name="password"
                onChange={handleInput}
              />
            </div>
            <div className="login-box">
              <button className="login-btn">로그인</button>
            </div>
          </form>
          <div
            className="sing-up"
            onClick={() => {
              navigate("/signup");
            }}
          >
            회원가입하러 가기
          </div>
        </div>
      </div>
    </div>
  );
}
