package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// The ExpensesLog Class stores a list of expenses
public class ExpensesLog {
    public List<Expense> expenses;

    //EFFECTS: Constructs an expenses log that is empty
    public ExpensesLog() {
        expenses = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds the given expense to the the ExpensesLog
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    //REQUIRES: ExpensesLog is not empty
    //MODIFIES: this
    //EFFECTS: removes the given expense if expense(s) is/are found;
    //         otherwise, do nothing
    public void removeExpense(Expense remove) {
        expenses.removeIf(e -> e.equals(remove));
    }

    //EFFECTS: returns a list of expenses that have items that match the given item
    public List<Expense> findExpense(String item) {
        List<Expense> found = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.isItem(item)) {
                found.add(e);
            }
        }
        found.sort(Comparator.comparing(Expense::getDate).reversed());
        return found;
    }

    //EFFECTS: returns a list of expenses that have items that match the given category
    public List<Expense> findExpense(Expense.Category type) {
        List<Expense> found = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory() == type) {
                found.add(e);
            }
        }
        found.sort(Comparator.comparing(Expense::getDate).reversed());
        return found;
    }

    //EFFECTS: returns a list of expenses that have items that match the given month and year
    public List<Expense> findMonthlyYearExpenses(int month, int year) {
        List<Expense> found = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.isMonth(month) && e.isYear(year)) {
                found.add(e);
            }
        }
        found.sort(Comparator.comparing(Expense::getDate).reversed());
        return found;
    }

    //EFFECTS: returns a sorted list of expenses stored in the log
    public List<Expense> getExpenses() {
        List<Expense> found = new ArrayList<>(expenses);
        found.sort(Comparator.comparing(Expense::getDate).reversed());
        return found;
    }

    //EFFECTS: returns the total of all the expenses in the log
    public BigDecimal findTotalExpenses() {
        BigDecimal total = new BigDecimal(0);
        for (Expense e : expenses) {
            total = total.add(e.getCost());
        }

        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }


    //REQUIRES: 0 < month <= 12 and year must be the same as given year
    //EFFECTS: returns the monthly total expenses in a given month and year
    public BigDecimal findMonthlyTotalExpenses(int month, int year) {
        BigDecimal total = new BigDecimal(0);
        for (Expense e : expenses) {
            if (e.isMonth(month) && e.isYear(year)) {
                total = total.add(e.getCost());
            }
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }

    //REQUIRES: 0 < month <= 12
    //EFFECTS: returns the monthly total expenses in a given month and category
    public BigDecimal findMonthlyTotalExpensesCategory(int month, int year, Expense.Category category) {
        BigDecimal total = new BigDecimal(0);
        for (Expense e : expenses) {
            if ((e.isMonth(month)) && (e.isYear(year)) && (e.isCategory(category))) {
                total = total.add(e.getCost());
            }
        }
        //source: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }

    //EFFECTS: returns the monthly total expenses in a given month and category
    public BigDecimal findTotalExpensesCategory(String category) {
        BigDecimal total = new BigDecimal(0);
        for (Expense e : expenses) {
            if (e.isCategory(Expense.parseCategory(category))) {
                total = total.add(e.getCost());
            }
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }

    //EFFECTS: returns the monthly total expenses in a given month and category
    public BigDecimal findTotalExpensesItem(String item) {
        BigDecimal total = new BigDecimal(0);
        for (Expense e : expenses) {
            if (e.isItem(item)) {
                total = total.add(e.getCost());
            }
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }

    //EFFECTS: returns the size of the ExpensesLog
    public int length() {
        return expenses.size();
    }
}

