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

@Service
public class ProjectTaskService {

    @Autowired
    BacklogRepository backlogRepository;

    @Autowired
    ProjectTaskRepository projectTaskRepository;

    @Autowired
    ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer BackLogSequence = backlog.getPTSequence();
            BackLogSequence++;
            backlog.setPTSequence(BackLogSequence);

            projectTask.setProjectSequence(projectIdentifier+"-"+BackLogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }
            if (projectTask.getStatus().equals("") || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }


    }

    public Iterable<ProjectTask> findBacklogById(String id) {

        Project project = projectRepository.findByProjectIdentifier(id);

        if (project == null) {
            throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
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
