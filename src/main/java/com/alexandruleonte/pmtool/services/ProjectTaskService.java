package com.alexandruleonte.pmtool.services;

import com.alexandruleonte.pmtool.domain.Backlog;
import com.alexandruleonte.pmtool.domain.Project;
import com.alexandruleonte.pmtool.domain.ProjectTask;
import com.alexandruleonte.pmtool.exceptions.ProjectIdException;
import com.alexandruleonte.pmtool.exceptions.ProjectNotFoundException;
import com.alexandruleonte.pmtool.repositories.BacklogRepository;
import com.alexandruleonte.pmtool.repositories.ProjectRepository;
import com.alexandruleonte.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    BacklogRepository backlogRepository;

    @Autowired
    ProjectTaskRepository projectTaskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer BackLogSequence = backlog.getPTSequence();
        BackLogSequence++;
        backlog.setPTSequence(BackLogSequence);

        projectTask.setProjectSequence(projectIdentifier+"-"+BackLogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if (projectTask.getStatus().equals("") || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
        projectService.findProjectByIdentifier(backlog_id, username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' not found");
        }

        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id+"'");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }

//    public Project saveOrUpdateProject(Project project) {
//        try {
//            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
//            if (project.getId() == null) {
//                Backlog backlog = new Backlog();
//                project.setBacklog(backlog);
//                backlog.setProject(project);
//                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
//            }
//            return projectRepository.save(project);
//        } catch (Exception e) {
//            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
//        }
//    }
//
//    public Project findProjectByIdentifier(String projectId) {
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//        if (project == null) {
//            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
//        }
//        return project;
//    }
//
//    public Iterable<Project> findAllProjects() {
//        return projectRepository.findAll();
//    }
//
//    public void deleteProjectByIdentifier(String projectId) {
//        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
//        if (project == null) {
//            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
//        }
//
//        projectRepository.delete(project);
//    }
}
