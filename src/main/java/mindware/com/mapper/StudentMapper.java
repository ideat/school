package mindware.com.mapper;

import mindware.com.model.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    void insertStudent(Student student);
    void updateStudent(Student student);
    List<Student> findStudentByPeriod(String year);
    List<Student> findStudentRetired(String year);
    Student findStudentById(@Param("studentId") int studentId);
}
