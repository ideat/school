package mindware.com.service;

import mindware.com.mapper.PaymentPlanMapper;
import mindware.com.model.PaymentPlan;
import mindware.com.utilities.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class PaymentPlanService {

    public List<PaymentPlan> findPaymentPlanByStudentId(int studentId){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentPlanMapper paymentPlanMapper = sqlSession.getMapper(PaymentPlanMapper.class);
            return paymentPlanMapper.findPaymentPlanByStudentId(studentId);
        }finally {
            sqlSession.close();
        }
    }

    public void insertPaymentPlan(PaymentPlan paymentPlan){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentPlanMapper paymentPlanMapper = sqlSession.getMapper(PaymentPlanMapper.class);
            paymentPlanMapper.insertPaymentPlan(paymentPlan);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public void insertPaymentPlanList(List<PaymentPlan> paymentPlanList){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentPlanMapper paymentPlanMapper = sqlSession.getMapper(PaymentPlanMapper.class);
            for(PaymentPlan paymentPlan:paymentPlanList) {
                paymentPlanMapper.insertPaymentPlan(paymentPlan);
            }
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public void updatePaymentPlan(PaymentPlan paymentPlan){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentPlanMapper paymentPlanMapper = sqlSession.getMapper(PaymentPlanMapper.class);
            paymentPlanMapper.updatePaymentPlan(paymentPlan);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public void deletePaymentPlan(int studentId){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentPlanMapper paymentPlanMapper = sqlSession.getMapper(PaymentPlanMapper.class);
            paymentPlanMapper.deletePaymentPlanByStudentId(studentId);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }


}
