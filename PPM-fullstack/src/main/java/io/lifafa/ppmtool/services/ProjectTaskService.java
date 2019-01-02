package io.lifafa.ppmtool.services;

import io.lifafa.ppmtool.domain.Backlog;
import io.lifafa.ppmtool.domain.Project;
import io.lifafa.ppmtool.domain.ProjectTask;
import io.lifafa.ppmtool.exceptions.ProjectNotFoundException;
import io.lifafa.ppmtool.repositories.BacklogRepository;
import io.lifafa.ppmtool.repositories.ProjectRepository;
import io.lifafa.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try{
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
            if(projectTask.getPriority()==0|| projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }

            //INITIAL status when status is null
            if(projectTask.getStatus()=="" || projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);

        }catch(Exception e){
            throw new ProjectNotFoundException("Project Not Found!");
        }


    }

    public Iterable<ProjectTask> findBacklogById(String id){

        Project project = projectRepository.findByProjectIdentifier(id);
        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findByProjectSequence(String backlog_id,String pt_id){
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);

        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' does not exist in project: '"+backlog_id+"' ");
        }

        return projectTask;
    }

    public ProjectTask updateProjectSequence(ProjectTask updateTask,String backlog_id,String pt_id){
        ProjectTask projectTask = findByProjectSequence(backlog_id,pt_id);

        projectTask = updateTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id,String pt_id){
        ProjectTask projectTask = findByProjectSequence(backlog_id,pt_id);


        projectTaskRepository.delete(projectTask);
    }

}
