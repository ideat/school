package mindware.com.mapper;

import mindware.com.model.Student;

import java.util.List;

public interface StudentMapper {
    void insertStudent(Student student);
    void updateStudent(Student student);
    List<Student> findStudentByPeriod(String year);
    List<Student> findStudentRetired(String year);
}
