import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import { getBacklog } from "../../actions/backlogActions";
import { connect } from "react-redux";
import PropTypes from "prop-types";

const boardAlgorithm = (errors, project_tasks) => {
  if (project_tasks.length < 1) {
    if (errors.projectNotFound) {
      return (
        <div className="alert alert-danger text-center" role="alert">
          {errors.projectNotFound}
        </div>
      );
    } else {
      return (
        <div className="alert alert-info text-center" role="alert">
          No Project Tasks on this board
        </div>
      );
    }
  } else {
    return <Backlog project_tasks_props={project_tasks} />;
  }
};

let BoardContent;

class ProjectBoard extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBacklog(id);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog;
    const { errors } = this.state;
    BoardContent = boardAlgorithm(errors, project_tasks);
    return (
      <div className="container">
        <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        {boardAlgorithm(errors, project_tasks)}
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStatetoProps = state => ({
  backlog: state.backlog,
  errors: state.errors
});

export default connect(
  mapStatetoProps,
  { getBacklog }
)(ProjectBoard);
