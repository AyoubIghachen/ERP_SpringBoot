package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.ExpensesDto;

import com.allianceever.projectERP.model.entity.Expenses;

import com.allianceever.projectERP.repository.ExpensesRepo;
import com.allianceever.projectERP.service.ExpensesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpensesServiceImpl implements ExpensesService {







    private ExpensesRepo expensesRepo;
    private ModelMapper mapper;

    @Autowired
    public ExpensesServiceImpl(ExpensesRepo expensesRepo, ModelMapper mapper) {
        this.expensesRepo = expensesRepo;
        this.mapper = mapper;
    }

    @Override
    public ExpensesDto getById(Long id) {
        Optional<Expenses> expense = expensesRepo.findById(id);
        return mapper.map(expense,ExpensesDto.class);
    }

    @Override
    public ExpensesDto create(ExpensesDto expensesDto) {
        Expenses expenses = mapper.map(expensesDto, Expenses.class);



        // Save the expenses entity in the database
        Expenses createdExpense = expensesRepo.save(expenses);
        return mapper.map(createdExpense, ExpensesDto.class);
    }





    @Override
    public ExpensesDto update(Long id, ExpensesDto expensesDto) {
        expensesRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Expenses is not exist with given id : " + id)
        );
        // convert DTO to entity
        Expenses expenses = mapper.map(expensesDto, Expenses.class);
        Expenses updatedExpense = expensesRepo.save(expenses);

        // convert entity to DTO
        return mapper.map(updatedExpense, ExpensesDto.class);
    }





    @Override
    public List<ExpensesDto> getAllExpensesOrderedByDate() {
        List<Expenses> expenses = expensesRepo.findAllOrderByDate();

        return expenses.stream()
                .map(expense -> mapper.map(expense, ExpensesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional

    public void delete(Long id) {
        Optional<Expenses> expenseOptional = expensesRepo.findById(id);

        if (expenseOptional.isPresent()) {
            Expenses expense = expenseOptional.get();
            expensesRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Expense is not exist with given id: " + id);
        }
    }
}
