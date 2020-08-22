package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Unit tests for the ExpensesLog class.
 */

class ExpensesLogTest {
    private ExpensesLog expenses;
    private Expense e;
    private Expense expense;
    private Expense other;

    @BeforeEach
    void setUp() {
        expenses = new ExpensesLog();
        e = new Expense(2020, 7, 23, "cookie", Expense.Category.MEAL, 5.00);
        expense = new Expense(2020, 7, 23, "Mac", Expense.Category.TECH, 1000.39);
        other = new Expense(2019, 12, 11, "cookie", Expense.Category.GROCERIES, 5.00);
    }

    @Test
    public void addExpenseEmpty() {
        assertEquals(0, expenses.length());
        expenses.addExpense(e);
        assertEquals(1, expenses.length());
    }

    @Test
    public void addExpenseNotEmpty() {
        assertEquals(0, expenses.length());
        expenses.addExpense(e);
        assertEquals(1, expenses.length());
        expenses.addExpense(e);
        assertEquals(2, expenses.length());
    }

    @Test
    public void removeExpenseOneFound() {
        expenses.addExpense(e);
        assertEquals(1, expenses.length());
        expenses.removeExpense(e);
        assertEquals(0, expenses.length());
    }

    @Test
    public void removeExpenseTwoFound() {
        expenses.addExpense(e);
        assertEquals(1, expenses.length());
        expenses.addExpense(expense);
        assertEquals(2,expenses.length());
        Expense smt = new Expense(2020, 7, 23, "Mac", Expense.Category.TECH, 1000.39);
        expenses.removeExpense(smt);
        assertEquals(1, expenses.length());
    }

    @Test
    public void removeExpenseNotFound() {
        expenses.addExpense(e);
        assertEquals(1, expenses.length());
        expenses.removeExpense(expense);
        assertEquals(1, expenses.length());
    }

    @Test
    public void removeExpenseNotFoundSlightlyDiffExpense() {
        expenses.addExpense(e);
        assertEquals(1,expenses.length());
        expenses.removeExpense(other);
        assertEquals(1, expenses.length());
    }

    @Test
    public void removeExpenseDiffCategory() {
        expenses.addExpense(e);
        assertEquals(1,expenses.length());
        Expense another = new Expense(2020, 7, 23, "cookie", Expense.Category.GROCERIES,
                5.00);
        expenses.removeExpense(another);
        assertEquals(1, expenses.length());
    }

    @Test
    public void removeExpenseDiffCost() {
        expenses.addExpense(e);
        assertEquals(1,expenses.length());
        Expense another = new Expense(2020, 7, 23, "cookie", Expense.Category.MEAL,
                5.25);
        expenses.removeExpense(another);
        assertEquals(1, expenses.length());
    }

    @Test
    public void findExpenseItemFound() {
        expenses.addExpense(e);
        List<Expense> expenses1 = new ArrayList<>();
        expenses1.add(e);
        assertEquals(expenses1, expenses.findExpense("cookie"));
    }

