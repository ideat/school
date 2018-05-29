package mindware.com.utilities;

import mindware.com.model.DetailLatePayment;
import mindware.com.service.DetailPaymentService;

import java.util.ArrayList;
import java.util.List;

public class DetailLatePaymentUtil {
    public List<DetailLatePayment> getDetailLatePayment(int studentId){
        DetailPaymentService detailPaymentService = new DetailPaymentService();
        List<DetailLatePayment> detailLatePaymentList = detailPaymentService.findDetailLatePaymentByStudentId(studentId);
        List<DetailLatePayment> processDetailPayment = new ArrayList<>();
        Double total = detailLatePaymentList.get(0).getTotalPayment() +
                       detailLatePaymentList.get(0).getPaymentComputation();
        DetailLatePayment aux = new DetailLatePayment();
        aux.setBalance(total);
        aux.setDescription("TOTAL A CANCELAR COMPUTACION Y MENSUALIDADES");
        processDetailPayment.add(aux);

        for(DetailLatePayment detailLatePayment:detailLatePaymentList){
            if (detailLatePayment.getPaymentMount()!=null)
                total = total - detailLatePayment.getPaymentMount();
            detailLatePayment.setBalance(total);
            processDetailPayment.add(detailLatePayment);
        }


        return processDetailPayment;
    }
}
