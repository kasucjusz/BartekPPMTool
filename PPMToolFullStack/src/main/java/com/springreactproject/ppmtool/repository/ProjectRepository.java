package com.springreactproject.ppmtool.repository;


import com.springreactproject.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {

    Project findByProjectIdentifier(String projectIdentifier);

    Project findByProjectIdentifierAndProjectLeader(String projectIdentifier, String username);


    @Override
    Iterable<Project> findAll();


    Iterable<Project>findAllByProjectLeader(String username);





}
