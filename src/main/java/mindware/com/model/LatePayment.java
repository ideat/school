package mindware.com.model;

public class LatePayment {
    private Integer studentId;
    private String nameStudent;
    private String lastNameStudent;
    private String courseLevel;
    private String turn;
    private Double sumPaymentPlanAmount;
    private Double sumPaymentMount;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public Double getSumPaymentPlanAmount() {
        return sumPaymentPlanAmount;
    }

    public void setSumPaymentPlanAmount(Double sumPaymentPlanAmount) {
        this.sumPaymentPlanAmount = sumPaymentPlanAmount;
    }

    public Double getMountDelayed() {
        return sumPaymentMount;
    }

    public void setSumPaymentMount(Double sumPaymentMount) {
        this.sumPaymentMount = sumPaymentMount;
    }

    public Double getSumPaymentMount() {
        return sumPaymentMount;
    }

    public Double getDelayedAmount() {
        return sumPaymentPlanAmount - sumPaymentMount ;
    }


}
