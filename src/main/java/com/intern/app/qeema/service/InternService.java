package com.intern.app.qeema.service;

import com.intern.app.qeema.model.entities.Intern;
import com.intern.app.qeema.model.models.InternProject;
import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.model.mapper.InternMapper;
import com.intern.app.qeema.model.models.InternRequest;
import com.intern.app.qeema.model.models.InternResponse;
import com.intern.app.qeema.reprosatory.InternRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InternService {

    @Autowired
    private InternRepo internRepo;

    @Autowired
    private projectServices projectServices;
//    public Intern createIntern(InternRequest internDTO){
//       return internRepo.save(InternHelper.internRequestMapper(internDTO));
//    }
    public InternRequest createIntern(InternRequest internDTO){
         internRepo.save(InternMapper.internRequestMapper(internDTO));
        return internDTO;
    }
    public List<InternResponse> findAllInterns (){
        return internRepo.findAll().stream().map(intern -> InternMapper.internResponseMapper(intern)).toList();
    }
    public List<InternResponse> findAllInternsSortedByGpaWithCertianUni (String university){
        return internRepo.findByUniversity(university,Sort.by(Sort.Direction.DESC, "gpa"))
                .stream().map(InternMapper::internResponseMapper).toList();
    }
    public List<InternResponse> findByUniversityAndGpaGreaterThan (String university,Double gpa){
        return internRepo.findByUniversityAndGpaGreaterThan(university,gpa)
                .stream().map(InternMapper::internResponseMapper).toList();
    }

    public void setAcceptance (int id , boolean isAccepted) {
        Intern intern =  internRepo.findById(id).get();
       //Intern intern =  internRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);

//        Optional<Intern> internOptional = internRepo.findById(id);
//        if(internOptional.isEmpty())

        intern.setAccepted(isAccepted);
        internRepo.save(intern);
    }
    public void deleteIntern(int id){
        internRepo.deleteById(id);
    }

    public void creatProjectInIntern(InternProject internProject){
        Intern intern =  internRepo.findById(internProject.getInternId()).get();
        Projects newProject = projectServices.addProject(internProject);

        if(intern.getProjectsInterns()!=null && intern.getProjectsInterns().isEmpty()){
            List<Projects> projects =new ArrayList<>();
            projects.add(newProject);
            intern.setProjectsInterns(projects);
            internRepo.save(intern);
        }
        else{
            intern.getProjectsInterns().add(newProject);
            internRepo.save(intern);
        }
    }


    public List<Projects> getProjectsByInternId(int id){
        Intern intern =  internRepo.findById(id).get();
        //List<Projects> ProjectsInterns;
        return intern.getProjectsInterns();


    }
}
