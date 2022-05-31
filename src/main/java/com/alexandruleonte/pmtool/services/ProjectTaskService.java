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

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer BackLogSequence = backlog.getPTSequence();
            BackLogSequence++;
            backlog.setPTSequence(BackLogSequence);

            projectTask.setProjectSequence(projectIdentifier+"-"+BackLogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            System.out.println(projectTask);
            if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
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

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' not found");
        }

        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id+"'");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
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
