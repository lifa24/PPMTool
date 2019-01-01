package io.lifafa.ppmtool.services;

import io.lifafa.ppmtool.domain.ProjectTask;
import io.lifafa.ppmtool.repositories.BacklogRepository;
import io.lifafa.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(){
        //PTs to be added to a specfic project, project != null, BL exists
        //set the bl to pt

        //we want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100 101
        //Update the BL SEQUENCE

        //INITIAL priority when priority null
        //INITIAL status when status is null

    }

}
