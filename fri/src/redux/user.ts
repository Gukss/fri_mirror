import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// 상태 타입
interface UserState {
  userId: number;
  location: string;
  heart: number;
  roomId: string;
  gameRoomId: string;
  nickname: string;
  anonymousProfileImageUrl: string;
  major: boolean | null;
  year: string;
}

// 초기 상태
const initialState: UserState = {
  userId: 0,
  location: "",
  heart: 0,
  roomId: "",
  gameRoomId: "",
  nickname: "",
  anonymousProfileImageUrl: "",
  year: "",
  major: null
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
    meeting(state, action: PayloadAction<string>) {
      state.roomId = action.payload;
    },
    game(state, action: PayloadAction<string>) {
      state.gameRoomId = action.payload;
    },
    useegg(state, action: PayloadAction<number>) {
      state.heart = state.heart - action.payload;
    },
    changeNick(state, action: PayloadAction<string>) {
      state.nickname = action.payload;
    },
    changeProfile(state, action: PayloadAction<string>) {
      state.anonymousProfileImageUrl = action.payload;
    }
  }
});

export default userSlice.reducer;
export const {
  login,
  logout,
  meeting,
  game,
  useegg,
  changeNick,
  changeProfile
} = userSlice.actions;
