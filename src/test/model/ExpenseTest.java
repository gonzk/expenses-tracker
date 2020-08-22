package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Unit tests for the Expense class.
 */

class ExpenseTest {
    private Expense e;
    private Expense expense;
    private Expense other;

    @BeforeEach
    void setUp() {
        e = new Expense(2020, 07, 23, "cookie", Expense.Category.MEAL, 5.00);
        expense = new Expense(2020, 06, 23, "Mac", Expense.Category.TECH, 1000.39);
        other = new Expense(2019, 12, 11, "cookie", Expense.Category.GROCERIES, 5.00);
    }

    @Test
    void findMonthTest() {
        e.getExpenseMonth();
        assertEquals(7, e.getExpenseMonth());
    }

    @Test
    void findYearTest() {
        e.getExpenseYear();
        assertEquals(2020, e.getExpenseYear());
    }


    @Test
    void isMonthTrue() {
        assertTrue(e.isMonth(7));
    }

    @Test
    void isMonthFalse() {
        assertFalse(e.isMonth(12));
    }

    @Test
    void isCategoryTrue() {
        assertTrue(e.isCategory(Expense.Category.MEAL));
    }

    @Test
    void isCategoryFalse() {
        assertFalse(e.isCategory(Expense.Category.BILLS));
    }

    @Test
    void setItemNew() {
        e.setItem("brownie");
        assertEquals("brownie", e.getItem());
    }

    @Test
    void setCategoryNew() {
        e.setCategory(Expense.Category.TRANSPORTATION);
        assertEquals(Expense.Category.TRANSPORTATION, e.getCategory());
    }

    @Test
    void setCostNew() {
        e.setCost(100.35);
        assertEquals(new BigDecimal("100.35").setScale(2, BigDecimal.ROUND_HALF_UP), e.getCost());
    }

    @Test
    void getDateExpense() {
        assertEquals(LocalDate.of(2020, 07, 23), e.getDate());
    }

    @Test
    void isYearTestTrue() {
        assertTrue(e.isYear(2020));
    }

    @Test
    void isYearTestFalse() {
        assertFalse(e.isYear(2010));
    }

    @Test
    void isItemTestTrue() {
        assertTrue(e.isItem("cookie"));
    }

    @Test
    void isItemTestFalse() {
        assertFalse(e.isItem("cookie dough"));
        assertFalse(e.isItem("pizza"));
    }

    @Test
    void isItemTestTrueSimilar() {
        e.setItem("cookie dough");
        assertTrue(e.isItem("cookie"));
    }

    @Test
    void isItemTestTrueCaseDifferent() {
        assertTrue(e.isItem("Cookie"));
    }

    @Test
    void setDateTest() {
        expense.setDate(2019, 8, 07);
        assertEquals(LocalDate.of(2019, 8, 07), expense.getDate());
    }

    @Test
    void getExpenseDayTest() {
        assertEquals(23, e.getExpenseDay());
    }

    @Test
    void testEqualsTrue() {
        Expense expense1 = new Expense(2020, 7, 23, "cookie", Expense.Category.MEAL, 5.00);
        assertEquals(e, expense1);
        assertEquals(e.hashCode(), expense1.hashCode());
        assertTrue(e.equals(expense1));
    }

    @Test
    void testEqualsFalseDate() {
        Expense expense1 = new Expense(2020, 8, 23, "cookie", Expense.Category.MEAL, 5.00);
        assertFalse(e.equals(expense1));
        assertNotEquals(e.hashCode(), expense1.hashCode());
    }

    @Test
    void testEqualsFalseItem() {
        Expense expense1 = new Expense(2020, 7, 23, "coffee", Expense.Category.MEAL, 5.00);
        assertFalse(e.equals(expense1));
        assertNotEquals(e.hashCode(), expense1.hashCode());
    }

