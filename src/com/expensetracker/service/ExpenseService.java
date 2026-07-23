package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Business logic layer — the UI and storage never communicate directly.
public class ExpenseService {

    private final ArrayList<Expense> expenses; // in-memory list (ArrayList)
    private final Storage storage;
    private int nextId; // auto-incremented unique ID

    public ExpenseService(Storage storage) {
        this.storage = storage;
        this.expenses = new ArrayList<>(storage.load());
        // Continue from the highest existing ID to avoid duplicates across restarts.
        this.nextId = expenses.stream().mapToInt(Expense::getId).max().orElse(0) + 1;
    }

    // Adds a new expense dated today.
    public Expense addExpense(String description, double amount, String category) {
        return addExpense(description, amount, category, LocalDate.now());
    }

    // Adds a new expense with an explicit date.
    public Expense addExpense(String description, double amount, String category, LocalDate date) {
        Expense expense = new Expense(nextId++, description, amount, category, date);
        expenses.add(expense);
        storage.save(expenses);
        return expense;
    }

    // Removes the expense with the given ID; returns true if found.
    public boolean removeExpense(int id) {
        boolean removed = expenses.removeIf(e -> e.getId() == id);
        if (removed) {
            storage.save(expenses);
        }
        return removed;
    }

    // Defensive copy — callers cannot modify the internal list.
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    // Case-insensitive filter by category.
    public List<Expense> getByCategory(String category) {
        List<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                result.add(e);
            }
        }
        return result;
    }

    // Sum of all expense amounts.
    public double getTotalAmount() {
        double total = 0.0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    // Groups expenses by category; uses a HashMap for O(1) lookups.
    public Map<String, Double> getSummaryByCategory() {
        Map<String, Double> summary = new HashMap<>();
        for (Expense e : expenses) {
            summary.merge(e.getCategory(), e.getAmount(), Double::sum);
        }
        return summary;
    }

    public boolean isEmpty() {
        return expenses.isEmpty();
    }
}
