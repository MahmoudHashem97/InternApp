package com.intern.app.qeema.model.helper;

import com.intern.app.qeema.model.entities.Intern;
import com.intern.app.qeema.model.models.InternRequest;
import com.intern.app.qeema.model.models.InternResponse;

public class InternMapper {
    public static Intern internRequestMapper(InternRequest internRequest){

       return Intern.builder()
               .email(internRequest.getEmail())
               .gpa(internRequest.getGpa())
               .name(internRequest.getName())
               .desiredTrack(internRequest.getDesiredTrack())
               .university(internRequest.getUniversity())
               .cvUrl(internRequest.getCvUrl())
               .build();
    }
    public static InternResponse internResponseMapper(Intern intern){
        return InternResponse.builder()
                .name(intern.getName())
                .desiredTrack(intern.getDesiredTrack())
                .id(intern.getId())
                .gpa(intern.getGpa())
                .build();
    }
}
