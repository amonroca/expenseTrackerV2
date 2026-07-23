package com.expensetracker.storage;

import com.expensetracker.model.Expense;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Persists expenses to a plain-text file, one entry per line.
public class FileStorage implements Storage {

    private final String filePath;

    public FileStorage() {
        this("expenses.dat");
    }

    // Custom path constructor — useful for keeping test data separate.
    public FileStorage(String filePath) {
        this.filePath = filePath;
    }

    // Overwrites the file with the current expense list.
    @Override
    public void save(List<Expense> expenses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Expense expense : expenses) {
                writer.write(expense.toStorageLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving expenses: " + e.getMessage());
        }
    }

    // Reads all expenses from the file; returns empty list if file doesn't exist.
    @Override
    public List<Expense> load() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return expenses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    expenses.add(Expense.fromStorageLine(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading expenses: " + e.getMessage());
        }

        return expenses;
    }
}
