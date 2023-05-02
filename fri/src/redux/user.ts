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
    }
  }
});

export default userSlice.reducer;
export const { login, logout } = userSlice.actions;
