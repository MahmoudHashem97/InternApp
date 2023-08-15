package com.intern.app.qeema.service;

import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.model.models.InternProject;
import com.intern.app.qeema.reprosatory.InternRepo;
import com.intern.app.qeema.reprosatory.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class projectServices {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private InternRepo internRepo;


    public Projects addProject(InternProject internProject){
        Projects newProject = Projects.builder().name(internProject.getName()).description(internProject.getDescription()).build();
        projectRepo.save(newProject);

        Projects addedProject = projectRepo
                .findByDescriptionAndName
                        (internProject.getDescription(),internProject.getName());


        return addedProject;
    }

}
