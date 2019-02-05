package com.springreactproject.ppmtool.services;

import com.springreactproject.ppmtool.domain.Project;
import com.springreactproject.ppmtool.exceptions.ProjectIdException;
import com.springreactproject.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


///glowne zalozenie warstwy serwisowej jest takie, zeby nie pakowac warstwy logicznej do kontrollera jesli to nie jest konieczne
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;



    public Project saveOrUpdateProject(Project project)
    {

        try{

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID "+project.getProjectIdentifier().toUpperCase()+" already exists");
        }


    }

    public Project findProjectByIdentifier(String projectId){


        Project project=projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project==null){
            throw new ProjectIdException("Project ID "+ projectId+" doesn't exists");
        }
        else
            return project;

    }

}
