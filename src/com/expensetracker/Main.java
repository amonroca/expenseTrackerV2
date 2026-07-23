package com.expensetracker;

import com.expensetracker.service.ExpenseService;
import com.expensetracker.storage.FileStorage;
import com.expensetracker.storage.Storage;
import com.expensetracker.ui.ConsoleUI;

// Entry point — wires all layers together.
public class Main {
    public static void main(String[] args) {
        Storage storage = new FileStorage();
        ExpenseService service = new ExpenseService(storage);
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}
