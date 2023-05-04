import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// 상태 타입
interface UserState {
  userId: number;
  location: string;
  heart: number;
  roomId: number;
  gameRoomId: string;
}

// 초기 상태
const initialState: UserState = {
  userId: 0,
  location: "",
  heart: 0,
  roomId: 0,
  gameRoomId: ""
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    login(state, action: PayloadAction<UserState>) {
      return action.payload;
    },
    logout() {
      return initialState;
    },
    meeting(state, action: PayloadAction<number>){
      state.roomId = action.payload;
    },
    game(state, action : PayloadAction<string>) {
      state.gameRoomId = action.payload;
    },
    useegg(state, action : PayloadAction<number>){
      state.heart--;
    }
  }
});

export default userSlice.reducer;
export const { login, logout, meeting, game, useegg } = userSlice.actions;
