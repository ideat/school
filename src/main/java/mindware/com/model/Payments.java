package mindware.com.model;

import java.util.Date;

public class Payments {
    private Integer paymentsId;
    private Integer studentId;
    private Date paymentDate;
    private Double paymentMount;
    private String description;
    private String paymentConcept;
    private String paymentType;
    private String invoicePayment;
    private Integer userId;

    public Integer getPaymentsId() {
        return paymentsId;
    }

    public void setPaymentsId(Integer paymentsId) {
        this.paymentsId = paymentsId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentMount() {
        return paymentMount;
    }

    public void setPaymentMount(Double paymentMount) {
        this.paymentMount = paymentMount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentConcept() {
        return paymentConcept;
    }

    public void setPaymentConcept(String paymentConcept) {
        this.paymentConcept = paymentConcept;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getInvoicePayment() {
        return invoicePayment;
    }

    public void setInvoicePayment(String invoicePayment) {
        this.invoicePayment = invoicePayment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
