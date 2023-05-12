import { useNavigate } from "react-router-dom"
import back from "../assets/back.png"

function Back() {
  const navigate = useNavigate();

  return (
    <>
      <img src={back} id="back" alt="back" onClick={() => navigate("/main")}/>
    </>
  )
}
export default Back;