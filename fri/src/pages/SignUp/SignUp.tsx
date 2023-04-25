import { useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import "./SignUp.scss";

interface SignUp {
  area: string;
  year: string;
  name: string;
  id: string;
  nickname: string;
  password: string;
  passwordCheck: string;
}

interface Error {
  area: boolean;
  year: boolean;
  name: boolean;
  id: boolean;
  nickname: boolean;
  password: boolean;
  passwordCheck: boolean;
}

export default function SignUp() {
  const navigate = useNavigate();
  const [form, setForm] = useState<SignUp>({
    area: "",
    year: "",
    name: "",
    id: "",
    nickname: "",
    password: "",
    passwordCheck: ""
  });
  const [message, setMessage] = useState<SignUp>({
    area: "",
    year: "",
    name: "",
    id: "",
    nickname: "",
    password: "",
    passwordCheck: ""
  });
  const [error, setError] = useState<Error>({
    area: false,
    year: false,
    name: false,
    id: false,
    nickname: false,
    password: false,
    passwordCheck: false
  });

  const [major, setMajor] = useState<boolean>(true);

  const handleMajorChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value;
      setMajor(value === "major");
    },
    [major]
  );

  const handleInput = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const { name, value } = e.target;
      setForm({ ...form, [name]: value });
    },
    [form]
  );

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const isFormValid = Object.values(error).every((value) => value === true);

      if (isFormValid) {
        // 회원가입 axios
        console.log("회원가입~");
      } else {
        console.log("다시");
        // 모달 띄우거나
        // 안채운거 빨갛게
      }
    },
    [form, error, message]
  );

  const handleBlur = (e: React.FocusEvent<HTMLInputElement, Element>) => {
    const { name, value } = e.target;

    if (name === "area") {
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다." });
        setMessageColor("red", "area");
        setError({ ...error, [name]: false });
      } else if (value.includes("캠퍼스")) {
        setMessage({ ...message, [name]: "캠퍼스를 빼고 입력해 주세요!" });
        setMessageColor("red", "area");
        setError({ ...error, [name]: false });
      } else if (["서울", "대전", "대구", "광주", "부울경"].includes(value)) {
        setError({ ...error, [name]: true });
        setMessage({ ...message, [name]: "가능합니다!" });
        setMessageColor("green", "area");
      } else {
        setMessage({ ...message, [name]: "지역을 옳바르게 입력해 주세요." });
        setMessageColor("red", "area");
        setError({ ...error, [name]: false });
      }
    } else if (name === "year") {
      if (value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "year");
        setError({ ...error, [name]: false });
      } else if (!value) {
        setMessage({ ...message, [name]: "숫자를 입력해 주세요!" });
        setMessageColor("red", "year");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "가능합니다!" });
        setMessageColor("green", "year");
        setError({ ...error, [name]: true });
      }
    } else if (name === "name") {
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "name");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "가능합니다!" });
        setMessageColor("green", "name");
        setError({ ...error, [name]: true });
      }
    } else if (name === "id") {
      const emailRegex =
        /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "id");
        setError({ ...error, [name]: false });
      } else if (!emailRegex.test(value)) {
        setMessage({ ...message, [name]: "이메일 형식이 맞지 않습니다." });
        setMessageColor("red", "id");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "가능합니다!" });
        setMessageColor("green", "id");
        setError({ ...error, [name]: true });
      }
    } else if (name === "nickname") {
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "nickname");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "가능합니다!" });
        setMessageColor("green", "nickname");
        setError({ ...error, [name]: true });
      }
    } else if (name === "password") {
      const passwordRegex =
        /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "password");
        setError({ ...error, [name]: false });
      } else if (!passwordRegex.test(value)) {
        setMessage({
          ...message,
          [name]: "숫자+영문자+특수문자 조합으로 8자리 이상 입력해주세요!"
        });
        setMessageColor("red", "password");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "가능합니다!" });
        setMessageColor("green", "password");
        setError({ ...error, [name]: true });
      }
    } else if (name === "passwordCheck") {
      if (value !== form.password) {
        setMessage({ ...message, [name]: "비밀번호가 동일하지 않습니다!" });
        setMessageColor("red", "passwordCheck");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "비밀번호가 동일합니다!" });
        setMessageColor("green", "passwordCheck");
        setError({ ...error, [name]: true });
      }
    }
  };

  const setMessageColor = (color: string, name: string) => {
    const messageElement = document.getElementById(`${name}message`);
    const inputElement = document.getElementById(name);
    if (messageElement && inputElement) {
      messageElement.style.color = color;
      inputElement.style.border = `2px solid ${color}`;
      inputElement.style.borderRadius = "0.5rem";
    }
  };

  return (
    <div className="signup">
      <div
        onClick={() => {
          navigate(-1);
        }}
        style={{ position: "absolute", top: 0, left: 0 }}
      >
        뒤로
      </div>
      <div className="signup-container">
        <div>
          <img
            src="/assets/images/Logo.png"
            alt="Logo"
            className="signup-logo"
          />
        </div>
        <div className="signup-form">
          <form onSubmit={handleSubmit}>
            <div className="signup-box">
              <div className="signup-label" id="label">
                # 전공자 / 비전공자 <span>(클릭해 주세요.)</span>
              </div>
              <div className="signup-input radio">
                <div className="switch">
                  <div className="quality">
                    <input
                      id="major"
                      type="radio"
                      name="major"
                      value="major"
                      checked={major}
                      onChange={handleMajorChange}
                    />
                    <label htmlFor="major">전공자</label>
                  </div>
                  <div className="quality">
                    <input
                      id="nonMajor"
                      type="radio"
                      name="major"
                      value="nonMajor"
                      checked={!major}
                      onChange={handleMajorChange}
                    />
                    <label htmlFor="nonMajor">비전공자</label>
                  </div>
                </div>
              </div>
              <div id="areamessage" className="message">
                {message.area}
              </div>
            </div>
            <div className="signup-box">
              <div className="signup-label" id="label">
                # 캠퍼스 <span>(지역만 입력해주세요.)</span>
              </div>
              <div className="signup-input">
                <input
                  className="campusInput"
                  placeholder="캠퍼스를 입력해주세요. ex) 대전, 서울"
                  type="text"
                  id="area"
                  name="area"
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="areamessage" className="message">
                {message.area}
              </div>
            </div>
            <div className="signup-box">
              <div className="signup-label">
                # 기수 <span>(숫자만 입력해주세요.)</span>
              </div>
              <div className="signup-input">
                <input
                  className="yearInput"
                  placeholder="기수를 입력해주세요. ex) 8"
                  type="number"
                  id="year"
                  name="year"
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="yearmessage" className="message">
                {message.year}
              </div>
            </div>
            <div className="signup-box">
              <div className="signup-label"># 이름</div>
              <div className="signup-input">
                <input
                  className="nameInput"
                  placeholder="이름을 입력해주세요."
                  type="text"
                  id="name"
                  name="name"
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="namemessage" className="message">
                {message.name}
              </div>
            </div>
            <div className="signup-box">
              <div className="signup-label"># 에듀싸피 아이디</div>
              <div className="signup-input">
                <input
                  className="emailInput"
                  placeholder="싸피 이메일을 입력해주세요."
                  type="email"
                  name="id"
                  id="id"
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="idmessage" className="message">
                {message.id}
              </div>
            </div>
            <div className="signup-box">
              <div className="signup-label"># 닉네임</div>
              <div className="signup-input">
                <input
                  className="nicknameInput"
                  placeholder="닉네임을 입력해주세요."
                  type="text"
                  id="nickname"
                  name="nickname"
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="nicknamemessage" className="message">
                {message.nickname}
              </div>
            </div>
            <div className="signup-box">
              <div className="signup-label"># 비밀번호</div>
              <div className="signup-input">
                <input
                  className="pwInput"
                  placeholder="비밀번호를 입력해주세요."
                  type="password"
                  id="password"
                  name="password"
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="passwordmessage" className="message">
                {message.password}
              </div>
            </div>
            <div className="signup-box">
              <div className="signup-label"># 비밀번호 재확인</div>
              <div className="signup-input">
                <input
                  className="pwInput"
                  placeholder="비밀번호를 재입력해주세요."
                  type="password"
                  id="passwordCheck"
                  name="passwordCheck"
                  onChange={handleInput}
                  onBlur={handleBlur}
                />
              </div>
              <div id="passwordCheckmessage" className="message">
                {message.passwordCheck}
              </div>
            </div>
            <div className="signup-box">
              <button className="signup-btn">회원가입</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
