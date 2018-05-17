package mindware.com.utilities;

import mindware.com.model.PaymentPlan;
import mindware.com.service.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentPlanUtils {
    public List<PaymentPlan> createPaymentPlan(int studentId, LocalDate initDate, LocalDate endDate, int fixedDay){
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        Util util = new Util();
        Double mountFee = new StudentService().findStudentById(studentId).getTypeFee().getMountFee();
        Double percentaje = new StudentService().findStudentById(studentId).getTypeFee().getDiscountPercentaje();
        mountFee = mountFee - (mountFee*percentaje/100);
        int months = util.monthsBetweenIgnoreDays(initDate,endDate);
        Date datePaymentPlan;
        for (int i = 0; i < months ; i++) {
            PaymentPlan paymentPlan = new PaymentPlan();
            paymentPlan.setStudentId(studentId);
            paymentPlan.setPaymentPlanNumer(i+1);
            paymentPlan.setPaymentPlanDate( util.dateWithFixedDayAndIncMonth(fixedDay,initDate,i+1));
            paymentPlan.setPaymentPlanAmount(mountFee);
            paymentPlanList.add(paymentPlan);
        }

        return paymentPlanList;
    }
}
