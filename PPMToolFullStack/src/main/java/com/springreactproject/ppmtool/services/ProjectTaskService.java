package com.springreactproject.ppmtool.services;


import com.springreactproject.ppmtool.domain.Backlog;
import com.springreactproject.ppmtool.domain.Project;
import com.springreactproject.ppmtool.domain.ProjectTask;
import com.springreactproject.ppmtool.exceptions.ProjectNotFoundException;
import com.springreactproject.ppmtool.repository.BacklogRepository;
import com.springreactproject.ppmtool.repository.ProjectRepository;
import com.springreactproject.ppmtool.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private ProjectService projectService;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, Principal principal){


            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, principal).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer BacklogSequence=backlog.getPTSequence();
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //add sequence to the project task
            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getPriority()==null||projectTask.getPriority()==0){
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus()==""||projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);







    }



    public Iterable<ProjectTask> findBacklogById(String id, Principal principal){

        Project project=projectService.findProjectByIdentifier(id, principal);
        if(!project.getProjectLeader().equals(principal.getName()))
        {
            throw new ProjectNotFoundException("Project doesnt exist or you are not the owner");
        }
//        if(project==null)
//        {
//            throw new ProjectNotFoundException("Project with ID: " +id+" does not exist");
//        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);


    }

    public ProjectTask findPtByProjectSeequence(String backlog_id, String pt_id, Principal principal){

        projectService.findProjectByIdentifier(backlog_id, principal);


        ProjectTask projectTask=projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask==null)
        {
            throw new ProjectNotFoundException("Project task "+pt_id+" does not eexist");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project task "+pt_id+" does not exist in project: "+backlog_id);

        }

       return projectTask;
    }


    public ProjectTask updateByProjectSequnce(ProjectTask updatedTask, String backlog_id, String pt_id, Principal principal){
        ProjectTask projectTask=findPtByProjectSeequence(backlog_id, pt_id, principal);
        projectTask=updatedTask;
        return projectTaskRepository.save(projectTask);

    }

    public void deletePtByProjectSequence(String backlog_id, String pt_id, Principal principal){

        ProjectTask projectTask=findPtByProjectSeequence(backlog_id, pt_id, principal);
        projectTaskRepository.delete(projectTask);
    }



}
