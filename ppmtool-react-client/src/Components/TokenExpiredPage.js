import React, { Component } from "react";
import { Link } from "react-router-dom";

export default class TokenExpiredPage extends Component {
  render() {
    return (
      <div className="container">
        <div className="alert alert-danger text-center" role="alert">
          Session Timedout. Your Token is Expired. Please login again!
          <br />
        </div>
        <hr />
        <Link className="btn btn-lg btn-success btn-secondary mr-2" to="/login">
          Login
        </Link>
        <br /> Or <br />
        <div className="alert alert-info text-center" role="alert">
          Goto Landing Page
          <br />
        </div>
        <Link className="btn btn-lg btn-primary mr-2" to="/">
          Home
        </Link>
      </div>
    );
  }
}
