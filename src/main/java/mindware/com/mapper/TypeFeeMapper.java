package mindware.com.mapper;

import mindware.com.model.TypeFee;

import java.util.List;

public interface TypeFeeMapper {
    void insertTypeFee(TypeFee typeFee);
    void updateTypeFee(TypeFee typeFee);
    List<TypeFee> findAllTypeFee();
    List<TypeFee> findTypeFeeByClassPeriodYear(String year);
}
