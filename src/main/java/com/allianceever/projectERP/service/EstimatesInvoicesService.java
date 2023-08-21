package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.EstimatesInvoicesDto;
import com.allianceever.projectERP.model.dto.HolidayDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface EstimatesInvoicesService {



    EstimatesInvoicesDto create(EstimatesInvoicesDto estimatesInvoicesDto);


    EstimatesInvoicesDto getById(Integer id);


    EstimatesInvoicesDto update(Integer id,EstimatesInvoicesDto estimatesInvoicesDto);


    List<EstimatesInvoicesDto> getAllEstimates();

    List<EstimatesInvoicesDto> getAllInvoices();


    void delete(Integer id);
}
