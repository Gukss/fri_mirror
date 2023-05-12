/*
redux의 경우 
1. combineRecuer를 통해 합친 reducer를 store에 전달
2. thunk
3. thunk를 apply할 수 있는 applyMiddleware
4. composeWithDevTools(리덕스 dev tool)를 전부 작성해 주었지만

redux/toolkit은 configureStore 만 있으면 된다.(위의 4가지 모두 자동화)

Redux와 redux-persist를 사용하여 상태 관리를 구현하고 있습니다. 
redux-persist는 리덕스 스토어의 일부 또는 전체를 로컬 스토리지에 저장하고, 
새로고침 후에도 상태를 유지할 수 있게 해줍니다.
*/

import { combineReducers, configureStore } from "@reduxjs/toolkit";
import storage from "redux-persist/lib/storage";
import userReducer from "./user";

import {
  persistStore,
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER
} from "redux-persist";

type SaveTokenState = ReturnType<typeof userReducer>;

export interface RootState {
  strr: SaveTokenState;
}

const rootReducer = combineReducers({
  strr: userReducer
});

const persistConfig = {
  key: "root",
  storage,
  whitelist: ["strr"] // 로컬 스토리지에 저장해서 상태를 유지하고 싶다면 화이트리스트에 리듀서를 담아주세요.
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER]
      }
    })
});

export const persistor = persistStore(store);
