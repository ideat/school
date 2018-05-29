package mindware.com.mapper;

import mindware.com.model.DetailLatePayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DetailLatePaymentMapper {
    List<DetailLatePayment> findDetailLatePaymentByStudentId(@Param("studentId") int studentId);
}
