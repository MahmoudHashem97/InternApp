package com.intern.app.qeema.reprosatory;


import com.intern.app.qeema.model.entities.Intern;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternRepo extends JpaRepository<Intern, Integer> {

    public List<Intern> findByUniversity(String university,Sort sort);
    public List<Intern> findByUniversityAndGpaGreaterThan(String university,Double gpa);

//    {
//        "desiredTrack": "Backend",
//            "id": 1,
//            "name": "ahmed",
//            "gpa": 4
//    },
//    {
//        "desiredTrack": "Backend",
//            "id": 2,
//            "name": "7oda",
//            "gpa": 0.7
//    },
//    {
//        "desiredTrack": "Backend",
//            "id": 7,
//            "name": "hamza",
//            "gpa": 4
//    },
//    {
//        "desiredTrack": "Backend",
//            "id": 8,
//            "name": "test",
//            "gpa": 1
//    },
//    {
//        "desiredTrack": "Backend",
//            "id": 3,
//            "name": "hashem",
//            "gpa": 2
//    },
//    {
//        "desiredTrack": "Backend",
//            "id": 5,
//            "name": "mohamed",
//            "gpa": 3.5
//    },
//    {
//        "desiredTrack": "Backend",
//            "id": 6,
//            "name": "hamza",
//            "gpa": 4
//    }

}
