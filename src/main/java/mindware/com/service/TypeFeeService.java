package mindware.com.service;

import mindware.com.mapper.TypeFeeMapper;
import mindware.com.model.TypeFee;
import mindware.com.utilities.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TypeFeeService {
    public void insertTypeFee(TypeFee typeFee){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try {
            TypeFeeMapper typeFeeMapper = sqlSession.getMapper(TypeFeeMapper.class);
            typeFeeMapper.insertTypeFee(typeFee);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public void updateTypeFee(TypeFee typeFee){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try {
            TypeFeeMapper typeFeeMapper = sqlSession.getMapper(TypeFeeMapper.class);
            typeFeeMapper.updateTypeFee(typeFee);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public List<TypeFee> findAllTypeFee(){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try {
            TypeFeeMapper typeFeeMapper = sqlSession.getMapper(TypeFeeMapper.class);
            return typeFeeMapper.findAllTypeFee();
        }finally {
            sqlSession.close();
        }
    }

    public List<TypeFee> findTypeFeeByClassPeriodYear(String year){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try {
            TypeFeeMapper typeFeeMapper = sqlSession.getMapper(TypeFeeMapper.class);
            return typeFeeMapper.findTypeFeeByClassPeriodYear(year);
        }finally {
            sqlSession.close();
        }
    }
}
