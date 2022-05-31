import React, { Component } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Backlog from "./Backlog";

class ProjectBoard extends Component {
  render() {
    const { id } = this.props.params;
    return (
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        <Backlog />
      </div>
    );
  }
}

function withRouter() {
  return function (props) {
    const params = useParams();
    const navigate = useNavigate();
    return <ProjectBoard {...props} params={params} navigate={navigate} />;
  };
}

export default withRouter(ProjectBoard);
