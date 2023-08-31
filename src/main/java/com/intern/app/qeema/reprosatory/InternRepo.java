package com.intern.app.qeema.reprosatory;


import com.intern.app.qeema.model.entities.Intern;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InternRepo extends JpaRepository<Intern, Integer> {

    public List<Intern> findByUniversity(String university,Sort sort);

    //List<Intern> findAllByUniversityOrderByGpaDesc();
//    List<Intern> findByUniversityOrderByGpaDesc();
    public List<Intern> findByUniversityAndGpaGreaterThan(String university,Double gpa);
    Optional<Intern> findByEmail(String email);

    public List<Intern> findByUniversityIsNotEmpty(String university,Sort sort);




}