    @Test
    void testEqualsFalseCategory() {
        Expense expense1 = new Expense(2020, 7, 23, "cookie", Expense.Category.GROCERIES,
                5.00);
        assertFalse(e.equals(expense1));
        assertNotEquals(e.hashCode(), expense1.hashCode());
    }

    @Test
    void testEqualsFalseCost() {
        Expense expense1 = new Expense(2020, 7, 23, "cookie", Expense.Category.MEAL,
                6.00);
        assertFalse(e.equals(expense1));
        assertNotEquals(e.hashCode(), expense1.hashCode());
    }

    @Test
    void testEqualsFalse() {
        assertFalse(e.equals(expense));
        assertNotEquals(e.hashCode(), expense.hashCode());
    }

    @Test
    void testEqualsTrueSame() {
        assertTrue(e.equals(e));
        assertEquals(e.hashCode(), e.hashCode());
    }

    @Test
    void testEqualsFalseNotExpenseInstance() {
        assertFalse(e.equals(2));
    }

    @Test
    void parseCategoryMealTest() {
        assertEquals(Expense.Category.MEAL, e.parseCategory("MEAL"));
        assertEquals(Expense.Category.MEAL, e.parseCategory("Meal"));
        assertEquals(Expense.Category.MEAL, e.parseCategory("meal"));

    }

    @Test
    void parseCategoryGroceriesTest() {
        assertEquals(Expense.Category.GROCERIES, e.parseCategory("GROCERIES"));
        assertEquals(Expense.Category.GROCERIES, e.parseCategory("Groceries"));
        assertEquals(Expense.Category.GROCERIES, e.parseCategory("groceries"));

    }

    @Test
    void parseCategoryTECHTest() {
        assertEquals(Expense.Category.TECH, e.parseCategory("TECH"));
        assertEquals(Expense.Category.TECH, e.parseCategory("Tech"));
        assertEquals(Expense.Category.TECH, e.parseCategory("tech"));

    }

    @Test
    void parseCategoryTravelTest() {
        assertEquals(Expense.Category.TRAVEL, e.parseCategory("TRAVEL"));
        assertEquals(Expense.Category.TRAVEL, e.parseCategory("Travel"));
        assertEquals(Expense.Category.TRAVEL, e.parseCategory("travel"));

    }

    @Test
    void parseCategoryEntertainmentTest() {
        assertEquals(Expense.Category.ENTERTAINMENT, e.parseCategory("ENTERTAINMENT"));
        assertEquals(Expense.Category.ENTERTAINMENT, e.parseCategory("Entertainment"));
        assertEquals(Expense.Category.ENTERTAINMENT, e.parseCategory("entertainment"));
    }

    @Test
    void parseCategoryBillsTest() {
        assertEquals(Expense.Category.BILLS, e.parseCategory("BILLS"));
        assertEquals(Expense.Category.BILLS, e.parseCategory("Bills"));
        assertEquals(Expense.Category.BILLS, e.parseCategory("bills"));
    }

    @Test
    void parseCategoryShoppingTest() {
        assertEquals(Expense.Category.SHOPPING, e.parseCategory("SHOPPING"));
        assertEquals(Expense.Category.SHOPPING, e.parseCategory("Shopping"));
        assertEquals(Expense.Category.SHOPPING, e.parseCategory("shopping"));
    }

    @Test
    void parseCategoryTranspoTest() {
        assertEquals(Expense.Category.TRANSPORTATION, e.parseCategory("TRANSPORTATION"));
        assertEquals(Expense.Category.TRANSPORTATION, e.parseCategory("Transportation"));
        assertEquals(Expense.Category.TRANSPORTATION, e.parseCategory("transportation"));
    }

    @Test
    void parseCategoryOtherTest() {
        assertEquals(Expense.Category.OTHER, e.parseCategory("OTHER"));
        assertEquals(Expense.Category.OTHER, e.parseCategory("Other"));
        assertEquals(Expense.Category.OTHER, e.parseCategory("other"));
    }

}