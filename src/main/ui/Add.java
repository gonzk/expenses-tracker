package ui;

import model.Expense;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Scanner;

import static model.Expense.parseCategory;

//This class is responsible for adding the expenses to the ExpensesTracker
public class Add {
    private ExpensesTracker tracker;
    private Scanner sc;

    public Add(ExpensesTracker tracker) {
        this.tracker = tracker;
        this.sc = tracker.getSc();
    }

    //REQUIRES: cost of user input must be written as the double type
    //MODIFIES: this
    //EFFECTS: adds a new Expense to the Expenses log
    public void addExpense() {
        //source: https://stackoverflow.com/questions/31487051/java-ask-string-input-with-multiple-words/31487088
        System.out.print("Enter the year of purchase: \n");
        int year = sc.nextInt();

        System.out.print("Enter the month of purchase: \n");
        int month = sc.nextInt();

        System.out.print("Enter the day of purchase: \n");
        int day = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter the item name: \n");
        String item = sc.nextLine();

        System.out.print("How much did it cost?  \n");
        double cost = sc.nextDouble();

        System.out.print("Choose one of the categories below: "
                + "\nMEAL\nGROCERIES\nTRAVEL\nENTERTAINMENT\nBILLS\nSHOPPING\nTECH\nTRANSPORTATION\nOTHER\n");
        Expense.Category category = parseCategory(sc.next());

        tracker.getExpensesLog().addExpense(new Expense(year, month, day, item, category, cost));
        try {
            tracker.getWriter().write(tracker.getExpensesLog().getExpenses(),"./data/" + tracker.getName() + ".json");
        } catch (IOException e) {
            System.out.println("Something went wrong!!");
        }

        System.out.print("Your new expense has been saved to file: \n"
                + "\tDate: " + LocalDate.of(year, month, day) + "\n" + "\tItem: " + item + "\n" + "Category: "
                + category + "\n" + "\tCost: " + BigDecimal.valueOf(cost).setScale(2, RoundingMode.HALF_UP));
    }
}
