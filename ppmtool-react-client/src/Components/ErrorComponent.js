import React, { Component } from "react";

class ErrorComponent extends Component {
  render() {
    return (
      <div className="container">
        <div className="alert alert-danger text-center" role="alert">
          An Error Occurred. The URL seems to be non existing!! <br />
          Contact Support support@projectmanagmenttool.com
        </div>
      </div>
    );
  }
}

export default ErrorComponent;
