package mindware.com.model;

import java.util.Date;

public class PaymentPlan {
    private Integer paymentPlantId;
    private Integer studentId;
    private Integer paymentPlanNumer;
    private Double paymentPlanAmount;
    private Date paymentPlanDate;

    public Integer getPaymentPlantId() {
        return paymentPlantId;
    }

    public void setPaymentPlantId(Integer paymentPlantId) {
        this.paymentPlantId = paymentPlantId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getPaymentPlanNumer() {
        return paymentPlanNumer;
    }

    public void setPaymentPlanNumer(Integer paymentPlanNumer) {
        this.paymentPlanNumer = paymentPlanNumer;
    }

    public Double getPaymentPlanAmount() {
        return paymentPlanAmount;
    }

    public void setPaymentPlanAmount(Double paymentPlanAmount) {
        this.paymentPlanAmount = paymentPlanAmount;
    }

    public Date getPaymentPlanDate() {
        return paymentPlanDate;
    }

    public void setPaymentPlanDate(Date paymentPlanDate) {
        this.paymentPlanDate = paymentPlanDate;
    }
}
