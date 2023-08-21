package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.ExpensesDto;
import com.allianceever.projectERP.model.dto.HolidayDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ExpensesService {


    ExpensesDto getById(Long id);



    ExpensesDto create(ExpensesDto expensesDto);



    List<ExpensesDto> getAllExpensesOrderedByDate();

    void delete(Long Id);

    ExpensesDto update(Long id, ExpensesDto expensesDto);
}
