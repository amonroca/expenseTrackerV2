package com.expensetracker.ui;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Menu-driven console interface — reads input and prints output only.
public class ConsoleUI {

    private static final String SEP = "─".repeat(60);

    private final ExpenseService service;
    private final Scanner scanner;

    public ConsoleUI(ExpenseService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // Main loop — runs until the user exits.
    public void start() {
        printHeader();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("  Option: ");

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    listExpenses();
                    break;
                case 3:
                    removeExpense();
                    break;
                case 4:
                    showSummary();
                    break;
                case 5:
                    filterByCategory();
                    break;
                case 0:
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 0-5.");
            }
        }

        scanner.close();
    }

    // --- Private helpers ---

    private void printHeader() {
        System.out.println(SEP);
        System.out.println("        EXPENSE TRACKER");
        System.out.println(SEP);
    }

    private void printMenu() {
        System.out.println("\n" + SEP);
        System.out.println("  1. Add expense");
        System.out.println("  2. List all expenses");
        System.out.println("  3. Remove expense");
        System.out.println("  4. Summary by category");
        System.out.println("  5. Filter by category");
        System.out.println("  0. Exit");
        System.out.println(SEP);
    }

    private void addExpense() {
        System.out.println("\n-- Add Expense --");

        System.out.print("  Description : ");
        String description = scanner.nextLine().trim();

        double amount = readDouble("  Amount ($)  : ");

        System.out.print("  Category    : ");
        String category = scanner.nextLine().trim();

        // Input validation
        if (description.isEmpty()) {
            System.out.println("Description cannot be empty.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
        if (category.isEmpty()) {
            System.out.println("Category cannot be empty.");
            return;
        }
        // '|' is the storage delimiter — reject it to avoid corrupting the file.
        if (description.contains("|") || category.contains("|")) {
            System.out.println("Inputs cannot contain the '|' character.");
            return;
        }

        Expense added = service.addExpense(description, amount, category);
        System.out.println("Saved: " + added);
    }

    private void listExpenses() {
        List<Expense> list = service.getAllExpenses();

        if (list.isEmpty()) {
            System.out.println("\nNo expenses recorded yet.");
            return;
        }

        System.out.println("\n-- All Expenses --");
        System.out.printf("  %-5s %-25s %10s  %-15s  %s%n",
                "ID", "Description", "Amount", "Category", "Date");
        System.out.println("  " + "─".repeat(70));

        for (Expense e : list) {
            System.out.println("  " + e);
        }

        System.out.println("  " + "─".repeat(70));
        System.out.printf("  %-5s %-25s $%9.2f%n", "", "TOTAL", service.getTotalAmount());
    }

    private void removeExpense() {
        if (service.isEmpty()) {
            System.out.println("\nNo expenses to remove.");
            return;
        }

        listExpenses();
        int id = readInt("\n  Enter ID to remove (0 to cancel): ");
        if (id == 0)
            return;

        boolean removed = service.removeExpense(id);
        System.out.println(removed
                ? "Expense #" + id + " removed."
                : "No expense with ID " + id + ".");
    }

    private void showSummary() {
        Map<String, Double> summary = service.getSummaryByCategory();

        if (summary.isEmpty()) {
            System.out.println("\nNo expenses to summarize.");
            return;
        }

        System.out.println("\n-- Summary by Category --");
        System.out.printf("  %-20s %10s%n", "Category", "Total");
        System.out.println("  " + "─".repeat(32));

        for (Map.Entry<String, Double> entry : summary.entrySet()) {
            System.out.printf("  %-20s $%9.2f%n", entry.getKey(), entry.getValue());
        }

        System.out.println("  " + "─".repeat(32));
        System.out.printf("  %-20s $%9.2f%n", "TOTAL", service.getTotalAmount());
    }

    private void filterByCategory() {
        System.out.print("\n  Category to filter: ");
        String category = scanner.nextLine().trim();

        List<Expense> filtered = service.getByCategory(category);

        if (filtered.isEmpty()) {
            System.out.println("No expenses found for category '" + category + "'.");
            return;
        }

        System.out.println("\n-- Expenses in '" + category + "' --");
        for (Expense e : filtered) {
            System.out.println("  " + e);
        }

        double categoryTotal = filtered.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("  Category total: $%.2f%n", categoryTotal);
    }

    // Loops until the user enters a valid integer.
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a whole number.");
            }
        }
    }

    // Loops until the user enters a valid decimal number.
    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid amount.");
            }
        }
    }
}
