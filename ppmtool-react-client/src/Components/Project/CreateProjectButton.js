import React from "react";
import { Link } from "react-router-dom";

const CreateProjectButton = () => {
  return (
    <React.Fragment>
      {/*This React.Fragment wraps below code under one parent and will not shown in the html rendering as like div */}
      <Link to="/addProject" className="btn btn-lg btn-info">
        Create a Project
      </Link>
    </React.Fragment>
  );
};
export default CreateProjectButton;
