import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";

function App() {
  return (
    <Provider store={store}>
      <div className="App">
        <Header />
        <BrowserRouter>
          <Routes>
            <Route exact path="/dashboard" element={<Dashboard />} />
            <Route exact path="/addProject" element={<AddProject />} />
            <Route
              exact
              path="/updateProject/:id"
              element={<UpdateProject />}
            />
            <Route exact path="/projectBoard/:id" element={<ProjectBoard />} />
            <Route
              exact
              path="/addProjectTask/:id"
              element={<AddProjectTask />}
            />
          </Routes>
        </BrowserRouter>
      </div>
    </Provider>
  );
}

export default App;
