package mindware.com.mapper;

import mindware.com.model.PaymentPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentPlanMapper {
    void insertPaymentPlan(PaymentPlan paymentPlan);
    void deletePaymentPlanByStudentId(@Param("studentId") int studentId);
    void updatePaymentPlan(PaymentPlan paymentPlan);
    List<PaymentPlan> findPaymentPlanByStudentId(@Param("studentId") int studentId);
}
