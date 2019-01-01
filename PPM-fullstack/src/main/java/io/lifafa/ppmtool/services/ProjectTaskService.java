package io.lifafa.ppmtool.services;

import io.lifafa.ppmtool.domain.Backlog;
import io.lifafa.ppmtool.domain.ProjectTask;
import io.lifafa.ppmtool.repositories.BacklogRepository;
import io.lifafa.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        //PTs to be added to a specfic project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //set the bl to pt
        projectTask.setBacklog(backlog);

        //we want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100 101
        Integer BacklogSequence = backlog.getPTSequence();

        //Update the BL SEQUENCE
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);

        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //INITIAL priority when priority null
        //In the future we need projectPriority()==0 to handle the form
        if(projectTask.getPriority()==null){
            projectTask.setPriority(3);
        }

        //INITIAL status when status is null
        if(projectTask.getStatus()=="" || projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id){
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

}
