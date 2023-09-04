package com.intern.app.qeema.controller;

import com.intern.app.qeema.model.DtoModels.*;
import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.model.secureDto.AuthenticationRequest;
import com.intern.app.qeema.model.secureDto.AuthenticationResponse;
//import com.intern.app.qeema.service.ExceptionHandler.ExceptionNotFound;
import com.intern.app.qeema.service.InternService;
import com.intern.app.qeema.service.securityService.AuthenticationSevice;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/")

public class InternController {

    private final InternService service;
    private final AuthenticationSevice authservice;

    @PostMapping("/Register")
    @Operation(summary = "Register as an intern")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody InternRequest request
    ){

        return new ResponseEntity<AuthenticationResponse>(authservice.register(request), HttpStatus.CREATED);
    }
    @PostMapping("/LogIn")
    @Operation(summary = "logIn as an intern")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authservice.authenticate(request));

    }
    @GetMapping("/interns")
    @Operation(summary = "Get all interns")
    public  ResponseEntity<List<InternResponse>> getAllInterns() {
        return new ResponseEntity<List<InternResponse>>(service.findAllInterns(), HttpStatus.OK);
    }

    @GetMapping("/interns/{university}")
    @Operation(summary = "Get all interns by certian university")
    public  ResponseEntity<List<InternResponse>> findAllInternsSortedByGpaWithCertianUni(@PathVariable("university") String university){
        return new ResponseEntity<List<InternResponse>>(service.findAllInternsSortedByGpaWithCertianUni(university
        ), HttpStatus.OK);
    }

    @GetMapping("/interns/{university}/{gpa}")
    @Operation(summary = "Get all interns by certian university sorted by their gpa")
    public  ResponseEntity<List<InternResponse>> findByUniversityAndGpaGreaterThan
            (@PathVariable("university") String university,@PathVariable("gpa") Double gpa){
        return new ResponseEntity<List<InternResponse>>(service.findByUniversityAndGpaGreaterThan(university,gpa
        ), HttpStatus.OK);
    }

    @DeleteMapping("/intern/{id}")
    @Operation(summary = "Delete an intern by his id")
    public void  deletById (@PathVariable("id") int id){
         service.deleteIntern(id);
    }

    @PatchMapping("/intern/acceptance/{id}")
    @Operation(summary = "Set the acceptance or the rejection of a certian intern")
    public ResponseEntity<AcceptanceResponse> setAcceptance (@PathVariable("id") int id , @RequestBody AcceptanceRequest acceptanceRequest) throws ChangeSetPersister.NotFoundException {
        return new ResponseEntity<AcceptanceResponse>(service.setAcceptance(id,acceptanceRequest.isAcceptance())
                , HttpStatus.OK);
    }

    @PatchMapping("/project")
    @Operation(summary = "Add project to a certian intern")
    public ResponseEntity<Projects> addProject(@Valid @RequestBody InternProject internProject) throws ExceptionInInitializerError{
        return new ResponseEntity<Projects>(service.creatProjectInIntern(internProject)
                , HttpStatus.CREATED);
    }

    @GetMapping("/project/{id}")
    @Operation(summary = "Get the projects of a certian intern")
    public  ResponseEntity<List<Projects>> findProjectByInternId(@PathVariable("id") int id) throws ChangeSetPersister.NotFoundException {
        return new ResponseEntity<List<Projects>>(service.getProjectsByInternId(id)
        , HttpStatus.OK);
    }
}
