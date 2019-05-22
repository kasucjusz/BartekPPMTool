import React, { Component } from "react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { logout } from "../../actions/securityActions";

class Landing extends Component {
  logout(e) {
    e.preventDefault();
    this.props.logout();
  }

  render() {
    const { validToken, user } = this.props.security;

    const guestLinks = (
      <div className="col-md-12 text-center">
        <p className="lead">
          Create an account to start your journey with our tool.
        </p>
        <hr />
        <a href="/register" className="btn btn-lg btn-primary mr-2">
          Sign Up
        </a>
        <a href="/login" className="btn btn-lg btn-secondary mr-2">
          Login
        </a>
      </div>
    );

    const userLinks = (
      <div>
        <h3 className="display-10 mb-1">Welcome,{user.fullName} </h3>
      </div>
    );

    return (
      <div className="landing">
        <div className="light-overlay landing-inner text-dark">
          <div className="container">
            <div className="row">
              <div className="col-md-12 text-center">
                <h1 className="display-3 mb-4">
                  Personal Project Management Tool
                </h1>

                {validToken ? userLinks : guestLinks}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Landing.propTypes = {
  security: PropTypes.object.isRequired,
  logout: PropTypes.func.isRequired
};

function mapStateToProps(state) {
  return {
    security: state.security
  };
}

export default connect(
  mapStateToProps,
  { logout }
)(Landing);
