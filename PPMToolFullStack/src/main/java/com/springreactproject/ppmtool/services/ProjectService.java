package com.springreactproject.ppmtool.services;

import com.springreactproject.ppmtool.domain.Project;
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
        ///Logika do dopisania w pozniejszych etapach

        return projectRepository.save(project);
    }

}
