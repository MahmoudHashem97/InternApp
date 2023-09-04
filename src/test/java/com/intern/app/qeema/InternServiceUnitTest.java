package com.intern.app.qeema;


import com.intern.app.qeema.model.DtoModels.AcceptanceRequest;
import com.intern.app.qeema.model.DtoModels.InternProject;
import com.intern.app.qeema.model.DtoModels.InternResponse;
import com.intern.app.qeema.model.DtoModels.Role;
import com.intern.app.qeema.model.entities.Intern;
import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.model.mapper.InternMapper;
import com.intern.app.qeema.reprosatory.InternRepo;
import com.intern.app.qeema.reprosatory.ProjectRepo;
import com.intern.app.qeema.service.InternService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;

import static com.intern.app.qeema.model.DtoModels.DesiredTrack.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InternServiceUnitTest {
    @Autowired
    private InternService internService;
    @Autowired
    private InternRepo internRepo ;
    @Autowired
    private ProjectRepo projectRepo;
    @BeforeEach
    public void beforeEach(){
        var intern1 = Intern.builder()
                .email("mahmoud1.hashem@gmail.com")
                .gpa(1)
                .desiredTrack(Backend)
                .university("GUC")
                .cvUrl("cdccdcv")
                .name("Mahmoud1 Hashem")
                .password("1234")
                .role(Role.User)
                .build();
        internRepo.save(intern1);
        var intern2 = Intern.builder()
                .email("mahmoud2.hashem@gmail.com")
                .gpa(2)
                .desiredTrack(Backend)
                .university("GUC")
                .cvUrl("cdccdcv")
                .name("Mahmoud2 Hashem")
                .password("1234")
                .role(Role.User)
                .build();
        internRepo.save(intern2);
        var intern3 = Intern.builder()
                .email("mahmoud3.hashem@gmail.com")
                .gpa(3)
                .desiredTrack(Backend)
                .university("GUC")
                .cvUrl("cdccdcv")
                .name("Mahmoud3 Hashem")
                .password("1234")
                .role(Role.User)
                .build();
        internRepo.save(intern3);
    }

    @AfterEach
    public void afterAll(){
        internRepo.deleteAll();
        projectRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void findAllUnitTest(){
        List<InternResponse> dataToTest =new ArrayList<>();
       InternResponse internResponse1= InternResponse.builder().id(1).gpa(1.0).desiredTrack(Backend).name("Mahmoud1 Hashem").build();
        dataToTest.add(internResponse1);
        InternResponse internResponse2= InternResponse.builder().id(2).gpa(2.0).desiredTrack(Backend).name("Mahmoud2 Hashem").build();
        dataToTest.add(internResponse2);
        InternResponse internResponse3= InternResponse.builder().id(3).gpa(3.0).desiredTrack(Backend).name("Mahmoud3 Hashem").build();
        dataToTest.add(internResponse3);

        List<InternResponse> listOfallServicTest = internService.findAllInterns();

        assertEquals(3,listOfallServicTest.size(),"Should be 3 intern");
        assertIterableEquals(listOfallServicTest,dataToTest,"should be deeply equal");

    }

    @Test
    @Order(2)
    public void findAllInternsSortedByGpaWithCertianUniTest(){

        List<InternResponse> listOfallServicTest = internService.findAllInternsSortedByGpaWithCertianUni("GUC");
        List<InternResponse> internfromDataBase = internRepo.findAllByUniversityOrderByGpaDesc("GUC").stream().map( InternMapper::internResponseMapper).toList();

        assertIterableEquals(listOfallServicTest,internfromDataBase,"should be deeply equal");
        assertEquals(3,listOfallServicTest.size(),"Should be 3 intern");

    }

    @Test
    @Order(3)
    public void findByUniversityAndGpaGreaterThanTest(){
        List<InternResponse> internfromDataBase = internRepo.findByUniversityAndGpaGreaterThan("GUC",2.0)
                .stream().map(InternMapper::internResponseMapper).toList();
        List<InternResponse> listOfallServicTest = internService.findByUniversityAndGpaGreaterThan("GUC",2.0);

        assertIterableEquals(listOfallServicTest,internfromDataBase,"should be deeply equal");
        assertEquals(1,listOfallServicTest.size(),"Should be 1 intern");
    }

    @Test
    @Order(4)
    public void setAcceptanceTest() throws ChangeSetPersister.NotFoundException {
        AcceptanceRequest acceptanceRequest=AcceptanceRequest.builder().acceptance(true).build();
        internService.setAcceptance(10,acceptanceRequest.isAcceptance());
        Intern intern =internRepo.findById(10).orElseThrow(ChangeSetPersister.NotFoundException::new);
        assertTrue(intern.isAccepted());
    }
    @Test
    @Order(5)
    public void createProjectTest() throws ChangeSetPersister.NotFoundException {
        InternProject internProject = InternProject.builder().internId(15).name("java").description("oop").build();
        internService.creatProjectInIntern(internProject);

       List<Projects> projectsFromDataBase= internRepo.findById(15).orElseThrow(ChangeSetPersister.NotFoundException::new).getProjectsInterns();
       List<Projects>projectadded =new ArrayList<>();
       projectadded.add(Projects.builder().description("oop").name("java").id(1).build());
       assertIterableEquals(projectsFromDataBase,projectadded,"should be deeply equal");
    }
    @Test
    @Order(6)
    public void getProjectByInternId() throws ChangeSetPersister.NotFoundException {
        InternProject internProject = InternProject.builder().internId(18).name("java").description("oop").build();
        internService.creatProjectInIntern(internProject);
        List<Projects>project= internService.getProjectsByInternId(18);
        List<Projects> projectsFromDataBase= internRepo.findById(18).orElseThrow(ChangeSetPersister.NotFoundException::new).getProjectsInterns();

        assertEquals(1,project.size(),"Should be 1 intern");
        assertIterableEquals(projectsFromDataBase,project,"should be deeply equal");
    }

}
