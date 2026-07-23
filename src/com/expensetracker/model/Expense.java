package com.expensetracker.model;

import java.time.LocalDate;

// Single expense entry — holds data only, no business logic.
public class Expense {

    private int id;
    private String description;
    private double amount;
    private String category;
    private LocalDate date;

    // Default constructor
    public Expense(int id, String description, double amount, String category, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Formatted single-line summary for console output.
    @Override
    public String toString() {
        return String.format("[%3d] %-25s $%8.2f  %-15s  %s",
                id, description, amount, category, date);
    }

    // Pipe-delimited line written to the data file.
    public String toStorageLine() {
        return id + "|" + description + "|" + amount + "|" + category + "|" + date;
    }

    // Rebuilds an Expense from a storage line.
    public static Expense fromStorageLine(String line) {
        String[] parts = line.split("\\|", 5);
        return new Expense(
                Integer.parseInt(parts[0]),
                parts[1],
                Double.parseDouble(parts[2]),
                parts[3],
                LocalDate.parse(parts[4]));
    }
}
