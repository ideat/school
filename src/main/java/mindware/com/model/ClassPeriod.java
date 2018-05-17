package mindware.com.model;

import java.util.Date;

public class ClassPeriod {
    private Integer classPeriodId;
    private String year;
    private String state;
    private Date initDate;
    private Date endDate;

    public Integer getClassPeriodId() {
        return classPeriodId;
    }

    public void setClassPeriodId(Integer classPeriodId) {
        this.classPeriodId = classPeriodId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
