package com.meghraj.ppmtool.repositories;


import org.springframework.data.repository.CrudRepository;

import com.meghraj.ppmtool.domain.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
}
