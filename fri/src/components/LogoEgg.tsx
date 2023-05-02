import "../style_key.scss"
import fri from "../assets/small_logo.png"
import egg from "../assets/egg.png"
import Back from "../components/Back"

function LogoEgg() {
	return(
		<div className="main_top">
			<Back />
			<img src={fri} alt="fri" id="main_logo"/>
			<div className="egg_cnt">
				<img src={egg} alt="egg" id="main_egg" />
				<img src={egg} alt="egg" id="main_egg" />
				<img src={egg} alt="egg" id="main_egg" />
				<img src={egg} alt="egg" id="main_egg" />
				<img src={egg} alt="egg" id="main_egg" />
			</div>
		</div>
	)
}
export default LogoEgg
