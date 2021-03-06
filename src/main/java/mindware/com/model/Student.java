package mindware.com.model;

import java.util.Date;
import java.util.List;

public class Student {
    private Integer studentId;
    private Integer classPeriodId;
    private Integer typeFeeId;
    private String nameStudent;
    private String lastNameStudent;
    private String parents;
    private String phoneNumber;
    private String address;
    private Date registrationDate;
    private Date bornDate;
    private Date retirementDate;
    private String classRoom;
    private String cellNumber;
    private String courseLevel;
    private String turn;
    private ClassPeriod classPeriod;
    private String computation;
    private TypeFee typeFee;
    private List<PaymentPlan> paymentPlanList;
    private List<Payments> paymentsList;


    public List<PaymentPlan> getPaymentPlanList() {
        return paymentPlanList;
    }

    public void setPaymentPlanList(List<PaymentPlan> paymentPlanList) {
        this.paymentPlanList = paymentPlanList;
    }

    public List<Payments> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<Payments> paymentsList) {
        this.paymentsList = paymentsList;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public ClassPeriod getClassPeriod() {
        return classPeriod;
    }

    public void setClassPeriod(ClassPeriod classPeriod) {
        this.classPeriod = classPeriod;
    }

    public TypeFee getTypeFee() {
        return typeFee;
    }

    public void setTypeFee(TypeFee typeFee) {
        this.typeFee = typeFee;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getClassPeriodId() {
        return classPeriodId;
    }

    public void setClassPeriodId(Integer classPeriodId) {
        this.classPeriodId = classPeriodId;
    }

    public Integer getTypeFeeId() {
        return typeFeeId;
    }

    public void setTypeFeeId(Integer typeFeeId) {
        this.typeFeeId = typeFeeId;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getLastNameStudent() {
        return lastNameStudent;
    }

    public void setLastNameStudent(String lastNameStudent) {
        this.lastNameStudent = lastNameStudent;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public Date getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(Date retirementDate) {
        this.retirementDate = retirementDate;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getComputation() {
        return computation;
    }

    public void setComputation(String computation) {
        this.computation = computation;
    }
}
