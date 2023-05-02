import { useState, useCallback } from "react";
import { useSearchParams } from "react-router-dom";
import "./Categories.scss";

interface Error {
  cate: boolean;
  name: boolean;
  place: boolean;
  people: boolean;
  area: boolean;
}
interface AddForm {
  cate: string;
  name: string;
  place: string;
  people: string;
  time: string;
  area: string;
}

interface CateProps {
  form: AddForm;
  setForm: React.Dispatch<React.SetStateAction<AddForm>>;
  error: Error;
  setError: React.Dispatch<React.SetStateAction<Error>>;
}

export default function Categories({
  form,
  setForm,
  error,
  setError
}: CateProps) {
  const [searchparams, setSearchParmas] = useSearchParams();
  const [select, setSelect] = useState<string>("");

  const handleClick = useCallback(
    (selectedCate: string) => {
      setForm({ ...form, cate: selectedCate });
      setError({ ...error, cate: true });
      if (selectedCate === select) {
        setSelect("");
      } else {
        setSelect(selectedCate);
      }
    },
    [form, select]
  );

  return (
    <div className="category-select-box">
      <div className="category-label" id="label">
        # 카테고리를 선택해주세요
      </div>
      <div className="category-select-boxs">
        <div
          className="select-box bet"
          id={select === "내기" ? "select" : ""}
          onClick={() => handleClick("내기")}
        >
          <div className="title">내기</div>
          <div className="content">
            점심시간 커피 내기?
          </div>
        </div>
        <div
          className="select-box"
          id={select === "DRINK" ? "select" : ""}
          onClick={() => handleClick("DRINK")}
        >
          <div className="title">술</div>
          <div className="content">
            이번 주 금요일에 술
          </div>
        </div>
        <div
          className="select-box"
          id={select === "MEAL" ? "select" : ""}
          onClick={() => handleClick("MEAL")}
        >
          <div className="title">밥</div>
          <div className="content">
            이번 주 금요일에 밥
          </div>
        </div>
        <div
          className="select-box"
          id={select === "GAME" ? "select" : ""}
          onClick={() => handleClick("GAME")}
        >
          <div className="title">게임</div>
          <div className="content">
            이번 주 금요일에 게임
          </div>
        </div><div
          className="select-box"
          id={select === "EXERCISE" ? "select" : ""}
          onClick={() => handleClick("EXERCISE")}
        >
          <div className="title">운동</div>
          <div className="content">
            이번 주 금요일에 운동
          </div>
        </div>
        <div
          className="select-box"
          id={select === "STUDY" ? "select" : ""}
          onClick={() => handleClick("STUDY")}
        >
          <div className="title">공부</div>
          <div className="content">이번 주 금요일에 공부</div>
        </div>
        <div
          className="select-box"
          id={select === "ETC" ? "select" : ""}
          onClick={() => handleClick("ETC")}
        >
          <div className="title">기타</div>
          <div className="content">이번 주 금요일에 만나</div>
        </div>
      </div>
      <button
        className="add-btn"
        id={select === "" ? "" : "show"}
        onClick={() => {
          if (form.cate === "내기") {
            searchparams.set("tab", "infos");
            searchparams.set("cate", "bet");
            setSearchParmas(searchparams);
          } else {
            searchparams.set("tab", "infos");
            searchparams.set("cate", select);
            setSearchParmas(searchparams);
          }
        }}
      >
        다음
      </button>
    </div>
  );
}
