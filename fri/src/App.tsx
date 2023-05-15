import { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { store, persistor } from "./redux/store";
import "./App.css";
import Onboarding from "./pages/Onboarding/Onboarding";
import Main from "./pages/Main/mainPage";
import More from "./pages/Main/morePage";
import Game from "./pages/Game/gamePage";
import GameWait from "./pages/Game/gameWaitPage";
import LogIn from "./pages/LogIn/LogIn";
import SignUp from "./pages/SignUp/SignUp";
import Add from "./pages/Add/Add";
import My from "./pages/My/MyPage";
import Chat from "./pages/Chat/Chat";
import Not from "./pages/NotFound";
import MyEdit from "./pages/My/MyEdit";
import Board from "./pages/Community/List";
import BoardDetail from "./pages/Community/ComDetail";
import AddBoard from "./pages/Community/AddCom";

function App() {
  
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <div className="App">
          <Router>
            <Routes>
              <Route path="/" element={<Onboarding />} />
              <Route path="/main" element={<Main />} />
              <Route path="/more" element={<More />} />
              <Route path="/game/:room" element={<Game />} />
              <Route path="/wait/:room" element={<GameWait />} />
              <Route path="/login" element={<LogIn />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/add" element={<Add />} />
              <Route path="/my" element={<My />} />
              <Route path="/my/edit" element={<MyEdit />} />
              <Route path="/chatting/:id" element={<Chat />} />
              <Route path="/board" element={<Board />} />
              <Route path="/board/:id" element={<BoardDetail />} />
              <Route path="/newboard/" element={<AddBoard />} />
              <Route path="*" element={<Not />} />
            </Routes>
          </Router>
        </div>
      </PersistGate>
    </Provider>
  );
}

export default App;
