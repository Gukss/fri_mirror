import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";
import Onboarding from "./pages/Onboarding/Onboarding";

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Onboarding />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
