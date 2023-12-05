package io.codejournal.springprojects.mvcjpathymeleaf.controller;

import io.codejournal.springprojects.mvcjpathymeleaf.entity.Student;
import io.codejournal.springprojects.mvcjpathymeleaf.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class StudentControllerTest {

    private MockMvc mockMvc;;

    @MockBean
    private StudentService studentService;

    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        studentController = new StudentController(studentService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void view_ReturnsViewPageWithStudentFromDB_WhenStudentIdExistsInDB() throws Exception {
        final UUID id = randomUUID();
        final Student student = new Student(id, randomUUID().toString(), randomUUID().toString(), 25);

        given(studentService.getStudent(id)).willReturn(Optional.of(student));

        // @formatter:off
        mockMvc.perform(
                get("/students/view").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", is(id)))
                .andExpect(model().attribute("student", is(notNullValue())))
                .andExpect(model().attribute("student", hasProperty("id", is(id))))
                .andExpect(model().attribute("student", hasProperty("firstName", is(student.getFirstName()))))
                .andExpect(model().attribute("student", hasProperty("lastName", is(student.getLastName()))))
                .andExpect(model().attribute("student", hasProperty("age", is(student.getAge()))))
                .andExpect(view().name("students/view"));
        // @formatter:on

        then(studentService).should().getStudent(id);
        then(studentService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void view_ReturnsViewPageWithEmptyStudent_WhenStudentIdDoesNotExist() throws Exception {
        final UUID id = randomUUID();

        given(studentService.getStudent(id)).willReturn(Optional.empty());

        // @formatter:off
        mockMvc.perform(
                        get("/students/view").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", is(id)))
                .andExpect(model().attribute("student", is(notNullValue())))
                .andExpect(model().attribute("student", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("student", hasProperty("firstName", is(nullValue()))))
                .andExpect(model().attribute("student", hasProperty("lastName", is(nullValue()))))
                .andExpect(model().attribute("student", hasProperty("age", is(nullValue()))))
                .andExpect(view().name("students/view"));
        // @formatter:on

        then(studentService).should().getStudent(id);
        then(studentService).shouldHaveNoMoreInteractions();
    }

    @Test
    public void add_ReturnsViewPageWithEmptyStudent() throws Exception {

        // @formatter:off
        mockMvc.perform(
                        get("/students/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", is(notNullValue())))
                .andExpect(model().attribute("student", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("student", hasProperty("firstName", is(nullValue()))))
                .andExpect(model().attribute("student", hasProperty("lastName", is(nullValue()))))
                .andExpect(model().attribute("student", hasProperty("age", is(nullValue()))))
                .andExpect(view().name("students/add"));
        // @formatter:on

        then(studentService).shouldHaveNoInteractions();
    }
}
