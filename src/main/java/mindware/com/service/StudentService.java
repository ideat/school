package mindware.com.service;

import mindware.com.mapper.StudentMapper;
import mindware.com.model.Student;
import mindware.com.utilities.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentService {
    public void insertStudent(Student student){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            studentMapper.insertStudent(student);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public void updateStudent(Student student){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            studentMapper.updateStudent(student);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public List<Student> findStudentByPeriod(String year){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findStudentByPeriod(year);
        }finally {
            sqlSession.close();
        }
    }

    public List<Student> findStudentRetired(String year){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findStudentRetired(year);
        }finally {
            sqlSession.close();
        }
    }

    public Student findStudentById(int studentId){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.findStudentById(studentId);
        }finally {
            sqlSession.close();
        }
    }
}
