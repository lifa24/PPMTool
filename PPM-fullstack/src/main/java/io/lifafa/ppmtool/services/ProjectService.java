package io.lifafa.ppmtool.services;

import io.lifafa.ppmtool.domain.Backlog;
import io.lifafa.ppmtool.domain.Project;
import io.lifafa.ppmtool.domain.User;
import io.lifafa.ppmtool.exceptions.ProjectIdException;
import io.lifafa.ppmtool.repositories.BacklogRepository;
import io.lifafa.ppmtool.repositories.ProjectRepository;
import io.lifafa.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){
        try{

            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());

            String id= project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(id);

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(id);
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(id));
            }

            return projectRepository.save(project);

        }catch(Exception e){
            throw new ProjectIdException("Project ID '"+project.getDescription().toUpperCase()+ "' already exist ");
        }

    }

    public Project findProjectByIdentifier(String projectId){


        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '"+projectId+ "' does not exist ");

        }
        return project;
    }

    public Iterable<Project> findAllProject(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project ==null){
            throw new ProjectIdException("Cannot Project with ID '"+projectId+"'. This project doesn't exist.");
        }

        projectRepository.delete(project);
    }
}
