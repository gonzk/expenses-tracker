package ui;

import model.Expense;
import persistence.Read;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static model.Expense.parseCategory;

//This class is responsible for deals with the log in the ExpensesTracker
public class Log {
    private ExpensesTracker tracker;
    private Scanner sc;

    public Log(ExpensesTracker tracker) {
        this.tracker = tracker;
        this.sc = tracker.getSc();
    }

    //EFFECTS: loads the expenses from the file, if the file exists
    public void load() {
        try {
            List<Expense> expenses = Read.readExpenses("./data/" + tracker.getName() + ".json");
            for (Expense e: expenses) {
                tracker.getExpensesLog().addExpense(e);
            }
            System.out.println("Hi " + tracker.getName() + "!" + "\nWhat would you like to do today? \n");
        } catch (IOException e) {
            System.out.println("Hi " + tracker.getName() + "!" + "\nWhat would you like to do today? \n");
        }
    }

    // EFFECTS: displays menu of log options to user
    public void menu() {
        System.out.println("\nPlease select from the following options");
        System.out.println("\titem -> filters log by item");
        System.out.println("\tcategory -> filters log by category");
        System.out.println("\tremove -> remove an expense");
        System.out.println("\thome -> see home menu");
    }

    public void remove() {
        System.out.print("Enter the year of expense: \n");
        int removeYear = sc.nextInt();

        System.out.print("Enter the month of expense: \n");
        int removeMonth = sc.nextInt();

        System.out.print("Enter the day of expense: \n");
        int removeDay = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter the item name: \n");
        String removeItem = sc.nextLine();

        System.out.print("How much did it cost?  \n");
        double removeCost = sc.nextDouble();

        System.out.print("Choose one of the categories below: "
                + "\nMEAL\nGROCERIES\nTRAVEL\nENTERTAINMENT\nBILLS\nSHOPPING\nTECH\nTRANSPORTATION\nOTHER\n");
        Expense.Category removeCategory = parseCategory(sc.next());

        Expense remove = new Expense(removeYear, removeMonth, removeDay, removeItem, removeCategory, removeCost);

        tracker.getExpensesLog().removeExpense(remove);
        print(tracker.getExpensesLog().getExpenses());
        try {
            tracker.getWriter().write(tracker.getExpensesLog().getExpenses(), "./data/" + tracker.getName() + ".json");
        } catch (IOException e) {
            System.out.println("Something went wrong!!");
        }

        System.out.println("Your new overall expenses total: " + tracker.getExpensesLog().findTotalExpenses());
    }

    //MODIFIES: this
    //EFFECTS: prints out the user's expenses log filtered to show the given category
    public void filteredLogCategory() {
        System.out.println("What category would you like to look for? ");
        String type = sc.next();

        Expense.Category category = parseCategory(type);

        print(tracker.getExpensesLog().findExpense(category));

        System.out.println("Your overall expenses total for this category is: "
                + tracker.getExpensesLog().findTotalExpensesCategory(type));

    }

    //MODIFIES: this
    //EFFECTS: prints out the user's expenses log filtered to show the items that match their input
    public void filteredLogItem() {
        sc.nextLine();
        System.out.println("What item would you like to look for? ");
        String item = sc.nextLine();

        print(tracker.getExpensesLog().findExpense(item));

        System.out.println("Your overall expenses total for this item is: "
                + tracker.getExpensesLog().findTotalExpensesItem(item) + "\n");
    }

    //REQUIRES: month must be entered as MM and year must be entered as yyyy
    //MODIFIES: this
    //EFFECTS: shows the user their monthly expenses and total for a given month and year
    public void seeMonthlyTotal() {
        System.out.print("Please enter the month (MM): \n");
        int month = sc.nextInt();
        System.out.println("Please enter the year (yyyy): ");
        int year = sc.nextInt();

        print(tracker.getExpensesLog().findMonthlyYearExpenses(month, year));

        System.out.println("Your monthly expenses total is: "
                + tracker.getExpensesLog().findMonthlyTotalExpenses(month, year));


    }

    //MODIFIES: this
    //EFFECTS: prints a log of a list of expenses
    public void print(List<Expense> expense) {
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("%10s %15s %20s %15s", "DATE", "ITEM", "CATEGORY", "COST");
        System.out.println();
        System.out.println("--------------------------------------------------------------------");

        for (Expense e : expense) {
            System.out.format("%10s %15s %20s %15s", e.getDate(), e.getItem(), e.getCategory(),
                    e.getCost());
            System.out.println();
        }

        System.out.println("--------------------------------------------------------------------");
    }
}
