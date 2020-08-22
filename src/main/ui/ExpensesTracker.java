package ui;


import model.ExpensesLog;
import persistence.Write;

import java.util.Scanner;

// The Expenses Tracker class is the class that performs all of the adding of expenses and shows the monthly total
// expenses and filters the log by either category or item
public class ExpensesTracker {
    private ExpensesLog expensesLog;
    private Scanner sc;
    private Write writer = new Write();
    private String name;
    private Log log;
    private Add add;

    // EFFECTS: initiates the expenses tracker
    public ExpensesTracker() {
        expensesLog = new ExpensesLog();
        sc = new Scanner(System.in);
        log = new Log(this);
        add = new Add(this);
        startTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void startTracker() {
        boolean go = true;

        greet();

        keepGoing(go);

        System.out.println("\nHave a nice day, " + name + "! \nThank you for using the Expenses Tracker!");

    }

    //MODIFIES: this
    //EFFECTS: continues to display the main menu until user no longer has anything else they would like to do
    private void keepGoing(boolean go) {
        String input;
        do {
            System.out.println("\nIs there anything else you would like to do today? ");
            String answer = sc.next();
            if (answer.equalsIgnoreCase("yes")) {
                menu();
                input = sc.next();
                input = input.toLowerCase();
                init(input);
            } else if (answer.equalsIgnoreCase("no")) {
                go = false;
            } else {
                System.out.println("Only yes or no answers please!!");
            }
        } while (go);
    }

    //EFFECTS: displays greeting
    private void greet() {
        String input;
        System.out.println("Welcome to the Expenses Tracker!");

        System.out.println("What is your name? \n");
        name = sc.nextLine();
        loadExpenses(name);

        System.out.println("\tadd -> add an expense");
        System.out.println("\tlog -> see my expenses log");
        System.out.println("\tmonthly -> see my monthly total expenses");

        input = sc.next();
        input = input.toLowerCase();
        init(input);
    }

    // MODIFIES: this
    // EFFECTS: loads expenses from LOG_FILE, if that file exists; otherwise just print greeting
    private void loadExpenses(String name) {
        log.load();
    }

    // EFFECTS: displays menu of options to user
    private void menu() {
        System.out.println("\nPlease select from the following options");
        System.out.println("\tadd -> add an expense");
        System.out.println("\tlog -> see my expenses log");
        System.out.println("\tmonthly -> see my monthly total expenses");
    }

    // EFFECTS: displays menu of log options to user
    private void menuLog() {
        log.menu();
    }

    // MODIFIES: this
    // EFFECTS: initializes user input
    private void init(String input) {
        if (input.equalsIgnoreCase("add")) {
            add.addExpense();
        } else if (input.equalsIgnoreCase("log")) {
            seeLog();
        } else if (input.equalsIgnoreCase("monthly")) {
            log.seeMonthlyTotal();
        } else {
            System.out.println("Please try again!");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equalsIgnoreCase("item")) {
            log.filteredLogItem();
        } else if (command.equalsIgnoreCase("category")) {
            log.filteredLogCategory();
        } else if (command.equalsIgnoreCase("remove")) {
            log.remove();
        } else {
            sc.nextLine();
        }
    }

    //MODIFIES: this
    //EFFECTS: prints the user's expenses log and gives them the option to filter it by item or category
    private void seeLog() {
        log.print(expensesLog.getExpenses());
        System.out.println("Your total expenses is: " + expensesLog.findTotalExpenses());

        menuLog();
        String command = sc.next();
        processCommand(command);
    }

    //EFFECTS: returns expenses log
    public ExpensesLog getExpensesLog() {
        return expensesLog;
    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }

    //EFFECTS: returns scanner
    public Scanner getSc() {
        return sc;
    }

    //EFFECTS: returns writer
    public Write getWriter() {
        return writer;
    }
}


