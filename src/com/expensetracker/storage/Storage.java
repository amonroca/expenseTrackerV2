package com.expensetracker.storage;

import com.expensetracker.model.Expense;
import java.util.List;

// Contract for any persistence backend.
public interface Storage {
    void save(List<Expense> expenses);

    List<Expense> load();
}
