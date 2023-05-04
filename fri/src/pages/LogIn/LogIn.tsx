import { useState, useCallback, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { login } from "../../redux/user";
import axios from "axios";
import logo from "../../assets/images/Logo.png";
import Back from "../../components/Back";
import "./LogIn.scss";

interface SingInForm {
  email: string;
  password: string;
}

function preloadImage(src: string) {
  const img = new Image();
  img.src = src;
}

export default function LogIn() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [form, setForm] = useState<SingInForm>({ email: "", password: "" });

  useEffect(() => {
    preloadImage("/assets/images/Logo.png");
  }, []);

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
      // 로그인 axios
      axios({
        method: "post",
        url: "https://k8b204.p.ssafy.io/api/user/sign-in",
        data: form
      })
        .then((res) => {
          dispatch(login(res.data));
          setForm({ email: "", password: "" });
          navigate("/main");
        })
        .catch((err) => {
          if (err.response.status === 400)
            alert("아이디 또는 비밀번호가 잘못되었습니다 ");
        });
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
                  name="email"
                  value={form.email}
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
                value={form.password}
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
