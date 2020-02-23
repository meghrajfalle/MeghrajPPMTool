package com.meghraj.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meghraj.ppmtool.domain.Backlog;
import com.meghraj.ppmtool.domain.Project;
import com.meghraj.ppmtool.domain.User;
import com.meghraj.ppmtool.exceptions.ProjectIdException;
import com.meghraj.ppmtool.repositories.BacklogRepository;
import com.meghraj.ppmtool.repositories.ProjectRepository;
import com.meghraj.ppmtool.repositories.UserRepository;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);  //sets the relationship between user and project
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId()==null){ // We don't want to create a new Backlog object only when we are updating the existing object
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if (project.getId() !=null){ // means we are we have sent update request
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project Id: '" + project.getProjectIdentifier().toUpperCase() + "' already exists !");
        }

    }

    public Project findProjectByProjectIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project Id: '" + projectId + "' doesnot exists !");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier (String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Cannot find Project with ID: '" + projectId + "'. This Project doesnot exists !");
        }

        projectRepository.delete(project);
    }
}
