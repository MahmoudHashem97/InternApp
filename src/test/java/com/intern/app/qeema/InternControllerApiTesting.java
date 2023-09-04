package com.intern.app.qeema;

import com.intern.app.qeema.model.DtoModels.InternProject;
import com.intern.app.qeema.model.DtoModels.InternResponse;
import com.intern.app.qeema.model.DtoModels.Role;
import com.intern.app.qeema.model.entities.Intern;
import com.intern.app.qeema.model.entities.Projects;
import com.intern.app.qeema.reprosatory.InternRepo;
import com.intern.app.qeema.reprosatory.ProjectRepo;
import com.intern.app.qeema.service.InternService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.intern.app.qeema.model.DtoModels.DesiredTrack.Backend;
import static java.nio.file.Paths.get;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InternControllerApiTesting {
    @Autowired
    private InternService internService;
    @Autowired
    private InternRepo internRepo ;
    @Autowired
    private ProjectRepo projectRepo;
    private static MockHttpServletRequest request;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

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
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterAll(){
        internRepo.deleteAll();
        projectRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void getAllIntern() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/interns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andReturn();
    }

    @Test
    @Order(2)
    public void findAllInternsSortedByGpaWithCertianUni() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/interns/{university}","GUC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andReturn();
    }
    @Test
    @Order(3)
    public void findByUniversityAndGpaGreaterThan() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/interns/{university}/{gpa}","GUC",2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andReturn();
    }

    @Test
    @Order(4)
    public void deletByIdTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/intern/{id}",10))
                .andExpect(status().isOk()).andReturn();
       assertFalse(internRepo.findById(10).isPresent());
    }

    @Test
    @Order(5)
    public void addProjectTest() throws Exception {
        this.mockMvc
                .perform(
                        patch("/project")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"internId\": \"14\"" +
                                        ", \"name\":\"python\"" +
                                        ", \"description\":\"depuger\"}")
                ) .andExpect(status().isCreated()).andReturn();

        List<Projects> projectsFromDataBase= internRepo.findById(14).orElseThrow(ChangeSetPersister.NotFoundException::new).getProjectsInterns();
        List<Projects>projectadded =new ArrayList<>();
        projectadded.add(Projects.builder().description("depuger").name("python").id(1).build());
        assertIterableEquals(projectsFromDataBase,projectadded,"should be deeply equal");
    }
    @Test
    @Order(6)
    public void findProjectByInternIdTest() throws Exception {
        InternProject internProject = InternProject.builder().internId(16).name("java").description("oop").build();
        internService.creatProjectInIntern(internProject);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/project/{id}",16))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andReturn();
    }


    @Test
    @Order(7)
    public void setAcceptanceTest() throws Exception {
        this.mockMvc
                .perform(
                        patch("/intern/acceptance/{id}",19)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"acceptance\": \"true\"}")
                ) .andExpect(status().isOk())

                .andReturn();
        assertTrue(internRepo.findById(19).orElseThrow(ChangeSetPersister.NotFoundException::new).isAccepted());
    }

}
