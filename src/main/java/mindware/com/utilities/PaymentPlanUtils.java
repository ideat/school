package mindware.com.utilities;

import mindware.com.model.PaymentPlan;
import mindware.com.service.ParameterService;
import mindware.com.service.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentPlanUtils {
    public List<PaymentPlan> createPaymentPlan(int studentId, LocalDate initDate, LocalDate endDate){
        List<PaymentPlan> paymentPlanList = new ArrayList<>();
        ParameterService parameterService = new ParameterService();
        int fixedDay = Integer.parseInt(parameterService.findValueParameterByTypeAndName("COBRO_CUOTAS",
                "DIA_PAGO").getValueParameter());
        int earlyMonth = Integer.parseInt(parameterService.findValueParameterByTypeAndName("COBRO_CUOTAS",
                "PAGO_MES_ACTUAL").getValueParameter());
        Util util = new Util();
        Double mountFee = new StudentService().findStudentById(studentId).getTypeFee().getMountFee();
        Double percentaje = new StudentService().findStudentById(studentId).getTypeFee().getDiscountPercentaje();
        mountFee = mountFee - (mountFee*percentaje/100);
        int months = util.monthsBetweenIgnoreDays(initDate,endDate);
        Date datePaymentPlan;
        for (int i = 0; i <= months ; i++) {
            PaymentPlan paymentPlan = new PaymentPlan();
            paymentPlan.setStudentId(studentId);
            paymentPlan.setPaymentPlanNumber(i+1);
            paymentPlan.setPaymentPlanDate( util.dateWithFixedDayAndIncMonth(fixedDay,initDate,i+earlyMonth));
            paymentPlan.setPaymentPlanAmount(mountFee);
            paymentPlanList.add(paymentPlan);
        }

        return paymentPlanList;
    }
}
