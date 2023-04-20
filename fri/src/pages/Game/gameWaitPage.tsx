import { useNavigate } from "react-router-dom"

function GameWaiting() {
  const navigate = useNavigate()

  return (
    <div className="wait_game">
      <p onClick={()=>navigate('/main')}>&#60;</p>
      <div className="top">커피 내기</div>
      <img src="/assets/lgm.png" alt="lgm" className="wait_lgm"/>
      <img src="/assets/mgl.png" alt="lgm" className="wait_lgm"/>
      <img src="/assets/lgm.png" alt="lgm" className="wait_lgm"/>
      <img src="/assets/mgl.png" alt="lgm" className="wait_lgm"/>
      
    </div>
  )
}export default GameWaiting