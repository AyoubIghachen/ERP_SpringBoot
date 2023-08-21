package com.allianceever.projectERP.service;


import com.allianceever.projectERP.model.dto.EstimatesInvoicesDto;
import com.allianceever.projectERP.model.dto.ExpensesDto;
import com.allianceever.projectERP.model.dto.LeaveTypeDto;
import com.allianceever.projectERP.model.dto.PaymentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {


    void delete(Long id);

    List<PaymentDto> getAll();



    PaymentDto create(PaymentDto paymentDto);
}
