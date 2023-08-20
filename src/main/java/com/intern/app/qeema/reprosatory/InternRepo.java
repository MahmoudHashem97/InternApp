package com.intern.app.qeema.reprosatory;


import com.intern.app.qeema.model.entities.Intern;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternRepo extends JpaRepository<Intern, Integer> {

    public List<Intern> findByUniversity(String university,Sort sort);

//    List<Intern> findAllByUniversityOrderByGpaDesc();
    public List<Intern> findByUniversityAndGpaGreaterThan(String university,Double gpa);



}
