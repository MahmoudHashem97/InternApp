package com.intern.app.qeema.model.entities;

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
public class Projects {
    @Id
    @SequenceGenerator(
            name = "Project_sequence",
            sequenceName ="Project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Project_sequence"
    )
    int id ;
    @Column(nullable = false)
    String name;
    @Column(nullable = false,columnDefinition = "TEXT")
    String description;


}
