package com.springreactproject.ppmtool.web;


import com.springreactproject.ppmtool.domain.ProjectTask;
import com.springreactproject.ppmtool.services.MapValidationErrorService;
import com.springreactproject.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    ProjectTaskService projectTaskService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult bindingResult,
                                                     @PathVariable String backlog_id){
       ResponseEntity<?> errorMap=mapValidationErrorService.MapValidationService(bindingResult);
       if(errorMap!=null){
           return errorMap;
       }

       ProjectTask projectTask1=projectTaskService.addProjectTask(backlog_id, projectTask);

       return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);




    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
        return projectTaskService.findBacklogById(backlog_id);

    }


    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id){
        ProjectTask projectTask=projectTaskService.findPtByProjectSeequence(backlog_id, pt_id);

        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }


    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                            @PathVariable String backlog_id,@PathVariable String pt_id){

        ResponseEntity<?> errorMap=mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null){
            return errorMap;
        }
        ProjectTask updatedTask=projectTaskService.updateByProjectSequnce(projectTask, backlog_id, pt_id);

        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);

    }


    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id){
        projectTaskService.deletePtByProjectSequence(backlog_id, pt_id);

        return new ResponseEntity<String>("Project Task "+ pt_id+" was deleted sucessfully", HttpStatus.OK);
    }


}
