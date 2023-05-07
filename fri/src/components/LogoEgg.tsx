import "../style_key.scss"
import { useSelector } from "react-redux"
import fri from "../assets/small_logo.png"
import egg from "../assets/egg.png"
import Back from "../components/Back"
import { RootState } from "../redux/store"

function LogoEgg() {
	const egg_cnt = useSelector((state: RootState) => {
		return state.strr.heart
	})

	const heart = () => {
		const result = []
		for(let i = 0; i < egg_cnt; i++){
			result.push(<img src={egg} alt="egg" id="main_egg" key={i}/>)
		}
		return result;
	}

	return(
		<div className="main_top">
			<Back />
			<img src={fri} alt="fri" id="main_logo"/>
			<div className="egg_cnt">
				{heart()}
			</div>
		</div>
	)
}
export default LogoEgg
