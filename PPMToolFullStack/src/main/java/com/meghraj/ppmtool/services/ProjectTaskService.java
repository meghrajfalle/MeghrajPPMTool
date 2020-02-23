package com.meghraj.ppmtool.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meghraj.ppmtool.domain.Backlog;
import com.meghraj.ppmtool.domain.Project;
import com.meghraj.ppmtool.domain.ProjectTask;
import com.meghraj.ppmtool.exceptions.ProjectNotFoundException;
import com.meghraj.ppmtool.repositories.BacklogRepository;
import com.meghraj.ppmtool.repositories.ProjectRepository;
import com.meghraj.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask,String username){

        //Exceptions: Project not found
        /*
            ProjectNotFound : "Project not Found"
         */

            //PTs to be added to a specific project, project !=null, BL exists
            Backlog backlog = projectService.findProjectByProjectIdentifier(projectIdentifier,username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);

            //set the BL to pt
            projectTask.setBacklog(backlog);

            //We want our project sequence to be like this: IDPRO-1 IDPRO-2  .... 100 101
            Integer BacklogSequence = backlog.getPTSequence();

            // Update the BL SEQUENCE
            BacklogSequence++;

            // Set BL sequence;
            backlog.setPTSequence(BacklogSequence);

            //Add Sequence to Project Task
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0 ){ //In the future we need projectTask.getPriority() == 0 to handle the form
                projectTask.setPriority(3);
            }

            //INITIAL status when status is null
            if(projectTask.getStatus() =="" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);


    }

    public Iterable<ProjectTask> findBacklogById(String id,String username) {

        projectService.findProjectByProjectIdentifier(id,username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

        //make sure we are searching on an existing backlog
        projectService.findProjectByProjectIdentifier(backlog_id,username);

        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null)
        {
            throw new ProjectNotFoundException("Project with Task: '"+ pt_id +"' not found.");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        //if(projectTask.getProjectIdentifier().equals(backlog_id)) this condition should also work used in tutorial
        //if(projectTask != null && backlog != null && projectTask.getBacklog()!= backlog){
            if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project with Task: '"+ pt_id +"' does not exist or belong in project: "+backlog_id);
        }

        return projectTask;
    }

    //update project task
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){

        //find existing project task
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);

        //replace it with the updated task
        projectTask = updatedTask;

        //save update
        return projectTaskRepository.save(projectTask);
    }

    public void deleteByProjectSequence(String backlog_id, String pt_id, String username){
       ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id, username);

//        Backlog backlog = projectTask.getBacklog();
//        List<ProjectTask> pts = backlog.getProjectTasks(); // Gets the list of all the project tasks of this backlog
//        pts.remove(projectTask);
//        backlogRepository.save(backlog);

       // projectTaskRepository.delete(projectTask);
    }

}
