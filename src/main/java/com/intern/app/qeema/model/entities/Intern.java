package com.intern.app.qeema.model.entities;


import com.intern.app.qeema.model.models.DesiredTrack;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Intern")
public class Intern {
@Id
@SequenceGenerator(
        name = "INTERN_sequence",
        sequenceName ="student_sequence",
        allocationSize = 1
)
@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "INTERN_sequence"

)
    private int id;


    @Column(name = "full_name",nullable = false)

    private String name;
    @Column(name = "email",nullable = false)

    private String email;
    @Column(name = "gpa",nullable = false)

    private double gpa;

    @Column(name = "cv_url",nullable = false,columnDefinition = "TEXT")

    private String cvUrl;
    @Column(name = "university",nullable = false)

    private String university;
    @Column(name = "is_accepted",nullable = false, columnDefinition = "boolean default false")

    private boolean isAccepted ;

    @Column(name = "desiredTrack",nullable = false)

   private DesiredTrack desiredTrack;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Projects> ProjectsInterns;

}
