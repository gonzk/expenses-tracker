package persistence;

import model.Expense;
import model.ExpensesLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static model.Expense.Category.MEAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Unit tests for the Write class.
 */

public class WriteTest {
    private static final String TEST_FILE = "./data/testExpenses.json";
    private Write testWrite;
    private Expense expense;
    private ExpensesLog expenses;

    @BeforeEach
    void runBefore() {
        testWrite = new Write();
        expense = new Expense(2020, 7, 23, "cookie", MEAL, 5.00);
        expenses = new ExpensesLog();

    }

    @Test
    void testWriteExpense() {
        expenses.addExpense(expense);
        try {
            testWrite.write(expenses.getExpenses(), TEST_FILE);

            List<Expense> expenseList = Read.readExpenses(TEST_FILE);
            Expense e = expenseList.get(0);
            assertEquals(LocalDate.of(2020, 7, 23), e.getDate());
            assertEquals("cookie", e.getItem());
            assertEquals(MEAL, e.getCategory());
            assertEquals(new BigDecimal("5.00"), e.getCost());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testException() {
        try {
            testWrite.write(expenses.getExpenses(), "");
            fail("IOException should have been thrown");
        } catch (IOException e) {
            //exception is thrown
        }
    }

}
