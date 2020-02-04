package com.meghraj.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;

import com.meghraj.ppmtool.domain.Backlog;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String Identifier);
}
