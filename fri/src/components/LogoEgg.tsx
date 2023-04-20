import '../style_key.scss'

function LogoEgg() {
	return(
		<div className="main_top">
			<img src="/assets/small_logo.png" alt="fri" id="main_logo"/>
			<span className='egg_cnt'>
			<img src="/assets/egg.png" alt="egg" id="main_egg" />
			 X 5</span>
		</div>
	)
}
export default LogoEgg
