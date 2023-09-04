package com.intern.app.qeema.model.mapper;

import com.intern.app.qeema.model.DtoModels.InternProject;
import com.intern.app.qeema.model.entities.Projects;

public class ProjectMapper {
    public static Projects projectsResponse (InternProject internProject){
        Projects newProject =  Projects.builder()
                .name(internProject.getName()).
                description(internProject.getDescription())
                .build();
        return newProject ;
    }
}
