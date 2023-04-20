import { useCallback } from "react";
import "./Inputs.scss";
interface AddForm {
  cate: string;
  name: string;
  place: string;
  people: string;
  time: string;
}
interface AddFormMsg {
  name: string;
  place: string;
  people: string;
}

interface Error {
  cate: boolean;
  name: boolean;
  place: boolean;
  people: boolean;
}

interface InputProps {
  form: AddForm;
  setForm: React.Dispatch<React.SetStateAction<AddForm>>;
  error: Error;
  setError: React.Dispatch<React.SetStateAction<Error>>;
  message: AddFormMsg;
  setMessage: React.Dispatch<React.SetStateAction<AddFormMsg>>;
}

export default function Inputs({
  form,
  setForm,
  error,
  setError,
  message,
  setMessage
}: InputProps) {
  const handleInput = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const { name, value } = e.target;
      setForm({ ...form, [name]: value });
    },
    [form]
  );

  const handleBlur = useCallback(
    (e: React.FocusEvent<HTMLInputElement, Element>) => {
      const { name, value } = e.target;
      if (name === "name") {
        if (!value || value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다." });
          setMessageColor("red", "name");
          setError({ ...error, [name]: false });
        } else {
          setMessage({ ...message, [name]: "가능합니다!" });
          setMessageColor("green", "name");
          setError({ ...error, [name]: true });
        }
      } else if (name === "place") {
        if (!value || value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다." });
          setMessageColor("red", "place");
          setError({ ...error, [name]: false });
        } else {
          setMessage({ ...message, [name]: "가능합니다!" });
          setMessageColor("green", "place");
          setError({ ...error, [name]: true });
        }
      } else if (name === "people") {
        if (value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다!" });
          setMessageColor("red", "people");
          setError({ ...error, [name]: false });
        } else if (!value) {
          setMessage({ ...message, [name]: "숫자를 입력해 주세요!" });
          setMessageColor("red", "people");
          setError({ ...error, [name]: false });
        } else if (Number(value) < 2 || Number(value) > 10) {
          setMessage({ ...message, [name]: "2~10명만 내기가 가능합니다!" });
          setMessageColor("red", "people");
          setError({ ...error, [name]: false });
        } else {
          setMessage({ ...message, [name]: "가능합니다!" });
          setMessageColor("green", "people");
          setError({ ...error, [name]: true });
        }
      }
    },
    [message, error, form]
  );

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
    <>
      <div className="add-input-box">
        <div className="input-label" id="label">
          # 방 이름 설정
        </div>
        <div className="add-input">
          <input
            placeholder="방 이름을 입력해주세요."
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
      <div className="add-input-box">
        <div className="input-label" id="label">
          # 내기 후 만날 장소
        </div>
        <div className="add-input">
          <input
            placeholder="내기 종료 후 만날 장소를 입력해주세요."
            type="text"
            id="place"
            name="place"
            onChange={handleInput}
            onBlur={handleBlur}
          />
        </div>
        <div id="placemessage" className="message">
          {message.place}
        </div>
      </div>
      <div className="add-input-box">
        <div className="input-label" id="label">
          # 내기 인원 <span>(숫자만 입력, 2~10명)</span>
        </div>
        <div className="add-input">
          <input
            placeholder="총인원을 입력해주세요. ex) 5, 6"
            type="number"
            id="people"
            name="people"
            onChange={handleInput}
            onBlur={handleBlur}
          />
        </div>
        <div id="peoplemessage" className="message">
          {message.people}
        </div>
      </div>
      <div className="add-input-box">
        <div className="input-label time" id="label">
          ※ 중요 <br />
          내기가 끝나고 5분뒤 입력하신 장소로 모여주세요!
        </div>
      </div>
    </>
  );
}
