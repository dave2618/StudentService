package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {
    Service service;

    @BeforeEach
    void config() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void saveValidStudent() {
        Iterable<Student> students = service.findAllStudents();

        Long size = students.spliterator().getExactSizeIfKnown();
        service.saveStudent("71", "Peterke", 522);
        Iterable<Student> newStudents = service.findAllStudents();
        Long size2 = newStudents.spliterator().getExactSizeIfKnown();
        assertEquals(size + 1, size2);
    }

    @Test
    void saveInvalidStudent() {
        Iterable<Student> students = service.findAllStudents();

        Long size = students.spliterator().getExactSizeIfKnown();
        service.saveStudent("78", "Peterke", 5); //110 <= group <= 938
        Iterable<Student> newStudents = service.findAllStudents();
        Long size2 = newStudents.spliterator().getExactSizeIfKnown();
        assertEquals(size, size2);
    }

    @Test
    void saveValidHomework() {
        Homework homework = new Homework("6", "verifikacio", 9, 5);
        int response = service.saveHomework(homework.getID(), homework.getDescription(), homework.getDeadline(), homework.getStartline());
        assertTrue(response == 1);
        service.deleteHomework(homework.getID());
    }

    @Test
    void saveInValidHomework() {
        Homework homework = new Homework("6", "verifikacio", 9, 123); // startline > 14
        int response = service.saveHomework(homework.getID(), homework.getDescription(), homework.getDeadline(), homework.getStartline());
        assertNotEquals(response, 0);
        service.deleteHomework(homework.getID());
    }

    @Test
    void deleteStudent() {
        Iterable<Student> students = service.findAllStudents();

        Long size = students.spliterator().getExactSizeIfKnown();
        service.deleteStudent("71");
        Iterable<Student> newStudents = service.findAllStudents();
        Long size2 = newStudents.spliterator().getExactSizeIfKnown();
        assertEquals(size, size2+1);
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, 532})
    void testGroups(int group) {
        Student student = new Student("9", "Attila", group);
        int res = service.saveStudent(student.getID(), student.getName(), student.getGroup());
        assertEquals(res, 1);
        service.deleteStudent(student.getID());
    }

    @Test
    void deleteHomework() {
    }
}
