package mindware.com.model;

import java.util.Date;

public class PaymentPlan {
    private Integer paymentPlantId;
    private Integer studentId;
    private Integer paymentNumer;
    private Double paymentAmount;
    private Date paymentDate;

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

    public Integer getPaymentNumer() {
        return paymentNumer;
    }

    public void setPaymentNumer(Integer paymentNumer) {
        this.paymentNumer = paymentNumer;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
