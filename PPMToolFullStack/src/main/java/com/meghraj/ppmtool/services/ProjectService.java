package com.meghraj.ppmtool.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meghraj.ppmtool.domain.Backlog;
import com.meghraj.ppmtool.domain.Project;
import com.meghraj.ppmtool.domain.User;
import com.meghraj.ppmtool.exceptions.ProjectIdException;
import com.meghraj.ppmtool.exceptions.ProjectNotFoundException;
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

        //project.getId !=null // means project exists and request is for update
        if(project.getId() !=null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject !=null && !(existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if (existingProject ==null){
                throw new ProjectNotFoundException("Project wth ID: '"+project.getId()+"' cannot be updated because it doesn't exist ");
            }
        }

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

    public Project findProjectByProjectIdentifier(String projectId, String username){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project Id: '" + projectId + "' doesnot exists !");
        }

        if (!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not Found in your account!");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier (String projectId,String username ){
        projectRepository.delete(findProjectByProjectIdentifier(projectId,username));
    }
}
