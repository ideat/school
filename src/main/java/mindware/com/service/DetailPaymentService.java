package mindware.com.service;

import mindware.com.mapper.DetailLatePaymentMapper;
import mindware.com.model.DetailLatePayment;
import mindware.com.utilities.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DetailPaymentService {

    public List<DetailLatePayment> findDetailLatePaymentByStudentId(int studentId){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            DetailLatePaymentMapper detailLatePaymentMapper = sqlSession.getMapper(DetailLatePaymentMapper.class);
            return detailLatePaymentMapper.findDetailLatePaymentByStudentId(studentId);
        }finally {
            sqlSession.close();
        }
    }
}
