package com.springreactproject.ppmtool.services;

import com.springreactproject.ppmtool.domain.Backlog;
import com.springreactproject.ppmtool.domain.Project;
import com.springreactproject.ppmtool.exceptions.ProjectIdException;
import com.springreactproject.ppmtool.repository.BacklogRepository;
import com.springreactproject.ppmtool.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


///glowne zalozenie warstwy serwisowej jest takie, zeby nie pakowac warstwy logicznej do kontrollera jesli to nie jest konieczne
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;



    public Project saveOrUpdateProject(Project project)
    {

        try{

            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());


            if(project.getId()==null){
                Backlog backlog= new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }


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


    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }


    public void deleteProjectByIdentifier(String projectId){
        Project project=projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project==null)
        {
            throw new ProjectIdException("Cannot delete: project with a given id "+ projectId+" doesn't exists");

        }
        projectRepository.delete(project);
    }



    /*

    public void updateProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot update: project with a given id " + projectId + " doesn't exists");

        }

        projectRepository.save(project);



    }
    *////////////////////okazuje sie ze nie potrzebne na chwile obecna

}
