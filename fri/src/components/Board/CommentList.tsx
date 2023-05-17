import { useRef, useState, useCallback } from "react";
import Up from "../../assets/Arrow upward.png";
import "./board.scss"

const comment = [
    {
        "commentId" : 1,
        "content" : "맛있긴해", 
        "createdAt" : "yyyy-mm-dd HH:MM:SS",
        "Identity" : "writer"
    },
    {
        "commentId" : 2,
        "content" : "ㅇㅈ",
        "createdAt" : "yyyy-mm-dd HH:MM:SS",
        "Identity" : "na"
    },
    {
        "commentId" : 1,
        "content" : "맛있긴해", 
        "createdAt" : "yyyy-mm-dd HH:MM:SS",
        "Identity" : "nam"
    }
]

function CommentList() {
    const textareaRef = useRef<HTMLTextAreaElement>(null);
    const btnRef = useRef<HTMLButtonElement>(null);
    const [text, setText] = useState<string>("");
    const [cmt, setData] = useState(comment);
    const submitMessage = useCallback(
        (e: React.ChangeEvent<HTMLTextAreaElement>) => {
          setText(e.target.value);
    
          const textarea = textareaRef.current;
          if (textarea) {
            textarea.classList.remove("autoHeight");
            if (textarea.scrollHeight > 40) {
              textarea.classList.add("autoHeight");
            }
          }
        },
        [text]
      );        
    

    function subCmt() {
        const data = {
            "commentId" : 1,
            "content" : text, 
            "createdAt" : "yyyy-mm-dd HH:MM:SS",
            "Identity" : "nam"
        }
        
        setData((prev) => [...prev, data])
        setText("")

    }


    return (
        <div className="comment">
            <div className="comment_box">
                {
                    cmt.length ? 
                    cmt.map((cmt, index) => (
                        <div className="cmt" key={index}><div className="cmt_name">{
                            cmt.Identity === "na" && "나" || cmt.Identity === "nam" && `익명${index+1}` || cmt.Identity === "writer" && "작성자"
                        }</div>{cmt.content}
                        {
                            cmt.Identity === "na" &&
                            <div className="mycmt">
                                <div className="cmt_r">수정</div>
                                <div className="cmt_d">삭제</div>
                            </div>
                        }
                        </div>
                    ))
                    :
                    null
                }
            </div>
            <div className="comment_bottom">
                <div className="text-input">
                <textarea
                    ref={textareaRef}
                    placeholder="댓글 여기에"
                    onChange={submitMessage}
                    value={text}
                />
                {text !== "" ? (
                    <button
                    className="send-message"
                    ref={btnRef}
                    onClick={subCmt}
                    >
                    <img src={Up} alt="up-arrow" />
                    </button>
                ) : (
                    ""
                )}
                </div>
            </div>
          </div>
    )
}
export default CommentList;