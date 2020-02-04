package com.meghraj.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meghraj.ppmtool.domain.Backlog;
import com.meghraj.ppmtool.domain.ProjectTask;
import com.meghraj.ppmtool.repositories.BacklogRepository;
import com.meghraj.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        //Exceptions: Project not found

        //PTs to be added to a specific project, project !=null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

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
        if (projectTask.getPriority() == null){ //In the future we need projectTask.getPriority() == 0 to handle the form
            projectTask.setPriority(3);
        }

        //INITIAL status when status is null
        if(projectTask.getStatus() =="" || projectTask.getStatus() == null){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

}