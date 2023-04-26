import { GameType } from "../pages/Main/mainPage";

interface roomType {
  room: GameType;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

function GameDetail({ room, open, setOpen }: roomType) {
  const { place, title, current_cnt, total_cnt } = room;
  return (
    <>
      {open ? (
        <div className="room_detail">
          <div className="room_modal">
            <div className="title">
              <span>{title}</span>{" "}
              <span style={{ color: "#FFC000" }} onClick={() => setOpen(false)}>
                X
              </span>
            </div>
            <p className="place"># {place}</p>
            <p className="cnt">
              {" "}
              참가자{" "}
              <span>
                ( {current_cnt} / {total_cnt} )
              </span>
            </p>
            <div className="join_game">참여하기</div>
          </div>
        </div>
      ) : null}
    </>
  );
}
export default GameDetail;
