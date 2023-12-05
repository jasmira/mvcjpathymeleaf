package io.codejournal.springprojects.mvcjpathymeleaf.service;

import io.codejournal.springprojects.mvcjpathymeleaf.entity.Student;
import io.codejournal.springprojects.mvcjpathymeleaf.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void getStudent_ReturnsStudent_WhenStudentExists() {
        final UUID id = randomUUID();
        final Student student = new Student(randomUUID(), randomUUID().toString(), randomUUID().toString(), 25);
        final Optional<Student> expectedStudent = Optional.of(student);

        given(studentRepository.findById(id)).willReturn(expectedStudent);

        final Optional<Student> actualStudent = studentService.getStudent(id);

        assertThat(actualStudent).isEqualTo(expectedStudent);

        then(studentRepository).should().findById(id);
        then(studentRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void getStudent_ReturnsStudent_WhenStudentDoesNotExist() {
        final UUID id = randomUUID();
        final Optional<Student> expectedStudent = Optional.empty();

        given(studentRepository.findById(id)).willReturn(expectedStudent);

        final Optional<Student> actualStudent = studentService.getStudent(id);

        assertThat(actualStudent).isEqualTo(expectedStudent);

        then(studentRepository).should().findById(id);
        then(studentRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void save_ReturnSaved_WhenStudentRecordIsCreated() {
        final UUID id = randomUUID();
        final Student expectedStudent = new Student();
        expectedStudent.setFirstName(randomUUID().toString());
        expectedStudent.setLastName(randomUUID().toString());
        expectedStudent.setAge(25);

        given(studentRepository.save(expectedStudent)).willAnswer(invocation -> {
            final Student toSaveStudent = invocation.getArgument(0);
            toSaveStudent.setId(id);
            return toSaveStudent;
        });

        final Student actualStudent = studentService.save(expectedStudent);

        assertThat(actualStudent).isEqualTo(expectedStudent);

        then(studentRepository).should().save(expectedStudent);
        then(studentRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void delete_DeletesStudent_WhenStudentExists() {
        final UUID id = randomUUID();

        willDoNothing().given(studentRepository).deleteById(id);

        studentService.delete(id);

        then(studentRepository).should().deleteById(id);
        then(studentRepository).shouldHaveNoMoreInteractions();
    }
}
