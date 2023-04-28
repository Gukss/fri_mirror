import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
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

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Onboarding />} />
          <Route path="/main" element={<Main />} />
          <Route path="/more" element={<More />} />
          <Route path="/wait/:room" element={<GameWait />} />
          <Route path="/game/:room" element={<Game />} />
          <Route path="/login" element={<LogIn />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/add" element={<Add />} />
          <Route path="/my" element={<My />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
