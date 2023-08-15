package com.intern.app.qeema.reprosatory;

import com.intern.app.qeema.model.entities.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Projects, Integer> {
    public Projects findByDescriptionAndName(String name,String description);
}
