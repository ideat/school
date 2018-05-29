package mindware.com.service;

import mindware.com.mapper.PaymentsMapper;
import mindware.com.model.Payments;
import mindware.com.utilities.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class PaymentsService {
    public void insertPayments(List<Payments> paymentsList){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentsMapper paymentsMapper = sqlSession.getMapper(PaymentsMapper.class);
            for(Payments payments:paymentsList) {
                paymentsMapper.insertPayments(payments);
            }
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }



    public void updatePayments(Payments payments){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentsMapper paymentsMapper = sqlSession.getMapper(PaymentsMapper.class);
            paymentsMapper.updatePayments(payments);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    public List<Payments> findPaymentsByDates(Date initDate, Date endDate){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentsMapper paymentsMapper = sqlSession.getMapper(PaymentsMapper.class);
            return paymentsMapper.findPaymentsByDates(initDate,endDate);
        }finally {
            sqlSession.close();
        }
    }

    public List<Payments> findPaymentsByStudentIdAndYear(int studentId, String year){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentsMapper paymentsMapper = sqlSession.getMapper(PaymentsMapper.class);
            return paymentsMapper.findPaymentsByStudentIdAndYear(studentId,year);
        }finally {
            sqlSession.close();
        }
    }

    public int countPaymentsPeriod(String paymentPeriod){
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession("development");
        try{
            PaymentsMapper paymentsMapper = sqlSession.getMapper(PaymentsMapper.class);
            return paymentsMapper.countPaymentsPeriod(paymentPeriod);
        }finally {
            sqlSession.close();
        }
    }
}
