package mindware.com.model;

public class TypeFee {
    private Integer typeFeeId;
    private Integer classPeriodId;
    private Double mountFee;
    private String nameFee;
    private Double discountPercentaje;
    private ClassPeriod classPeriod;

    public ClassPeriod getClassPeriod() {
        return classPeriod;
    }

    public void setClassPeriod(ClassPeriod classPeriod) {
        this.classPeriod = classPeriod;
    }

    public Double getDiscountPercentaje() {
        return discountPercentaje;
    }

    public void setDiscountPercentaje(Double discountPercentaje) {
        this.discountPercentaje = discountPercentaje;
    }

    public Integer getTypeFeeId() {
        return typeFeeId;
    }

    public void setTypeFeeId(Integer typeFeeId) {
        this.typeFeeId = typeFeeId;
    }

    public Integer getClassPeriodId() {
        return classPeriodId;
    }

    public void setClassPeriodId(Integer classPeriodId) {
        this.classPeriodId = classPeriodId;
    }

    public Double getMountFee() {
        return mountFee;
    }

    public void setMountFee(Double mountFee) {
        this.mountFee = mountFee;
    }

    public String getNameFee() {
        return nameFee;
    }

    public void setNameFee(String nameFee) {
        this.nameFee = nameFee;
    }
}
