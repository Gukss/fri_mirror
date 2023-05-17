import Back from "../../assets/back.png";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Pencil from "../../assets/Pencil.png"
import Profile from "../../assets/my_img.png"
import Trash from "../../assets/Trash.png"
import heart from "../../assets/like_btn.png";
import comment from "../../assets/comment_btn.png";
import scrap from "../../assets/scrap_btn.png"
import Comment from "../../components/Board/CommentList"
import axios from "axios";
import "./detail.scss"

export type BoardType = {
	boardId : number;
	title : string;
	content : string;
	likesCount : number;
	commentCount : number;
	createAt : string;
	boardImageThumbnail : string;
}

function ComDetail() {
	const [data, setData] = useState<BoardType[]>([])
	const api_url = process.env.REACT_APP_REST_API;
	const navigate = useNavigate();
	const [category, setCategory] = useState("HOT");
	const [isnav, setIsnav] = useState(false);
	const userId = useSelector((state: RootState) => {
			return state.strr.userId;
		});
	// const Image = ["https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAxMjNfMjA2%2FMDAxNjExMzI5MjgzODc1.cgcbb-bGxMkzT2jOtZb3JOWi-P3-g5gp_nM6JQVS_aYg.agdfRlQYtp9z-MYgh3XfRtE2i4QOGek5SEUOXszwiw8g.JPEG.jungtw0123%2Fb47281594850ac6901c5448a87f9c2ac.jpg&type=a340"]
	const Image = ["https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAxMjNfMjA2%2FMDAxNjExMzI5MjgzODc1.cgcbb-bGxMkzT2jOtZb3JOWi-P3-g5gp_nM6JQVS_aYg.agdfRlQYtp9z-MYgh3XfRtE2i4QOGek5SEUOXszwiw8g.JPEG.jungtw0123%2Fb47281594850ac6901c5448a87f9c2ac.jpg&type=a340",
"https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMDdfMTA0%2FMDAxNjEyNjUwNDAyMTYx.XzkEBVqaETaovHcm73anO-UeD42Oaei7jyk_m4H1Xosg.YzXpQdbcnQvv2c6w-Ux6U3TvWhn1YkEpDUQwYk7tR_Ig.JPEG.cocacola1585%2F0C0BC3F4-8318-41B2-B219-941BFD642547.jpeg&type=ofullfill340_600_png",
"https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMjAyMDJfMTc0%2FMDAxNjQzNzc3NzY0NjY3.BPgAx8f5JmcX6FhQ-PvUhY_IogE7sTnp1h1ZF6M3EWgg.yY1bI4nkRwFWgRcPsX6oyQXazv7vcO2KJg2uAEM0Ozcg.PNG.sinnam88%2F%25C1%25F6%25BA%25EA%25B8%25AE_%25BD%25BA%25C6%25A9%25B5%25F0%25BF%25C0_%25B9%25E8%25B0%25E6%25C8%25AD%25B8%25E9_%25B0%25ED%25C8%25AD%25C1%25FA_%25B3%25EB%25C6%25AE%25BA%25CF%25C4%25C4%25C7%25BB%25C5%25CD_%25B8%25F0%25C0%25BD_%252849%2529.png&type=a340"];

    // useEffect(() => {
    //     const getData = async () => {
    //         const header = {
    //           Authorization: Number(userId),
    //           "Content-Type": "application/json"
    //         };
    //         try {
    //           const res = await axios.get(api_url + "board", {headers: header});
    //           setData(res.data);
    //         }catch(e){console.log(e)};
    //     }
    //     getData();
    // }, [])

    return (
      <div className="board_detail">
		<img src={Back} alt="<" id="back" onClick={() => navigate('/board')}/>
		<div className="detail_scroll">
			<div className="detail_title">Title</div>
			<div className="detail-rd">
				<div className="write_board">수정<img src={Pencil} alt="pencil" id="pencil"/></div>
				<div className="delete_board">삭제<img src={Trash} alt="trash" id="trash"/></div>
			</div>
			<div className="detail-content">
				<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Neque dolore dicta voluptatibus facilis, consequatur nobis numquam molestiae nihil in? Quibusdam repudiandae similique a, quae placeat provident veritatis repellat suscipit fugit?
					Lorem ipsum dolor sit, amet consectetur adipisicing elit. Est magnam, repellendus corporis beatae, facere laboriosam exercitationem quod veritatis vel impedit consequatur non temporibus a ea ab molestiae quis pariatur magni?
					Lorem ipsum dolor sit amet consectetur, adipisicing elit. Blanditiis non quidem magni quis, obcaecati amet magnam dolores accusantium autem harum delectus reiciendis sequi dicta aperiam recusandae laboriosam, eius quam laudantium.
				</p>
				<div className="detail_contet-bottom">
					<div className="bottom-writer"><img src={Profile} alt="writer"/>내이름은 작성자 입니다</div>
					<div className="bottom-btn">
						<div className="heart"><img src={heart} alt="♡"/> 999+</div>
						<div className="comment"><img src={comment} alt="○"/>999+</div>
						<div className="scrap"><img src={scrap} alt="□"/>999+</div>
					</div>
				</div>
			</div>
			<div className="detail_image">
				{
					Image.length && Image.length> 1 &&				
					Image.map((url, index) => (
						<div className="imgs"  key={index} ><img src={url} className="imgs" alt="img"/></div>
					))
				}
				{
					Image.length && Image.length === 1 &&								
					<div className="img"><img src={Image[0]} alt="img"/></div>
				}
			</div>
			<Comment />
	  	</div>
	</div>
    )
}
export default ComDetail;