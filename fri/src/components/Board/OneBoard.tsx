import { useNavigate } from "react-router-dom";
import { BoardType } from "../../pages/Community/List";
import "./board.scss";
import heart from "../../assets/like_btn.png";
import noheart from  "../../assets/noHeart.png";
import comment from "../../assets/comment_btn.png";

interface oneType {
	board : BoardType;
}

function OneBoard({board} : oneType) {
	const navigate = useNavigate();
	const now = new Date();
	const hour = Number(now.getHours());
	const minute = Number(now.getMinutes());

	return (
		<div className="board-one" onClick={() => navigate("/board/"+board.boardId)}>
			<div className="board-left">
				<div className="board-title">
					{board.title}
				</div>
				<div className="board-content">{board.content}</div>
				<div className="board-bottom">
					<div className="heart"><img src={board.like ? heart : noheart} alt="♡"/>{board.likeCount}</div>
					<div className="comment"><img src={comment} alt="○"/>{board.commentCount}</div>
					<div className="writer">{board.nickname}</div>
					{/* <div className="time">12시간 전</div> */}
				</div>
			</div>
			<div className="board-right">
				{
					board.boardThumbnailUrl !== "" ?
					<img src={board?.boardThumbnailUrl} className="thumbnail" alt="thumb" />: null
				}
			</div>
		</div>
	)
}
export default OneBoard;