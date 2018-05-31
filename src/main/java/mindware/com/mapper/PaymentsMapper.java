package mindware.com.mapper;

import mindware.com.model.Payments;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PaymentsMapper {
    void insertPayments(Payments payments);
    void updatePayments(Payments payments);
    void deletePayments(@Param("paymentId") int paymentId);
    List<Payments> findPaymentsByDates(@Param("initDate") Date initDate, @Param("endDate") Date endDate);
    List<Payments> findPaymentsByStudentIdAndYear(@Param("studentId") int studentId, @Param("year") String year);
    List<Payments> findPaymentsByStudentInvoiceDate(@Param("studentId") int studentId,
                                                    @Param("invoiceNumber") String invoiceNumber,
                                                    @Param("paymentDate") Date paymentDate);
    List<Payments> findPaymentsGroupedByTypeDateStudent();
    int countPaymentsPeriod(@Param("paymentPeriod") String paymentPeriod);
}
