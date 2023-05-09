import { useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import logo from "../../assets/images/Logo.png";
import Back from "../../components/Back";
import axios from "axios";
import "./SignUp.scss";

interface SignUp {
  area: string;
  year: string;
  name: string;
  id: string;
  nickname: string;
  password: string;
  passwordCheck: string;
  code: string;
}

interface Error {
  area: boolean;
  year: boolean;
  name: boolean;
  id: boolean;
  nickname: boolean;
  password: boolean;
  passwordCheck: boolean;
  code: boolean;
}

const area = ["서울", "대전", "광주", "구미", "부울경"];
const year = [9, 8, 7, 6, 5, 4, 3, 2, 1];

export default function SignUp() {
  const api_url = process.env.REACT_APP_REST_API;
  const navigate = useNavigate();
  const [form, setForm] = useState<SignUp>({
    area: "",
    year: "",
    name: "",
    id: "",
    nickname: "",
    password: "",
    passwordCheck: "",
    code: ""
  });
  const [message, setMessage] = useState<SignUp>({
    area: "",
    year: "",
    name: "",
    id: "",
    nickname: "",
    password: "",
    passwordCheck: "",
    code: ""
  });
  const [error, setError] = useState<Error>({
    area: false,
    year: false,
    name: false,
    id: false,
    nickname: false,
    password: false,
    passwordCheck: false,
    code: false
  });

  const [major, setMajor] = useState<boolean>(true);
  const [isemail, setEmail] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);
  const [isCer, setCer] = useState<boolean>(false);
  const [isCon, setCon] = useState<boolean>(false);
  const [nicknameCheck, setNicknameCheck] = useState<boolean>(false);

  const [isAreaDown, setIsAreaDown] = useState(false);

  const onClickArea = useCallback(() => {
    setIsAreaDown(!isAreaDown);
  }, [isAreaDown]);

  const onClickAreaOption = useCallback(
    (e: React.MouseEvent<HTMLButtonElement>) => {
      const { name, value } = e.currentTarget;
      setIsAreaDown(false);
      setForm({ ...form, [name]: value });
      setError({ ...error, [name]: true });
    },
    [form, error]
  );

  const [isYearDown, setIsYearDown] = useState(false);

  const onClickYear = useCallback(() => {
    setIsYearDown(!isYearDown);
  }, [isYearDown]);

  const onClickYearOption = useCallback(
    (e: React.MouseEvent<HTMLButtonElement>) => {
      const { name, value } = e.currentTarget;
      setIsYearDown(false);
      setForm({ ...form, [name]: value });
      setError({ ...error, [name]: true });
    },
    [form, error]
  );

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
      if (name === "id") {
        const emailRegex =
          /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
        if (!value || value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
          setMessageColor("red", "id");
          setError({ ...error, [name]: false });
          setEmail(false);
        } else if (!emailRegex.test(value)) {
          setMessage({ ...message, [name]: "이메일 형식이 맞지 않습니다." });
          setMessageColor("red", "id");
          setError({ ...error, [name]: false });
          setEmail(false);
        } else {
          setMessage({ ...message, [name]: "가능합니다!" });
          setMessageColor("green", "id");
          setError({ ...error, [name]: true });
          setEmail(true);
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
      } else if (name === "code") {
        if (!value || value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
          setMessageColor("red", "code");
          setError({ ...error, [name]: false });
        } else if (value.trim().length < 8 || value.trim().length > 8) {
          setMessage({ ...message, [name]: "인증번호는 8글자입니다!" });
          setMessageColor("red", "code");
          setError({ ...error, [name]: false });
        } else {
          setMessage({ ...message, [name]: "" });
          setMessageColor("green", "code");
          setError({ ...error, [name]: true });
        }
      }
    },
    [form, error, message]
  );

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const isFormValid = Object.values(error).every((value) => value === true);

      if (isFormValid) {
        let area = "";
        if (form.area == "대전") area = "DAEJEON";
        else if (form.area == "서울") area = "SEOUL";
        else if (form.area == "광주") area = "GWANJU";
        else if (form.area == "구미") area = "GUMI";
        else if (form.area == "부울경") area = "BUSAN";

        const data = {
          name: form.name,
          email: form.id,
          password: form.password,
          nickname: form.nickname,
          area: area,
          year: String(form.year),
          isMajor: major
        };
        const go = async () => {
          try {
            axios.post(api_url + "user", data);
            navigate("/");
          } catch (e) {
            alert("입력된 값을 확인해 주세요.");
          }
        };
        go();
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
        setEmail(false);
      } else if (!emailRegex.test(value)) {
        setMessage({ ...message, [name]: "이메일 형식이 맞지 않습니다." });
        setMessageColor("red", "id");
        setError({ ...error, [name]: false });
        setEmail(false);
      } else {
        setMessage({ ...message, [name]: "가능합니다!" });
        setMessageColor("green", "id");
        setError({ ...error, [name]: true });
        setEmail(true);
      }
    } else if (name === "nickname") {
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "nickname");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "중복확인이 필요합니다!" });
        setMessageColor("red", "nickname");
        setError({ ...error, [name]: false });
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
    } else if (name === "code") {
      if (!value || value.trim().length === 0) {
        setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
        setMessageColor("red", "code");
        setError({ ...error, [name]: false });
      } else if (value.trim().length < 8 || value.trim().length > 8) {
        setMessage({ ...message, [name]: "인증번호는 8글자입니다!" });
        setMessageColor("red", "code");
        setError({ ...error, [name]: false });
      } else {
        setMessage({ ...message, [name]: "" });
        setMessageColor("green", "code");
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

  const goEdu = async () => {
    setLoading(true);
    try {
      await axios.post(api_url + "user/certified/edu", { email: form.id });
      setCon(true);
      setEmail(false);
      setLoading(false);
    } catch (e: any) {
      setLoading(false);
      if (e.response.status === 400) alert("이미 가입된 이메일입니다.");
      else alert("싸피인이 맞습니까? 에듀싸피 이메일을 입력해주세요.");
    }
  };

  const goCertification = async () => {
    setLoading(true);
    try {
      const data = {
        email: form.id,
        code: form.code
      };
      await axios.post(api_url + "user/certified/code", data);
      setCer(true);
      setCon(false);
      setLoading(false);
    } catch (e) {
      setLoading(false);
      alert("이메일로 받은 코드를 정확히 입력해주세요.");
    }
  };

  const duplicateCheck = useCallback(async () => {
    setLoading(true);
    try {
      const data = {
        nickname: "닉네임"
      };
      await axios.post(api_url + "user/certified/nickname", data);
      // 이쪽 수정
      setNicknameCheck(true);
      setMessage({ ...message, nickname: "가능합니다!" });
      setMessageColor("green", "nickname");
      setError({ ...error, nickname: true });
      setLoading(false);
    } catch (e) {
      setLoading(false);
      alert("이미 닉네임이 존재합니다. 다른 닉네임으로 재시도해주세요.");
    }
  }, []);

  return (
    <div className="signup">
      <div className="signup-container">
        <div className="signup-nav">
          <Back />
          <img src={logo} alt="Logo" className="signup-logo" />
        </div>
        <div className="signup-form">
          <form onSubmit={handleSubmit}>
            <div className="signup-box">
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
                    <label htmlFor="major">전공</label>
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
                    <label htmlFor="nonMajor">비전공</label>
                  </div>
                </div>
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
              {isemail ? (
                <div
                  className="edu_certi"
                  onClick={goEdu}
                  style={{
                    pointerEvents: loading ? "none" : "auto",
                    background: loading ? "#ffefbe" : "#ffce3c"
                  }}
                >
                  {!loading ? (
                    <div
                      style={{
                        height: "2rem",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center"
                      }}
                    >
                      인증
                    </div>
                  ) : (
                    <div className="spinner">
                      <div className="spinner-wrapper">
                        <div className="rotator">
                          <div className="inner-spin"></div>
                          <div className="inner-spin"></div>
                        </div>
                      </div>
                    </div>
                  )}
                </div>
              ) : null}
              {isCon ? (
                <>
                  <div className="signup-input" style={{ marginTop: "0.5rem" }}>
                    <input
                      className="codeInput"
                      placeholder="인증번호 8자리"
                      type="text"
                      id="code"
                      name="code"
                      onChange={handleInput}
                      onBlur={handleBlur}
                      autoFocus
                    />
                  </div>
                  <div id="codemessage" className="message">
                    {message.code}
                  </div>
                  {error.code && (
                    <div
                      className="email_certi"
                      onClick={goCertification}
                      style={{
                        pointerEvents: loading ? "none" : "auto",
                        background: loading ? "#ffefbe" : "#ffce3c"
                      }}
                    >
                      {!loading ? (
                        <div
                          style={{
                            height: "2rem",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center"
                          }}
                        >
                          확인
                        </div>
                      ) : (
                        <div className="spinner">
                          <div className="spinner-wrapper">
                            <div className="rotator">
                              <div className="inner-spin"></div>
                              <div className="inner-spin"></div>
                            </div>
                          </div>
                        </div>
                      )}
                    </div>
                  )}
                </>
              ) : null}
            </div>
            {isCer ? (
              <>
                <div className="signup-box">
                  <div className="signup-label" id="label">
                    # 캠퍼스
                  </div>
                  <div className="component">
                    <button
                      className={`select-button ${
                        form.area === "" ? "" : "checked"
                      }`}
                      type="button"
                      id="people"
                      name="area"
                      onClick={onClickArea}
                    >
                      <div
                        className={`select ${
                          form.area === "" ? "" : "checked"
                        }`}
                      >
                        {form.area === "" ? "지역을 선택해주세요." : form.area}
                      </div>
                    </button>

                    {isAreaDown && (
                      <div className="drop-down">
                        {area.map((area) => (
                          <button
                            className="option"
                            value={area}
                            key={area}
                            onClick={onClickAreaOption}
                            name="area"
                          >
                            {area}
                          </button>
                        ))}
                      </div>
                    )}
                  </div>
                </div>
                <div className="signup-box">
                  <div className="signup-label"># 기수</div>
                  <div className="component">
                    <button
                      className={`select-button ${
                        form.year === "" ? "" : "checked"
                      }`}
                      type="button"
                      id="people"
                      name="year"
                      onClick={onClickYear}
                    >
                      <div
                        className={`select ${
                          form.year === "" ? "" : "checked"
                        }`}
                      >
                        {form.year === "" ? "기수를 선택해주세요." : form.year}
                      </div>
                    </button>

                    {isYearDown && (
                      <div className="drop-down">
                        {year.map((year) => (
                          <button
                            className="option"
                            value={year}
                            key={year}
                            onClick={onClickYearOption}
                            name="year"
                          >
                            {year}
                          </button>
                        ))}
                      </div>
                    )}
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
                  {!nicknameCheck && form.nickname !== "" && (
                    <div className="nickname_certi" onClick={duplicateCheck}>
                      중복확인
                    </div>
                  )}
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
                      // onBlur={handleBlur}
                    />
                  </div>
                  <div id="passwordCheckmessage" className="message">
                    {message.passwordCheck}
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
                    회원가입
                  </button>
                </div>
              </>
            ) : null}
          </form>
        </div>
      </div>
    </div>
  );
}
