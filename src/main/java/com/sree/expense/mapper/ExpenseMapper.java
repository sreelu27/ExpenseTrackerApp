package com.sree.expense.mapper;

import com.sree.expense.dto.CategoryDto;
import com.sree.expense.dto.ExpenseDto;
import com.sree.expense.entity.Category;
import com.sree.expense.entity.Expense;

public class ExpenseMapper {

    public static ExpenseDto mapToExpenseDto(Expense expense){
        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(expense.getCategory());
        return new ExpenseDto(expense.getId(), expense.getAmount(), expense.getExpenseDate(), categoryDto);
    }

    public static Expense mapToExpense(ExpenseDto expenseDto){
        Category category = CategoryMapper.mapToCategory(expenseDto.categoryDto());
        return new Expense(expenseDto.id(), expenseDto.amount(), expenseDto.expenseDate(), category);
    }

}
