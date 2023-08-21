package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.PaymentDto;
import com.allianceever.projectERP.model.entity.EstimatesInvoices;
import com.allianceever.projectERP.model.entity.Payment;
import com.allianceever.projectERP.repository.EstimatesInvoicesRepo;
import com.allianceever.projectERP.repository.PaymentRepo;
import com.allianceever.projectERP.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepo paymentRepo;

    private ModelMapper mapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepo paymentRepo, ModelMapper mapper) {
        this.paymentRepo = paymentRepo;
        this.mapper = mapper;
    }

    @Override
    public PaymentDto create(PaymentDto paymentDto) {
        // convert DTO to entity
        Payment payment = mapper.map(paymentDto, Payment.class);
        Payment newPayment = paymentRepo.save(payment);

        // convert entity to DTO
        return mapper.map(newPayment, PaymentDto.class);
    }

    @Override
    public List<PaymentDto> getAll() {


        List<Payment> payments = paymentRepo.findAll();

        return payments.stream()
                .map(payment -> mapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());
    }




    @Override
    @Transactional
    public void delete(Long id) {

            paymentRepo.deleteById(id);

    }




}



