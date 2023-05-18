import { useState, useCallback } from "react";
import { RootState } from "../../redux/store";
import { useSelector } from "react-redux";
import Back from "../../assets/back.png";
import logo from "../../assets/small_logo.png";
import axios from "axios";
import "./AddCom.scss";
import { type } from "os";

interface PostForm {
  title: string;
  cate: string;
  content: string;
}

interface Error {
  title: boolean;
  cate: boolean;
  content: boolean;
}

const Cate = ["맛집", "정보", "Git", "Job", "자유", "Q&A"];
const api_url = process.env.REACT_APP_REST_API;

function AddCom() {
  const [form, setForm] = useState<PostForm>({
    title: "",
    cate: "",
    content: ""
  });
  const [error, setError] = useState<Error>({
    title: false,
    cate: false,
    content: false
  });
  const [message, setMessage] = useState<PostForm>({
    title: "",
    cate: "",
    content: ""
  });
  const [file, setFile] = useState<File[]>([]);
  const [images, setImages] = useState<string[]>([]);

  const [isAreaDown, setIsAreaDown] = useState(false);

  const userId = useSelector((state: RootState) => {
    return state.strr.userId;
  });

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
    (
      e:
        | React.ChangeEvent<HTMLInputElement>
        | React.ChangeEvent<HTMLTextAreaElement>
    ) => {
      const { name, value } = e.target;
      setForm({ ...form, [name]: value });
      if (name === "content") {
        if (!value || value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다." });
          setMessageColor("red", "content");
          setError({ ...error, [name]: false });
        } else {
          setMessage({ ...message, [name]: "가능합니다!" });
          setMessageColor("green", "content");
          setError({ ...error, [name]: true });
        }
      }
    },
    [form, error]
  );

  const handleBlur = useCallback(
    (
      e:
        | React.ChangeEvent<HTMLInputElement>
        | React.ChangeEvent<HTMLTextAreaElement>
    ) => {
      const { name, value } = e.target;

      if (name === "title") {
        if (!value || value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다." });
          setMessageColor("red", "title");
          setError({ ...error, [name]: false });
        } else {
          setMessage({ ...message, [name]: "가능합니다!" });
          setMessageColor("green", "title");
          setError({ ...error, [name]: true });
        }
      } else if (name === "content") {
        if (!value || value.trim().length === 0) {
          setMessage({ ...message, [name]: "필수 입력 사항입니다." });
          setMessageColor("red", "content");
          setError({ ...error, [name]: false });
        } else {
          setMessage({ ...message, [name]: "가능합니다!" });
          setMessageColor("green", "content");
          setError({ ...error, [name]: true });
        }
      }
    },
    [message, error, form]
  );

  const createPost = async (formData: FormData) => {
    try {
      const header = {
        // "Content-Type": "multipart/form-data",
        Authorization: userId
      };

      formData.forEach((data) => console.log(data));

      const res = await axios.post(api_url + "board", formData, {
        headers: header
      });
      console.log(res);
    } catch (e) {
      console.log(e);
    }
  };

  console.log("사진", file);

  const handleSubmit = useCallback(
    (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      const isFormValid = Object.values(error).every((value) => value === true);
      if (isFormValid) {
        let boardCategory = "";

        if (form.cate === "맛집") boardCategory = "RESTAURANT";
        else if (form.cate === "정보") boardCategory = "INFORMATION";
        else if (form.cate === "Git") boardCategory = "GITHUB";
        else if (form.cate === "Job") boardCategory = "JOB";
        else if (form.cate === "자유") boardCategory = "FREE";
        else if (form.cate === "Q&A") boardCategory = "ASK";

        const data = {
          boardCategory: boardCategory,
          title: form.title,
          content: form.content
        };

        const formData = new FormData();
        formData.append(
          "createBoardRequest",
          new Blob([JSON.stringify(data)], {
            type: "application/json"
          })
        );

        console.log("파일", file);

        if (file.length > 0) {
          file.forEach((fileObj) => {
            // const fileData = new File([JSON.stringify(fileObj)], fileObj.name, {
            //   type: "multipart/form-data"
            // });
            formData.append("boardImage", fileObj);
          });
        }

        createPost(formData);
      } else {
        alert("잘못된 입력입니다.");
      }
    },
    [form, error, message, file]
  );

  const handleImg = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      e.preventDefault();

      const newFiles = e.target.files;
      const fileArray: File[] = [...file];
      const imgArray: string[] = [...images];

      if (newFiles) {
        for (let i = 0; i < newFiles.length; i++) {
          const newFile = newFiles[i];
          // api용 파일 저장
          fileArray.push(newFile);

          // 렌더용 사진 저장
          const ImageUrl = URL.createObjectURL(newFile);
          imgArray.push(ImageUrl);
        }
      }
      setFile(fileArray);
      setImages(imgArray);
    },
    [file, images]
  );

  const removeImg = useCallback(
    (index: number) => {
      // images 배열에서 해당 index를 가진 이미지 삭제
      const newImages = [...images];
      newImages.splice(index, 1);
      setImages(newImages);

      // file 배열에서 해당 index를 가진 이미지 삭제
      const newFile = [...file];
      newFile.splice(index, 1);
      setFile(newFile);
    },
    [images, file]
  );

  return (
    <div className="add-post">
      <img src={Back} alt="<" id="back" />
      <div className="add-container">
        <div className="small-logo">
          <img src={logo} alt="Logo" />
        </div>
        <form className="add-form" onSubmit={handleSubmit}>
          <div className="add-input-box">
            <div className="input-label" id="label">
              # 제목
            </div>
            <div className="add-input">
              <input
                placeholder="게시물의 제목을 입력해주세요."
                type="text"
                id="title"
                name="title"
                onChange={handleInput}
                onBlur={handleBlur}
              />
            </div>
            <div id="titlemessage" className="message">
              {message.title}
            </div>
          </div>
          <div className="add-input-box">
            <div className="input-label" id="label">
              # 카테고리 설정
            </div>
            <div className="component">
              <button
                className={`select-button ${form.cate === "" ? "" : "checked"}`}
                type="button"
                id="postCate"
                name="postCate"
                onClick={onClickArea}
              >
                <div className={`select ${form.cate === "" ? "" : "checked"}`}>
                  {form.cate === "" ? "카테고리를 선택해주세요." : form.cate}
                </div>
              </button>
              <div className="message">{message.cate}</div>
              {isAreaDown && (
                <div className="drop-down">
                  {Cate.map((cate) => (
                    <button
                      className="option"
                      value={cate}
                      key={cate}
                      onClick={onClickAreaOption}
                      name="cate"
                    >
                      {cate}
                    </button>
                  ))}
                </div>
              )}
            </div>
          </div>
          <div className="add-input-box">
            <div className="input-label" id="label">
              # 본문
            </div>
            <div className="add-textarea">
              <textarea
                placeholder="게시물의 내용을 입력해주세요."
                id="content"
                name="content"
                onChange={handleInput}
                onBlur={handleBlur}
              />
            </div>
            <div id="contentmessage" className="message">
              {message.content}
            </div>
          </div>
          <div className="add-input-box post-img">
            {images.length > 0 && (
              <div className="image-cnt">{images.length}개</div>
            )}
            <div className="input-label" id="label">
              # 사진 <span>(선택)</span>
            </div>
            <div className="img-add-box">
              <div className="add-input-img">
                <div>+</div>
                <input
                  type="file"
                  id="post-img"
                  multiple
                  accept="image/*"
                  onChange={handleImg}
                />
              </div>
              <div className="post_images">
                {images.map((url, index) => {
                  return (
                    <div className="post_img" key={index}>
                      <div className="btn-x" onClick={() => removeImg(index)}>
                        X
                      </div>
                      <img src={url} alt="uploaded image" />
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
          <button
            className="post-btn"
            id={
              Object.values(error).every((value) => value === true)
                ? "show"
                : ""
            }
          >
            게시하기
          </button>
        </form>
      </div>
    </div>
  );
}
export default AddCom;
