package com.meghraj.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meghraj.ppmtool.domain.ProjectTask;
import com.meghraj.ppmtool.repositories.BacklogRepository;
import com.meghraj.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(){

        //PTs to be added to a specific project, project !=null, BL exists
        //set the BL to pt

        //We want our project sequence to be like this: IDPRO-1 IDPRO-2  .... 100 101
        // Update the BL SEQUENCE

        //INITIAL priority when priority null
        //INITIAL status when status is null
        return null;
    }

}
