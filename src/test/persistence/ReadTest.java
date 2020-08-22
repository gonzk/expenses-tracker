package persistence;

import model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static model.Expense.Category.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * Unit tests for the Read class.
 */

public class ReadTest {
    private Read read;

    @BeforeEach
    void runBefore() {
        read = new Read();
    }

    @Test
    void testExpensesFile1() {
        try {
            List<Expense> expenses = read.readExpenses("./data/testExpensesFile1.json");
            Expense expense1 = expenses.get(0);
            assertEquals(LocalDate.of(2020, 7, 23), expense1.getDate());
            assertEquals("Mac", expense1.getItem());
            assertEquals(TECH, expense1.getCategory());
            assertEquals(new BigDecimal("1000.39"), expense1.getCost());

            Expense expense2 = expenses.get(1);
            assertEquals(LocalDate.of(2019, 12, 11), expense2.getDate());
            assertEquals("cookie", expense2.getItem());
            assertEquals(MEAL, expense2.getCategory());
            assertEquals(new BigDecimal("5.00"), expense2.getCost());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testLog() {
        try {
            List<Expense> expenses = read.readExpenses("./data/log.json");
            Expense expense1 = expenses.get(0);
            assertEquals(LocalDate.of(2020, 8, 7), expense1.getDate());
            assertEquals("cookie", expense1.getItem());
            assertEquals(OTHER, expense1.getCategory());
            assertEquals(new BigDecimal("20.00"), expense1.getCost());

            Expense expense2 = expenses.get(1);
            assertEquals(LocalDate.of(2019, 7, 10), expense2.getDate());
            assertEquals("train tickets", expense2.getItem());
            assertEquals(TRAVEL, expense2.getCategory());
            assertEquals(new BigDecimal("60.00"), expense2.getCost());

            Expense expense3 = expenses.get(2);
            assertEquals(LocalDate.of(2010, 2, 23), expense3.getDate());
            assertEquals("pikachu", expense3.getItem());
            assertEquals(OTHER, expense3.getCategory());
            assertEquals(new BigDecimal("23.00"), expense3.getCost());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Read.readExpenses("./path/does/not/exist/testExpense.json");
            fail("Exception should be thrown!");
        } catch (IOException e) {
            // Exception is thrown
        }
    }
}
