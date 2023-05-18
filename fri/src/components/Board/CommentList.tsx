import { useRef, useState, useCallback } from "react";
import { RootState } from "../../redux/store";
import { useSelector } from "react-redux";
import { CommentType } from "../../pages/Community/ComDetail";
import axios from "axios";
import Up from "../../assets/Arrow upward.png";
import "./board.scss"

interface commentType {
	comment : CommentType[];
	board : number;
	cmt : number;
	setCmt : React.Dispatch<React.SetStateAction<number>>
}

function CommentList({comment, board, setCmt, cmt} : commentType) {
    const textareaRef = useRef<HTMLTextAreaElement>(null);
    const btnRef = useRef<HTMLButtonElement>(null);
    const [text, setText] = useState<string>("");
    const [data, setData] = useState(comment);
    const api_url = process.env.REACT_APP_REST_API;
    const userId = useSelector((state:RootState) => {
        return state.strr.userId;
    })

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
    
		const delCmt = async (cmtId : number) => {
			const datas = {
				"commentId" : cmtId,
				"isDelete" : true, 
			}        
			const header = {
				"Content-Type" : "application/json",
				"Authorization" : userId
			}
			
			try {
				await axios.patch(api_url + 'comment/delete', datas, {headers : header});
				setData(data.filter((d) => d.commentId !== cmtId))
				setCmt(cmt-1)
			}catch(e){console.log(e)}
		}

	const subCmt = async () => {
		const datas = {
			"boardId" : board,
			"content" : text, 
		}        
		const header = {
			"Content-Type" : "application/json",
			"Authorization" : userId
		}
		
		try {
			const res = await axios.post(api_url + 'comment', datas, {headers : header});
			const newData = {...res.data, "identity" : "na"}
			setData([...data, newData])
			setCmt(cmt+1)
		}catch(e){console.log(e)}
		
		setText("")
	}


	return (
	<div className="comment">
		<div className="comment_box">
			{
				data.length ? 
				data.map((cmt, index) => (
					<div className="cmt" key={index}>
						<div className="cmt_top">
						<div className="cmt_name">{
							cmt.identity === "na" && "나" || cmt.identity === "nam" && `익명${index+1}` || cmt.identity === "writer" && "작성자"}
						</div>
					{
						cmt.identity === "na" &&
						<div className="mycmt">
							{/* <div className="cmt_r">수정</div> */}
							<div className="cmt_d" onClick={() => delCmt(cmt?.commentId)}>삭제</div>
						</div>
					}
				</div>
					<div className="cmt_content">{cmt.content}</div>
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