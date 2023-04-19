import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";
import Onboarding from "./pages/Onboarding/Onboarding";
import Main from "./pages/mainPage"
import Game from "./pages/Game/gamePage"
import GameWait from "./pages/Game/gameWaitPage"

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Onboarding />} />
          <Route path='/main' element={<Main />} />
          <Route path='/wait/:room' element={<GameWait />} />
          <Route path='/game/:room' element={<Game />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
