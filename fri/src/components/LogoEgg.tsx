import '../style_key.scss'
import fri from '../assets/small_logo.png'
import egg from "../assets/egg.png"

function LogoEgg() {
	return(
		<div className="main_top">
			<img src={fri} alt="fri" id="main_logo"/>
			<span className='egg_cnt'>
			<img src={egg} alt="egg" id="main_egg" />
			 X 5</span>
		</div>
	)
}
export default LogoEgg
