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

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){

        try{
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer BacklogSequence=backlog.getPTSequence();
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //add sequence to the project task
            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getPriority()==0||projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus()==""||projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        }
        catch (Exception e)
        {
            throw new ProjectNotFoundException("Project Not Found");
        }






    }



    public Iterable<ProjectTask> findBacklogById(String id){

        Project project=projectRepository.findByProjectIdentifier(id);
        if(project==null)
        {
            throw new ProjectNotFoundException("Project with ID: " +id+" does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);


    }

    public ProjectTask findPtByProjectSeequence(String backlog_id, String pt_id){

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: " +backlog_id+" does not exist");
        }

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


    public ProjectTask updateByProjectSequnce(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask=findPtByProjectSeequence(backlog_id, pt_id);
        projectTask=updatedTask;
        return projectTaskRepository.save(projectTask);

    }

    public void deletePtByProjectSequence(String backlog_id, String pt_id){

        ProjectTask projectTask=findPtByProjectSeequence(backlog_id, pt_id);
        projectTaskRepository.delete(projectTask);
    }



}
