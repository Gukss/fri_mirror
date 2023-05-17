import Topheader from "../../components/LogoEgg";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import { useEffect, useState } from "react";
import Sort from "../../assets/Sort.png";
import OneBoard from "../../components/Board/OneBoard";
import { useNavigate } from "react-router-dom";
import Pencil from "../../assets/Pencil.png"
import axios from "axios";
import Nav from "../../components/navEgg";
import "./List.scss"

export type BoardType = {
  nickname : string;
	boardId : number;
	title : string;
	content : string;
	likeCount : number;
	commentCount : number;
	createAt : string;
	boardThumbnailUrl : string;
  like : boolean;
}

function List() {
	const [data, setData] = useState<BoardType[]>();
  const [sort, setSort] = useState<string>("최신순");
  const [sortdata, setSortdata] = useState<BoardType[]>();
  const [select, setSelect] = useState(false);
	const api_url = process.env.REACT_APP_REST_API;
	const navigate = useNavigate();
	const [category, setCategory] = useState("HOT");
	const [isnav, setIsnav] = useState(false);
	const userId = useSelector((state: RootState) => {
			return state.strr.userId;
		});


  // function sortData(pick:string){
  //   setSort(pick);
  //   if(pick === "최신순") setSortdata(data);
  //   else if(pick === "인기순")
  //   {setSortdata(data)
  //   sortdata?.sort((a, b) => a.likeCount - b.likeCount)}
  //   else if(pick === "좋아요") setSortdata(data?.filter((d) => d.like))

  //   setSelect(false);
  // }

  const changeCate = async (area: string) => {
    const header = {
      Authorization: Number(userId),
      "Content-Type": "application/json"
    };
    try {
      const res = await axios.get(api_url + `board/list?boardCategory=${area}`, {
        headers: header
      });
      setData(res.data)
      setSortdata(res.data.boardList);
    } catch (e) {
      console.log(e);
    }
  };
    
    useEffect(() => {
        const getData = async () => {
            const header = {
              Authorization: Number(userId),
              "Content-Type": "application/json"
            };
            try {
              const res = await axios.get(api_url + `board/list?boardCategory=HOT`, {headers: header});
              setData(res.data.boardList);
              setSortdata(res.data.boardList);
            }catch(e){console.log(e)};
        }
        getData();
    }, [])

    return (
      <div className="boardpage">
        <div className="board-nav">
          <Topheader />
          <ul className="board_cate">
            <li
              id={category == "HOT" ? "select" : "HOT"}
              onClick={() => {
                setCategory("HOT");
                changeCate("HOT");
              }}
            >
              Hot
            </li>
            <li
              id={category == "RESTAURANT" ? "select" : "RESTAURANT"}
              onClick={() => {
                setCategory("RESTAURANT");
                changeCate("RESTAURANT");
              }}
            >
              맛집
            </li>
            <li
              id={category == "INFORMATION" ? "select" : "INFORMATION"}
              onClick={() => {
                setCategory("INFORMATION");
                changeCate("INFORMATION");
              }}
            >
              정보
            </li>
            <li
              id={category == "GITHUB" ? "select" : "GITHUB"}
              onClick={() => {
                setCategory("GITHUB");
                changeCate("GITHUB");
              }}
            >
              Git
            </li>
            <li
              id={category == "JOB" ? "select" : "JOB"}
              onClick={() => {
                setCategory("JOB");
                changeCate("JOB");
              }}
            >
              Job
            </li>
            <li
              id={category == "FREE" ? "select" : "FREE"}
              onClick={() => {
                setCategory("FREE");
                changeCate("FREE");
              }}
            >
              자유
            </li>
            <li
              id={category == "ASK" ? "select" : "ASK"}
              onClick={() => {
                setCategory("ASK");
                changeCate("ASK");
              }}
            >
              QnA
            </li>
          </ul>
        </div>
        <div className="board_medium">
          <div className="write_board">글쓰기<img src={Pencil} alt="pencil" id="pencil"/></div>
          <div className="board_sort_group">
          <div className="board_sort"><img src={Sort} alt="=" />{sort}</div>
          {/* {
            select ? 
            <div className="sort_select"> */}
              {/* <div className="sort_new" id="new" onClick={() => sortData("최신순")}>최신순</div> */}
              {/* <div className="sort_pop" id="pop" onClick={() => sortData("인기순")}>인기순</div> */}
              {/* <div className="sort_like" id="like" onClick={() => sortData("좋아요")}>좋아요</div> */}
              {/* <div className="sort_scrap" id="scrap" onClick={() => sortData("스크랩")}>스크랩</div> */}
              {/* <div className="sort_my" id="my" onClick={() => sortData("내 글")}>내 글</div> */}
            {/* </div>
            : null
          } */}
        </div>
      </div>
      <div className="board-content">
        {
          sortdata?.length ?
          sortdata.map((board) => (
            <OneBoard board={board} key={board.boardId}/>
          ))
          :
          <div className="no_content">아직 게시물이 없어요.</div>       
        }
      </div>
				<Nav isnav={isnav} setIsnav={setIsnav} />
		</div>
    )
}
export default List;