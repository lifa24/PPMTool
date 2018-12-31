package io.lifafa.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0;
    private String projectIdentifier;

    //oneToOne with project
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id",nullable = false)
    @JsonIgnore
    private Project project;

    //oneTo Many projectTasks
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy = "backlog")
    private List<ProjectTask> projectTask = new ArrayList<>();

    public Backlog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPTSequence() {
        return PTSequence;
    }

    public void setPTSequence(Integer PTSequence) {
        this.PTSequence = PTSequence;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public List<ProjectTask> getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(List<ProjectTask> projectTask) {
        this.projectTask = projectTask;
    }
}
