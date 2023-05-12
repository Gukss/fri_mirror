interface modalType {
		modal : boolean;
    setModal: React.Dispatch<React.SetStateAction<boolean>>;
  }

type Props = {
	goChat : () => Promise<void>;
}

export default function CheckModal({modal, setModal} : modalType, {goChat} : Props){

	return (
		<div className="checkmodal" style={modal ? {bottom : "40vh"} : {bottom : "-20vh"}}>
			<div className="check-text">
				달걀이 하나 깨져요ㅠ
			</div>
			<div className="button">
				<button 
					className="ok-btn" 
					onClick={() => {goChat(); setModal(false)}}
				>괜찮아요</button>
				<button className="no-btn" onClick={() => {setModal(false)}}>괜찮을리가</button>
			</div>
		</div>
	)
}