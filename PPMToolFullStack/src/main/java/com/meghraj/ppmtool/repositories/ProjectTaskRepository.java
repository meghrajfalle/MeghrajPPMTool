package com.meghraj.ppmtool.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.meghraj.ppmtool.domain.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
}
