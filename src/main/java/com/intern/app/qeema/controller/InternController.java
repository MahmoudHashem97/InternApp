package com.intern.app.qeema.controller;

import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.model.models.InternProject;
import com.intern.app.qeema.model.models.InternRequest;
import com.intern.app.qeema.model.models.InternResponse;
import com.intern.app.qeema.reprosatory.InternRepo;
import com.intern.app.qeema.service.InternService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class InternController {

    final private InternService service;

   // final private InternRepo internRepo;

    @PostMapping("/Intern")
    @Operation(summary = "Create intern")
    public ResponseEntity<InternRequest> creatIntern(@Valid @RequestBody InternRequest request){
        return new ResponseEntity<InternRequest>(service.createIntern(request), HttpStatus.CREATED);
    }

    @GetMapping("/Interns")
    @Operation(summary = "Get all interns")
    public  ResponseEntity<List<InternResponse>> getAllInterns(){
        return new ResponseEntity<List<InternResponse>>(service.findAllInterns(), HttpStatus.OK);
    }

    @GetMapping("/Interns/{university}")
    @Operation(summary = "Get all interns by certian university")
    public  ResponseEntity<List<InternResponse>> findAllInternsSortedByGpaWithCertianUni(@PathVariable("university") String university){
        return new ResponseEntity<List<InternResponse>>(service.findAllInternsSortedByGpaWithCertianUni(university
        ), HttpStatus.OK);
    }

    @GetMapping("/Interns/{university}/{gpa}")
    @Operation(summary = "Get all interns by certian university sorted by their gpa")
    public  ResponseEntity<List<InternResponse>> findByUniversityAndGpaGreaterThan
            (@PathVariable("university") String university,@PathVariable("gpa") Double gpa){
        return new ResponseEntity<List<InternResponse>>(service.findByUniversityAndGpaGreaterThan(university,gpa
        ), HttpStatus.OK);
    }

    @DeleteMapping("/Intern/{id}")
    @Operation(summary = "Delete an intern by his id")
    public void  deletById (@PathVariable("id") int id){
         service.deleteIntern(id);

    }

    @PatchMapping("/Acceptance/{id}")
    @Operation(summary = "Set the acceptance or the rejection of a certian intern")
    public void setAcceptance (@PathVariable("id") int id ,@RequestParam boolean isAcceped){
       service.setAcceptance(id,isAcceped);
    }

    @PatchMapping("/Project")
    @Operation(summary = "Add project to a certian intern")
    public void addProject(@Valid @RequestBody InternProject internProject){
        service.creatProjectInIntern(internProject);
    }

    @GetMapping("/Project/{id}")
    @Operation(summary = "Get the projects of a certian intern")
    public  ResponseEntity<List<Projects>> findProjectByInternId(@PathVariable("id") int id){
        return new ResponseEntity<List<Projects>>(service.getProjectsByInternId(id)
        , HttpStatus.OK);
    }
}
