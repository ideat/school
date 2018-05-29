package mindware.com.service;

import mindware.com.mapper.LatePaymentMapper;
import mindware.com.model.LatePayment;
import mindware.com.utilities.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class LatePaymentService {

    public List<LatePayment> findLatePaymenActiveByCutoffDate(Date cutoffDate){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            LatePaymentMapper latePaymentMapper = sqlSession.getMapper(LatePaymentMapper.class);
            return latePaymentMapper.findLatePaymenActiveByCutoffDate(cutoffDate);
        }finally {
            sqlSession.close();
        }
    }



}
