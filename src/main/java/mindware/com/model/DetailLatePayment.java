package mindware.com.model;

import java.util.Date;

public class DetailLatePayment {
    private Integer studentId;
    private String  fullNameStudent;
    private String courseLevel;
    private Date paymentDate;
    private String invoiceNumber;
    private String description;
    private Double paymentMount;
    private Double balance;
    private String paymentConcept;
    private Double totalPayment;
    private Double paymentComputation;

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Double getPaymentComputation() {
        return paymentComputation;
    }

    public void setPaymentComputation(Double paymentComputation) {
        this.paymentComputation = paymentComputation;
    }

    public String getPaymentConcept() {
        return paymentConcept;
    }

    public void setPaymentConcept(String paymentConcept) {
        this.paymentConcept = paymentConcept;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFullNameStudent() {
        return fullNameStudent;
    }

    public void setFullNameStudent(String fullNameStudent) {
        this.fullNameStudent = fullNameStudent;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPaymentMount() {
        return paymentMount;
    }

    public void setPaymentMount(Double paymentMount) {
        this.paymentMount = paymentMount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
