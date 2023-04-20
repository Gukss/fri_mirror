import { useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import "./Categories.scss";

interface Error {
  cate: boolean;
  name: boolean;
  place: boolean;
  people: boolean;
}
interface AddForm {
  cate: string;
  name: string;
  place: string;
  people: string;
  time: string;
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
  const navigate = useNavigate();
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
      console.log(form);
    },
    [form, select]
  );

  return (
    <div className="category-select-box">
      <div className="category-label" id="label">
        # 방 카테고리를 선택해주세요!
      </div>
      <div className="category-select-boxs">
        <div
          className="select-box"
          id={select === "내기" ? "select" : ""}
          onClick={() => handleClick("내기")}
        >
          <div className="title">내기</div>
          <div className="content">
            미니게임으로 <br /> 내기할 사람?
          </div>
        </div>
        <div
          className="select-box"
          id={select === "술" ? "select" : ""}
          onClick={() => handleClick("술")}
        >
          <div className="title">술</div>
          <div className="content">
            술이 마시고
            <br /> 싶은 사람
          </div>
        </div>
        <div
          className="select-box"
          id={select === "밥" ? "select" : ""}
          onClick={() => handleClick("밥")}
        >
          <div className="title">밥</div>
          <div className="content">
            밥이 먹고 <br />
            싶은 사람
          </div>
        </div>
        <div
          className="select-box"
          id={select === "게임" ? "select" : ""}
          onClick={() => handleClick("게임")}
        >
          <div className="title">게임</div>
          <div className="content">
            PC게임, 보드게임 <br /> 하고 싶은 사람!
          </div>
        </div>
        <div
          className="select-box"
          id={select === "공부" ? "select" : ""}
          onClick={() => handleClick("공부")}
        >
          <div className="title">공부</div>
          <div className="content">
            공부, 스터디, 취준 <br /> 할 사람
          </div>
        </div>
        <div
          className="select-box"
          id={select === "기타" ? "select" : ""}
          onClick={() => handleClick("기타")}
        >
          <div className="title">기타</div>
        </div>
      </div>
      <button
        className="add-btn"
        id={select === "" ? "" : "show"}
        onClick={() => {
          navigate("/add?tab=others");
        }}
      >
        다음
      </button>
    </div>
  );
}
