package com.intern.app.qeema.model.mapper;

import com.intern.app.qeema.model.DtoModels.AcceptanceResponse;
import com.intern.app.qeema.model.entities.Intern;
import com.intern.app.qeema.model.DtoModels.InternRequest;
import com.intern.app.qeema.model.DtoModels.InternResponse;

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

    public static AcceptanceResponse AcceptanceResponseMapper(Intern intern){
        AcceptanceResponse acceptanceResponse= AcceptanceResponse.builder().name(intern.getName())
                .desiredTrack(intern.getDesiredTrack())
                .id(intern.getId())
                .gpa(intern.getGpa())
                .acceptance(intern.isAccepted())
                .build();
        return acceptanceResponse;
    }

}
