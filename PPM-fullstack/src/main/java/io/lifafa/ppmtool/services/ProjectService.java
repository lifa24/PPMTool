package io.lifafa.ppmtool.services;

import io.lifafa.ppmtool.domain.Project;
import io.lifafa.ppmtool.exceptions.ProjectIdException;
import io.lifafa.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);

        }catch(Exception e){
            throw new ProjectIdException("Project ID '"+project.getDescription().toUpperCase()+ "' already exist ");
        }

    }
}
