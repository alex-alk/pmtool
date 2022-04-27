package com.alexandruleonte.pmtool.services;

import com.alexandruleonte.pmtool.domain.Project;
import com.alexandruleonte.pmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        return projectRepository.save(project);
    }
}
