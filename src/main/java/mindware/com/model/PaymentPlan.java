package mindware.com.model;

import java.util.Date;

public class PaymentPlan {
    private Integer paymentPlanId;
    private Integer studentId;
    private Integer paymentPlanNumber;
    private Double paymentPlanAmount;
    private Date paymentPlanDate;

    public Integer getPaymentPlanId() {
        return paymentPlanId;
    }

    public void setPaymentPlanId(Integer paymentPlanId) {
        this.paymentPlanId = paymentPlanId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getPaymentPlanNumber() {
        return paymentPlanNumber;
    }

    public void setPaymentPlanNumber(Integer paymentPlanNumber) {
        this.paymentPlanNumber = paymentPlanNumber;
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
