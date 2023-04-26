import { useState, useCallback, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import Categories from "./Categories/Categories";
import Inputs from "./Inputs/Inputs";
import "./Add.scss";

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

export default function Add() {
  const navigate = useNavigate();
  const [searchparams, setSearchParmas] = useSearchParams();
  const tab = searchparams.get("tab");

  const [form, setForm] = useState<AddForm>({
    cate: "",
    name: "",
    time: "내기 종료 후 5분뒤",
    place: "카페",
    people: ""
  });

  const [message, setMessage] = useState<AddFormMsg>({
    name: "",
    place: "",
    people: ""
  });

  const [error, setError] = useState<Error>({
    cate: false,
    name: false,
    place: false,
    people: false
  });

  useEffect(() => {
    if (tab === null || !["cate", "infos"].includes(tab)) {
      setSearchParmas("?tab=cate");
    }
  }, []);

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      console.log(error);
      const isFormValid = Object.values(error).every((value) => value === true);

      if (isFormValid) {
        // 방생성 axios
        console.log("방생성~");
      } else {
        console.log("다시");
        // 모달 띄우거나
        // 안채운거 빨갛게
      }
    },
    [form, error, message]
  );

  return (
    <div className="add-room">
      <div
        onClick={() => {
          navigate(-1);
        }}
        style={{ position: "absolute", top: 0, left: 0 }}
      >
        뒤로
      </div>
      <div className="add-container">
        <div className="small-logo">
          <img src="/assets/images/Small_Logo.png" alt="Logo" />
        </div>
        <div className="add-title">미팅방 생성</div>
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
