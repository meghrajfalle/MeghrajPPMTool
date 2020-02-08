import axios from "axios";
import { GET_ERRORS } from "./types";

export const addProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  try {
    await axios.post(`/api/backlog/${backlog_id}`, project_task);
    history.push(`/projectBoard/${backlog_id}`);
    dispatch({
      // this is required to remove whatever validations error you might have had before submitting the form clear it from the redux store state
      type: GET_ERRORS, // so that when we open the same form for updation we don't still see the same validation errors
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};
