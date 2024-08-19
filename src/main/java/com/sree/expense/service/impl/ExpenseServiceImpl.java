package com.sree.expense.service.impl;

import com.sree.expense.dto.ExpenseDto;
import com.sree.expense.entity.Category;
import com.sree.expense.entity.Expense;
import com.sree.expense.exception.ResourceNotFoundException;
import com.sree.expense.mapper.CategoryMapper;
import com.sree.expense.mapper.ExpenseMapper;
import com.sree.expense.repository.CategoryRepository;
import com.sree.expense.repository.ExpenseRepository;
import com.sree.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        Expense expense = expenseRepository.save(ExpenseMapper.mapToExpense(expenseDto));
        return ExpenseMapper.mapToExpenseDto(expense);
    }

    @Override
    public ExpenseDto getExpenseById(Long id) {
        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        return ExpenseMapper.mapToExpenseDto(expense);
    }

    @Override
    public List<ExpenseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(expense -> ExpenseMapper.mapToExpenseDto(expense))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto) {
        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setAmount(expenseDto.amount());
        expense.setExpenseDate(expenseDto.expenseDate());
        if(expenseDto.categoryDto() != null){
            Category category = categoryRepository
                    .findById(expenseDto.categoryDto().id())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            expense.setCategory(category);
        }

        Expense exp = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseDto(exp);

    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);
    }
}
