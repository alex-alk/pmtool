import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import AddProject from "./components/Project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/Project/UpdateProject";

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
          </Routes>
        </BrowserRouter>
      </div>
    </Provider>
  );
}

export default App;
