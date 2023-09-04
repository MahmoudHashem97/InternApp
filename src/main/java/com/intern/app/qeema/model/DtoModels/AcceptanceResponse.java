package com.intern.app.qeema.model.DtoModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptanceResponse {
    private DesiredTrack desiredTrack;
    private int id ;
    private String name ;
    private Double gpa ;
    private boolean acceptance;

}
