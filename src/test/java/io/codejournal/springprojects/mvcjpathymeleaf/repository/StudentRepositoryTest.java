package io.codejournal.springprojects.mvcjpathymeleaf.repository;

import io.codejournal.springprojects.mvcjpathymeleaf.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void save_StoresRecord_WhenRecordIsValid() {
        final Student expectedStudent = new Student();
        expectedStudent.setFirstName(randomUUID().toString());
        expectedStudent.setLastName(randomUUID().toString());
        expectedStudent.setAge(25);

        final Student savedStudent = studentRepository.save(expectedStudent);

        final Student actualStudent = entityManager.find(Student.class, savedStudent.getId());

        assertThat(actualStudent).isEqualTo(expectedStudent);
    }
}
