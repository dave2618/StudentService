package service;

import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceMockTest {
    Service service;

    @Mock
    StudentXMLRepository fileRepository1;
    @Mock
    HomeworkXMLRepository fileRepository2;
    @Mock
    GradeXMLRepository fileRepository3;

    @BeforeEach
    void config() {
        MockitoAnnotations.initMocks(this);

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void saveValidStudent() {
        when(fileRepository1.save(any(Student.class))).thenReturn(null);
        Integer res = service.saveStudent("71", "Peterke", 522);

        assertEquals(res, 1);
        verify(fileRepository1).save(any(Student.class));
    }

    @Test
    void deleteHomework() {
        when(fileRepository2.delete(anyString())).thenReturn(new Homework("71", "Verval", 13, 5));
        Integer res = service.deleteHomework("71");

        assertEquals(res, 1);
        verify(fileRepository2).delete(anyString());
    }

    @Test
    void deleteStudent() {
        when(fileRepository1.delete(anyString())).thenReturn(new Student("71", "Peterke", 522));
        Integer res = service.deleteStudent("71");

        assertEquals(res, 1);
        verify(fileRepository1).delete(anyString());
    }

    @Test
    void findStudents() {
        assertNotNull(service);
        Student student1 = new Student("71", "Peterke", 522);
        Student student2 = new Student("72", "Albert", 523);
        Student student3 = new Student("73", "John", 524);
        ArrayList<Student> array = new ArrayList<>();
        array.add(student1);
        array.add(student2);
        array.add(student3);

        when(fileRepository1.findAll()).thenReturn(array);
        assertEquals(service.findAllStudents(), array);
    }

}
