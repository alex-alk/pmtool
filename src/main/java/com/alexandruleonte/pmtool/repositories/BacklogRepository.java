package com.alexandruleonte.pmtool.repositories;

import com.alexandruleonte.pmtool.domain.Backlog;
import com.alexandruleonte.pmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
    Backlog findByProjectIdentifier(String projectIdentifier);
}
