package com.springreactproject.ppmtool.services;

import com.springreactproject.ppmtool.domain.Backlog;
import com.springreactproject.ppmtool.domain.Project;
import com.springreactproject.ppmtool.domain.User;
import com.springreactproject.ppmtool.exceptions.ProjectIdException;
import com.springreactproject.ppmtool.exceptions.ProjectNotFoundException;
import com.springreactproject.ppmtool.repository.BacklogRepository;
import com.springreactproject.ppmtool.repository.ProjectRepository;
import com.springreactproject.ppmtool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;


///glowne zalozenie warstwy serwisowej jest takie, zeby nie pakowac warstwy logicznej do kontrollera jesli to nie jest konieczne
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;



    public Project saveOrUpdateProject(Project project, String username)
    {


        if(project.getId()!=null){
            Project existingProject=projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject!=null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }

            else if(existingProject==null){
                throw new ProjectNotFoundException("Project with ID "+project.getProjectIdentifier()+"cannot be updated bc it doesnt exist");
            }
        }


        try{

            User user=userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
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

    public Project findProjectByIdentifier(String projectId, Principal principal){


        Project project=projectRepository.findByProjectIdentifierAndProjectLeader(projectId.toUpperCase(), principal.getName());
        if(project==null){
            throw new ProjectIdException("Project ID "+ projectId+" doesn't exists");
        }

        if(!project.getProjectLeader().equals(principal.getName())){
            throw new ProjectNotFoundException("Project with ID "+projectId+"Doesnt belong to user "+principal.getName());
        }
        else
            return project;

    }


    public Iterable<Project> findAllProjects(String username){

        return projectRepository.findAllByProjectLeader(username);
    }


    public void deleteProjectByIdentifier(String projectId, Principal principal){
        Project project=findProjectByIdentifier(projectId, principal);

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
