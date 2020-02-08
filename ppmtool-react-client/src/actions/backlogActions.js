import axios from "axios";

export const addProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  await axios.post(`/api/backlog/${backlog_id}`, project_task);
  history.push(`/project/projectBoard/${backlog_id}`);
};
