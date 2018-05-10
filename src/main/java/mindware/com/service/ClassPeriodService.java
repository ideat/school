package mindware.com.service;

import mindware.com.mapper.ClassPeriodMapper;
import mindware.com.model.ClassPeriod;
import mindware.com.utilities.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ClassPeriodService {
    public void insertClassPeriod(ClassPeriod classPeriod){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            ClassPeriodMapper classPeriodMapper = sqlSession.getMapper(ClassPeriodMapper.class);
            classPeriodMapper.insertClassPeriod(classPeriod);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public void updateClassPeriod(ClassPeriod classPeriod){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            ClassPeriodMapper classPeriodMapper = sqlSession.getMapper(ClassPeriodMapper.class);
            classPeriodMapper.updateClassPeriod(classPeriod);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public List<ClassPeriod> findAllClassPeriod(){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            ClassPeriodMapper classPeriodMapper = sqlSession.getMapper(ClassPeriodMapper.class);
            return classPeriodMapper.findAllClassPeriod();
        }finally {
            sqlSession.close();
        }

    }

    public Integer getCountClassPeriodByYear(String year){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            ClassPeriodMapper classPeriodMapper = sqlSession.getMapper(ClassPeriodMapper.class);
            return classPeriodMapper.getCountClassPeriodByYear(year);
        }finally {
            sqlSession.close();
        }

    }

    public List<ClassPeriod> findClassPeriodByState(String state){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            ClassPeriodMapper classPeriodMapper = sqlSession.getMapper(ClassPeriodMapper.class);
            return classPeriodMapper.findClassPeriodByState(state);
        }finally {
            sqlSession.close();
        }

    }
}
