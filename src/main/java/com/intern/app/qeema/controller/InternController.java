package com.intern.app.qeema.controller;

import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.model.models.InternProject;
import com.intern.app.qeema.model.models.InternRequest;
import com.intern.app.qeema.model.models.InternResponse;
import com.intern.app.qeema.reprosatory.InternRepo;
import com.intern.app.qeema.service.InternService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class InternController {
    @Autowired
    private InternService service;
    @Autowired
    private InternRepo internRepo;

    @PostMapping("/")
    public ResponseEntity<InternRequest> creatIntern(@Valid @RequestBody InternRequest request){
        return new ResponseEntity<InternRequest>(service.createIntern(request), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public  ResponseEntity<List<InternResponse>> getAllInterns(){
        return new ResponseEntity<List<InternResponse>>(service.findAllInterns(), HttpStatus.OK);
    }

    @GetMapping("/{university}")
    public  ResponseEntity<List<InternResponse>> findAllInternsSortedByGpaWithCertianUni(@PathVariable("university") String university){
        return new ResponseEntity<List<InternResponse>>(service.findAllInternsSortedByGpaWithCertianUni(university
        ), HttpStatus.OK);
    }

    @GetMapping("/Gpa/{university}/{gpa}")
    public  ResponseEntity<List<InternResponse>> findByUniversityAndGpaGreaterThan
            (@PathVariable("university") String university,@PathVariable("gpa") Double gpa){
        return new ResponseEntity<List<InternResponse>>(service.findByUniversityAndGpaGreaterThan(university,gpa
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void  deletById (@PathVariable("id") int id){
         internRepo.deleteById(id);
    }


    @PatchMapping("/ISAcceped/{id}")
    public void setAcceptance (@PathVariable("id") int id ,@RequestParam boolean isAcceped){
       service.setAcceptance(id,isAcceped);
    }

    @PatchMapping("/Projects")
    public void addProject(@Valid @RequestBody InternProject internProject){
        service.creatProjectInIntern(internProject);
    }

    @GetMapping("/Projects/{id}")
    public  ResponseEntity<List<Projects>> findProjectByInternId(@PathVariable("id") int id){
        return new ResponseEntity<List<Projects>>(service.getProjectsByInternId(id)
        , HttpStatus.OK);
    }
//git remote add origin https://github.com/MahmoudHashem97/InternApp
}
