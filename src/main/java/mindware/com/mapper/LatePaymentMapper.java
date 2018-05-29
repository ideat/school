package mindware.com.mapper;

import mindware.com.model.LatePayment;

import java.util.Date;
import java.util.List;

public interface LatePaymentMapper {
    List<LatePayment> findLatePaymenActiveByCutoffDate(Date cutoffDate);
}
