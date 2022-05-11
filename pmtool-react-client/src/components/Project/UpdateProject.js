import React, { useEffect, useState } from "react";
import { getProject, createProject } from "../../actions/projectActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classNames from "classnames";
import { useParams, useNavigate } from "react-router-dom";

function UpdateProject(props) {
  const params = useParams();
  const navigate = useNavigate();

  const [projectName, setProjectName] = useState("");
  const [projectIdentifier, setProjectIdentifier] = useState("");
  const [description, setDescription] = useState("");
  const [start_date, setStartDate] = useState("");
  const [end_date, setEndDate] = useState("");
  const [id, setId] = useState("");
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (!props.project.id) {
      props.getProject(params.id, navigate);
    } else {
      if (props.project.errors) {
        setErrors(props.project.errors);
      }

      setProjectName(props.project.projectName);
      setDescription(props.project.description);
      setStartDate(props.project.start_date);
      setEndDate(props.project.end_date);
      setProjectIdentifier(props.project.projectIdentifier);
      setId(props.project.id);
      setErrors(props.errors);
    }
  }, [props, params.id, navigate]);

  function onSubmit(e) {
    e.preventDefault();
    const updateProject = {
      projectName: projectName,
      description: description,
      start_date: start_date,
      end_date: end_date,
      projectIdentifier: projectIdentifier,
      id: id,
    };
    props.createProject(updateProject, navigate);
  }

  return (
    <div className="project">
      <div className="container">
        <div className="row">
          <div className="col-md-8 m-auto">
            <h5 className="display-4 text-center">Update Project form</h5>
            <hr />
            <form onSubmit={onSubmit}>
              <div className="form-group">
                <input
                  type="text"
                  className={classNames("form-control form-control-lg", {
                    "is-invalid": errors.projectName,
                  })}
                  placeholder="Project Name"
                  name="projectName"
                  value={projectName}
                  onChange={(e) => setProjectName(e.target.value)}
                />
                {errors.projectName && (
                  <div className="invalid-feedback">{errors.projectName}</div>
                )}
              </div>
              <div className="form-group">
                <input
                  type="text"
                  className="form-control form-control-lg"
                  placeholder="Unique Project ID"
                  name="projectIdentifier"
                  value={projectIdentifier}
                  disabled
                />
              </div>
              <div className="form-group">
                <textarea
                  className={classNames("form-control form-control-lg", {
                    "is-invalid": errors.description,
                  })}
                  placeholder="Project Description"
                  name="description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                ></textarea>
                {errors.description && (
                  <div className="invalid-feedback">
                    {props.errors.description}
                  </div>
                )}
              </div>
              <h6>Start Date</h6>
              <div className="form-group">
                <input
                  type="date"
                  className="form-control form-control-lg"
                  name="start_date"
                  value={start_date}
                  onChange={(e) => setStartDate(e.target.value)}
                />
              </div>
              <h6>Estimated End Date</h6>
              <div className="form-group">
                <input
                  type="date"
                  className="form-control form-control-lg"
                  name="end_date"
                  value={end_date}
                  onChange={(e) => setEndDate(e.target.value)}
                />
              </div>

              <input type="submit" className="btn btn-primary btn-block mt-4" />
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

UpdateProject.propTypes = {
  getProject: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired,
  createProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  project: state.project.project,
  errors: state.errors,
});

export default connect(mapStateToProps, { getProject, createProject })(
  UpdateProject
);
