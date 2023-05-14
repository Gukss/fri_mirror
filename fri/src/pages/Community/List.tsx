import Topheader from "../../components/LogoEgg";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Nav from "../../components/navEgg";
import "./List.scss"

export type BoardType = {
	boardId : number;
	title : string;
	content : string;
	likesCount : number;
	commentCount : number;
	createAt : string;
	boardImageThumbnail : string;
}

function List() {
	const [data, setData] = useState<BoardType[]>([])
	const api_url = process.env.REACT_APP_REST_API;
	const navigate = useNavigate();
	const [category, setCategory] = useState("HOT");
	const [isnav, setIsnav] = useState(false);
	const userId = useSelector((state: RootState) => {
			return state.strr.userId;
		});

//   const changeCate = async (area: string) => {
//     const header = {
//       Authorization: Number(userId),
//       "Content-Type": "application/json"
//     };
//     try {
//       const res = await axios.get(api_url + `board/list?boardCategory=${category}`, {
//         headers: header
//       });
//       setData(res.data)
//     } catch (e) {
//       console.log(e);
//     }
//   };
    
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
        <div className="boardpage">
            <div className="board-nav">
        <Topheader />
        <ul className="board_cate">
          <li
            id={category == "HOT" ? "select" : "HOT"}
            onClick={() => {
              setCategory("HOT");
              // changeCate("HOT");
            }}
          >
            Hot
          </li>
          <li
            id={category == "RESTAURANT" ? "select" : "RESTAURANT"}
            onClick={() => {
              setCategory("RESTAURANT");
              // changeCate("RESTAURANT");
            }}
          >
            맛집
          </li>
          <li
            id={category == "INFORMATION" ? "select" : "INFORMATION"}
            onClick={() => {
              setCategory("INFORMATION");
              // changeCate("INFORMATION");
            }}
          >
            정보
          </li>
          <li
            id={category == "GITHUB" ? "select" : "GITHUB"}
            onClick={() => {
              setCategory("GITHUB");
              // changeCate("GITHUB");
            }}
          >
            Git
          </li>
          <li
            id={category == "JOB" ? "select" : "JOB"}
            onClick={() => {
              setCategory("JOB");
              // changeCate("JOB");
            }}
          >
            Job
          </li>
          <li
            id={category == "FREE" ? "select" : "FREE"}
            onClick={() => {
              setCategory("FREE");
              // changeCate("FREE");
            }}
          >
            자율
          </li>
          <li
            id={category == "ASK" ? "select" : "ASK"}
            onClick={() => {
              setCategory("ASK");
              // changeCate("ASK");
            }}
          >
            QnA
          </li>
        </ul>
      </div>
				<Nav isnav={isnav} setIsnav={setIsnav} />
		</div>
    )
}
export default List;