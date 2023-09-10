package com.allianceever.projectERP;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.entity.EstimatesInvoices;
import com.allianceever.projectERP.model.entity.Payment;
import com.allianceever.projectERP.repository.EstimatesInvoicesRepo;
import com.allianceever.projectERP.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Configuration
@Component
public class StatusHelper {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private EstimatesInvoicesRepo estimatesInvoicesRepo;

    // Helper method to map status to color class
    @Autowired
    public static String getStatusColorClass(String status) {
        if ("Approved".equals(status)) {
            return "text-success";
        } else if ("Declined".equals(status)) {
            return "text-danger";
        } else if ("Pending".equals(status)) {
            return "text-primary";
        }else{
            return "text-primary";
        }
    }


    public String getPaymentStatusForInvoice(Long invoiceId, BigDecimal invoiceAmount) {
        EstimatesInvoices estimatesInvoices = estimatesInvoicesRepo.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        List<Payment> Payments = paymentRepo.findAll();
        List<Payment> AcceptedPayments = new ArrayList<>();;
        for (Payment payment : Payments) {
            if(payment.getInvoiceID()==invoiceId){
                AcceptedPayments.add(payment);
            }

        }
        if (AcceptedPayments.size()!=0) {
            BigDecimal totalPaidAmount = calculateTotalPaidAmount(AcceptedPayments);

            if (totalPaidAmount.compareTo(estimatesInvoices.getTotal()) < 0) {
                return "not yet";
            } else {
                return "paid";
            }
        } else {
            return "no payments found"; // Handle the case when no payments are found
        }



    }

    private BigDecimal calculateTotalPaidAmount(List<Payment> payments) {
        BigDecimal totalPaidAmount = BigDecimal.ZERO;

        for (Payment payment : payments) {
            BigDecimal paidAmount = payment.getPaidAmount();
            totalPaidAmount = totalPaidAmount.add(paidAmount);
        }

        return totalPaidAmount;
    }


}
