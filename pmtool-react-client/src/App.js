import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import AddProject from "./components/Project/AddProject";

function App() {
  return (
    <div className="App">
      <Header />
      <BrowserRouter>
        <Routes>
          <Route exact path="/dashboard" element={<Dashboard />} />
          <Route exact path="/addProject" element={<AddProject />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
