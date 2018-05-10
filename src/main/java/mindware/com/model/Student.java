package mindware.com.model;

import java.util.Date;

public class Student {
    private Integer studentId;
    private Integer classPeriodId;
    private Integer typeFeeId;
    private String nameStudent;
    private String lastNameStudent;
    private String parants;
    private String phoneNumber;
    private String address;
    private Date registrationDate;
    private Date bornDate;
    private Date retirement;
    private String classRoom;
    private String cellNumber;
    private ClassPeriod classPeriod;
    private TypeFee typeFee;

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

    public String getParants() {
        return parants;
    }

    public void setParants(String parants) {
        this.parants = parants;
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

    public Date getRetirement() {
        return retirement;
    }

    public void setRetirement(Date retirement) {
        this.retirement = retirement;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }
}
