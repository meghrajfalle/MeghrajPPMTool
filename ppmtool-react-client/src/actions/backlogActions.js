import axios from "axios";
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK } from "./types";

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

export const getBacklog = backlog_id => async dispatch => {
  try {
    const res = await axios.get(`/api/backlog/${backlog_id}`);
    dispatch({
      type: GET_BACKLOG,
      payload: res.data
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getProjectTask = (
  backlog_id,
  pt_id,
  history
) => async dispatch => {
  try {
    const res = await axios.get(`/api/backlog/${backlog_id}/${pt_id}`);
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data
    });
  } catch (error) {
    history.push("/dashboard");
  }
};

export const updateProjectTask = (
  backlog_id,
  pt_id,
  project_task,
  history
) => async dispatch => {
  try {
    const res = await axios.patch(
      `/api/backlog/${backlog_id}/${pt_id}`,
      project_task
    );
    history.push("/projectBoard/${backlog_id}");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};