    @Test
    public void findExpenseItemFoundTwo() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        List<Expense> expenses1 = new ArrayList<>();
        expenses1.add(e);
        expenses1.add(other);
        assertEquals(expenses1, expenses.findExpense("cookie"));
    }

    @Test
    public void findExpenseItemFilterFound() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        List<Expense> expenses1 = new ArrayList<>();
        expenses1.add(e);
        assertEquals(expenses1,expenses.findExpense("cookie"));
    }

    @Test
    public void findExpenseItemFilterNotFound() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        List<Expense> expenses1 = new ArrayList<>();
        assertEquals(expenses1, expenses.findExpense("Mac"));
    }

    @Test
    public void findExpenseCategoryFound() {
        expenses.addExpense(e);
        expenses.addExpense(other);

        List<Expense> expenses1 = new ArrayList<>();
        expenses1.add(e);
        assertEquals(expenses1, expenses.findExpense(Expense.Category.MEAL));
    }


    @Test
    public void findExpenseCategoryFilterNotFound() {
        expenses.addExpense(e);
        expenses.addExpense(other);

        List<Expense> expenses1 = new ArrayList<>();
        assertEquals(expenses1, expenses.findExpense(Expense.Category.TECH));
    }

    @Test
    public void findMonthlyTotalExpensesOne() {
        expenses.addExpense(e);
        expenses.findMonthlyTotalExpenses(7, 2020);
        assertEquals(BigDecimal.valueOf(5.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpenses(7, 2020));
    }

    @Test
    public void findMonthlyTotalExpensesTwo() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        expenses.findMonthlyTotalExpenses(7, 2020);
        assertEquals(e.getCost().add(expense.getCost()),
                expenses.findMonthlyTotalExpenses(7, 2020));
    }

    @Test
    public void findMonthlyTotalExpensesFilter() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        other.setDate(2019, 12, 3);
        expenses.findMonthlyTotalExpenses(7, 2020);
        assertEquals(BigDecimal.valueOf(1005.39).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpenses(7, 2020));
    }

    @Test
    public void findMonthlyTotalExpensesNone() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        expenses.findMonthlyTotalExpenses(3, 2012);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpenses(3, 2012));
    }

    @Test
    public void findMonthlyTotalExpensesNotYear() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpenses(7, 2012));
    }

    @Test
    public void findMonthlyTotalExpensesMeal() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        assertEquals(BigDecimal.valueOf(5.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(7, 2020, Expense.Category.MEAL));
    }

    @Test
    public void findMonthlyTotalExpensesBillsNone() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(7, 2020, Expense.Category.BILLS));
    }

    @Test
    public void findMonthlyTotalExpensesTest() {
        expenses.addExpense(e);
        Expense expenses1 = new Expense(2020, 07, 23, "cookie", Expense.Category.GROCERIES,
                5.00);
        expenses.addExpense(expenses1);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(7, 2020, Expense.Category.TECH));
    }

    @Test
    public void findMonthlyTotalExpensesGroceriesTwo() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        Expense another = new Expense(2020, 6, 12, "banana", Expense.Category.GROCERIES, 2.00);
        expenses.addExpense(another);
        assertEquals(BigDecimal.valueOf(2.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(6, 2020, Expense.Category.GROCERIES));
    }

    @Test
    public void findMonthlyTotalExpensesDiffMonth() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        Expense another = new Expense(2020, 12, 7, "banana", Expense.Category.GROCERIES, 2.00);
        expenses.addExpense(another);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(3, 2019, Expense.Category.GROCERIES));
    }

    @Test
    public void findMonthlyTotalExpensesDiffYear() {
        expenses.addExpense(e);
        Expense another = new Expense(2020, 7, 7, "banana", Expense.Category.MEAL, 2.00);
        expenses.addExpense(another);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(7, 2019, Expense.Category.MEAL));
    }

    @Test
    public void findMonthlyTotalExpensesDiffCategory() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        Expense another = new Expense(2020, 12, 7, "banana", Expense.Category.GROCERIES, 2.00);
        expenses.addExpense(another);
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(7, 2020, Expense.Category.BILLS));
    }

    @Test
    public void findMonthlyTotalExpensesEmpty() {
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(7, 2020, Expense.Category.BILLS));
    }



    @Test
    public void getExpensesEmpty() {
        List<Expense> found = new ArrayList<>();
        assertEquals(found, expenses.getExpenses());
    }

    @Test
    public void getExpensesOne() {
        expenses.addExpense(e);
        List<Expense> found = new ArrayList<>();
        found.add(e);
        assertEquals(found, expenses.getExpenses());
    }

    @Test
    public void findTotalExpensesCategoryEmpty() {
        assertEquals(new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesCategory("BILLS"));
    }

    @Test
    public void findTotalExpensesCategoryNone() {
        expenses.addExpense(e);
        assertEquals(new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesCategory("BILLS"));
    }

    @Test
    public void findTotalExpensesCategoryTwo() {
        expenses.addExpense(e);
        Expense another = new Expense(2020, 12, 7,"pizza", Expense.Category.MEAL, 12.00);
        expenses.addExpense(another);
        assertEquals(new BigDecimal("17.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesCategory("MEAL"));
    }

    @Test
    public void findTotalExpensesCategoryFilter() {
        expenses.addExpense(e);
        Expense another = new Expense(2020, 12, 7, "pizza", Expense.Category.MEAL, 12.00);
        expenses.addExpense(another);
        expenses.addExpense(expense);
        assertEquals(new BigDecimal("1000.39").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesCategory("TECH"));
    }

    @Test
    public void findTotalExpensesItemEmpty() {
        assertEquals(new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesItem("cake"));
    }

    @Test
    public void findTotalExpensesItemNone() {
        expenses.addExpense(e);
        assertEquals(new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesItem("cake"));
    }

    @Test
    public void findTotalExpensesItemFoundAll() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        assertEquals(new BigDecimal("10.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesItem("cookie"));
    }

    @Test
    public void findTotalExpensesItemFoundSimilar() {
        expenses.addExpense(e);
        Expense another = new Expense(2020, 12, 7, "cookie dough", Expense.Category.MEAL, 12.00);
        expenses.addExpense(another);
        assertEquals(new BigDecimal("17.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesItem("cookie"));
    }

    @Test
    public void findTotalExpensesItemFilter() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        assertEquals(new BigDecimal("5.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpensesItem("cookie"));
    }

    @Test
    public void findMonthlyTotalExpensesNotMonthAndYr() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        expenses.addExpense(other);
        assertEquals(new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findMonthlyTotalExpenses(1, 2001));
    }

    @Test
    public void findTotalExpensesEmpty() {
        assertEquals(new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpenses());
    }

    @Test
    public void findTotalExpensesTest() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        expenses.addExpense(other);
        assertEquals(new BigDecimal("1010.39").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findTotalExpenses());
    }

    @Test
    public void findMonthlyYearExpensesTestEmpty() {
        assertEquals(0, expenses.findMonthlyYearExpenses(9, 2000).size());
    }

    @Test
    public void findMonthlyYearExpensesTestFound() {
        expenses.addExpense(e);
        assertEquals(1, expenses.findMonthlyYearExpenses(7, 2020).size());
        assertTrue(expenses.findMonthlyYearExpenses(7, 2020).contains(e));
    }

    @Test
    public void findMonthlyYearExpensesTestFilter() {
        expenses.addExpense(e);
        expenses.addExpense(other);
        assertEquals(1, expenses.findMonthlyYearExpenses(7, 2020).size());
        assertTrue(expenses.findMonthlyYearExpenses(7, 2020).contains(e));
        assertFalse(expenses.findMonthlyYearExpenses(7, 2020).contains(other));
    }

    @Test
    public void findMonthlyYearExpensesNotMonthAndYr() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        expenses.addExpense(other);
        assertEquals(0, expenses.findMonthlyYearExpenses(1, 2001).size());
        assertFalse(expenses.findMonthlyYearExpenses(1, 2001).contains(e));
        assertFalse(expenses.findMonthlyYearExpenses(1, 2001).contains(expense));
        assertFalse(expenses.findMonthlyYearExpenses(1, 2001).contains(other));
    }

    @Test
    public void findMonthlyYearExpensesNotYear() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        assertEquals(0, expenses.findMonthlyYearExpenses(7, 2001).size());
        assertFalse(expenses.findMonthlyYearExpenses(7, 2001).contains(e));
        assertFalse(expenses.findMonthlyYearExpenses(7, 2001).contains(expense));
    }

    @Test
    public void findMonthlyYearExpensesNotMonth() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        assertEquals(0, expenses.findMonthlyYearExpenses(12, 2020).size());
        assertFalse(expenses.findMonthlyYearExpenses(12, 2020).contains(e));
        assertFalse(expenses.findMonthlyYearExpenses(12, 2020).contains(expense));
    }

    @Test
    public void notMonthYEarCategoryMonthlyExpenses() {
        expenses.addExpense(e);
        expenses.addExpense(expense);
        assertEquals(new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP),
                expenses.findMonthlyTotalExpensesCategory(03, 2020, Expense.Category.SHOPPING));
    }
}
