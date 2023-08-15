package com.intern.app.qeema.model.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InternRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

//    @NotBlank(message = "GPA is mandatory")
    private Double gpa;

    @NotBlank(message = "CV_URL is mandatory")
    private String cvUrl;

    @NotBlank(message = "University is mandatory")
    private String university;

//    @NotBlank(message = "Desired_Track is mandatory")
    private DesiredTrack desiredTrack;

}
