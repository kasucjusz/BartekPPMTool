import React, { Component } from "react";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { logout } from "../../actions/securityActions";

class Header extends Component {
  logout(e) {
    e.preventDefault();
    this.props.logout();
  }
  render() {
    const { validToken, user } = this.props.security;
    const userLinks = (
      <ul className="navbar-nav ml-auto">
        <li className="nav-item">
          <a className="nav-link " href="#" onClick={this.logout.bind(this)}>
            Logout
          </a>
        </li>
        <li className="nav-item">
          <Link className="nav-link" to="/dashboard">
            <i className="fas fa-user-circle mr-1" />
            {user.fullName}
          </Link>
        </li>
      </ul>
    );

    const guestLinks = (
      <ul className="navbar-nav ml-auto">
        <li className="nav-item">
          <a className="nav-link " href="/register">
            Sign Up
          </a>
        </li>
        <li className="nav-item">
          <a className="nav-link" href="/login">
            Login
          </a>
        </li>
      </ul>
    );

    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <a className="navbar-brand" href="/landing">
            Personal Project Management Tool
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mobile-nav"
          >
            <span className="navbar-toggler-icon" />
          </button>

          <div className="collapse navbar-collapse" id="mobile-nav">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item">
                <a className="nav-link" href="/dashboard">
                  Dashboard
                </a>
              </li>
            </ul>
          </div>
          {validToken ? userLinks : guestLinks}
        </div>
        <div className="pull-right">
          <ul className="navbar-nav ml-auto">
            <li className="nav-item">
              <a className="nav-link " href="/about">
                About
              </a>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

Header.propTypes = {
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
)(Header);
