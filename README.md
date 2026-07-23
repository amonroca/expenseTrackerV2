# Overview

I built a command-line expense tracker to deepen my understanding of Java's object-oriented features, the Collections Framework, and file I/O. The goal was to design a clean, extensible architecture from the start — separating concerns into model, service, storage, and UI layers — so each feature I add later slots in without rewriting existing code.

The application lets users add, list, remove, and summarize expenses by category. Data is automatically saved to a file between sessions.

[Software Demo Video](https://youtu.be/zuGNG2l2wOA)

# Development Environment

- VS Code with the Extension Pack for Java
- Java 20.0.2 (Oracle HotSpot 64-bit)
- No external libraries — standard library only (`java.util`, `java.io`, `java.time`)

# Useful Websites

- [Java Collections Framework Overview](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/overview.html)
- [Java File I/O Tutorial](https://docs.oracle.com/javase/tutorial/essential/io/)
- [Java Interfaces and Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/index.html)

# Future Work

- Add a `RecurringExpense extends Expense` class using inheritance.
- Add date-range filtering (`getByDateRange(LocalDate from, LocalDate to)`).
- Add monthly budget limits with over-budget warnings.
