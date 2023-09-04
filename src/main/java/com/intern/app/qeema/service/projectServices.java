package com.intern.app.qeema.service;

import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.model.DtoModels.InternProject;
import com.intern.app.qeema.model.mapper.ProjectMapper;
import com.intern.app.qeema.reprosatory.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class projectServices {
    @Autowired
    private ProjectRepo projectRepo;



    public Projects addProject(InternProject internProject){
        Projects newProject = ProjectMapper.projectsResponse(internProject);
        Projects addedProject = projectRepo.save(newProject);

//        Projects addedProject = projectRepo
//                .findByDescriptionAndName
//                        (internProject.getDescription(),internProject.getName());
        return addedProject;
    }

}
