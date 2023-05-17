import { useNavigate } from "react-router-dom";

import "./board.scss";
import heart from "../../assets/like_btn.png";
import comment from "../../assets/comment_btn.png";
import pic from "../../assets/notfound.png";

function OneBoard() {
	const navigate = useNavigate();

	return (
		<div className="board-one" onClick={() => navigate("/board/1")}>
			<div className="board-left">
				<div className="board-title">
					난 내기에서 져본 적이 없지
				</div>
				<div className="board-content">가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하</div>
				<div className="board-bottom">
					<div className="heart"><img src={heart} alt="♡"/> 999+</div>
					<div className="comment"><img src={comment} alt="○"/>999+</div>
					<div className="writer">안녕하세요저는누구입니다</div>
					<div className="time">12시간 전</div>
				</div>
			</div>
			<div className="board-right">
				<img src={pic} className="thumbnail" alt="thumb" />
			</div>
		</div>
	)
}
export default OneBoard;