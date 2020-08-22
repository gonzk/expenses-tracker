package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

// The Expense Class represents a single expense that is made. Each Expense
// is represented by the date of purchase, item name, category, and cost
public class Expense {

    public enum Category {
        MEAL, GROCERIES, TRAVEL, ENTERTAINMENT, BILLS, SHOPPING, TECH,
        TRANSPORTATION, OTHER
    }

    private LocalDate date;
    private String item;
    private Category category;
    private double cost;


    //EFFECTS: constructs a new instance of Expense with fields set to the given inputs
    public Expense(int year, int month, int day, String item, Category category, double cost) {
        this.date = LocalDate.of(year, month, day);
        this.item = item;
        this.category = category;
        this.cost = cost;
    }

    //EFFECTS: returns the month when expense was made
    public int getExpenseMonth() {
        return date.getMonthValue();
    }

    //EFFECTS: returns the year when expense was made
    public int getExpenseYear() {
        return date.getYear();
    }

    //EFFECTS: returns the day when expense was made
    public int getExpenseDay() {
        return date.getDayOfMonth();
    }

    //REQUIRES: 0 < month <= 12
    //EFFECTS: returns true if expense is made in the given month; false otherwise
    public boolean isMonth(int month) {
        return getExpenseMonth() == month;
    }

    //REQUIRES: year is written as yyyy
    //EFFECTS: returns true if expense is made in the given month; false otherwise
    public boolean isYear(int year) {
        return getExpenseYear() == year;
    }

    //EFFECTS: returns true if expense is made in the given month; false otherwise
    public boolean isCategory(Category category) {
        return getCategory() == category;
    }

    //source: https://tinyurl.com/y55a7w7f (had to shorten the link because it was too long)
    //EFFECTS: returns true if the expense matches the given item false otherwise
    public boolean isItem(String item) {
        String find = this.item;
        return Pattern.compile(Pattern.quote(item), Pattern.CASE_INSENSITIVE).matcher(find).find();
    }

    //EFFECTS: returns true if o is the same as an instance of an Expense
    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (o instanceof Expense) {
            Expense expense = (Expense) o;
            isEqual = hashCode() == o.hashCode();
        }
        return isEqual;
    }

    //EFFECTS: generates a new hashcode
    @Override
    public int hashCode() {
        return Objects.hash(date, item, category, cost);
    }

    //setters

    //MODIFIES: this, date;
    //EFFECTS: sets the title of the item
    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    //MODIFIES: this;
    //EFFECTS: sets the title of the item
    public void setItem(String item) {
        this.item = item;
    }

    //MODIFIES: this;
    //EFFECTS: sets the category
    public void setCategory(Category category) {
        this.category = category;
    }

    //MODIFIES: this;
    //EFFECTS: sets the cost of the item
    public void setCost(double cost) {
        this.cost = cost;

    }

    //getters

    //EFFECTS: returns the date of when the expense was made
    public LocalDate getDate() {
        return date;
    }

    //EFFECTS: returns the name of the expense
    public String getItem() {
        return item;
    }

    //EFFECTS: returns the category for which the expense was made
    public Category getCategory() {
        return category;
    }

    //EFFECTS: returns the cost of the expense
    public BigDecimal getCost() {
        BigDecimal round = BigDecimal.valueOf(cost);
        round = round.setScale(2, RoundingMode.HALF_UP);
        return round;
    }

    //MODIFIES: type
    //EFFECTS: returns the corresponding category to the given string
    public static Category parseCategory(String type) {
        if (type.equalsIgnoreCase("MEAL")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else if (type.equalsIgnoreCase("GROCERIES")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else if (type.equalsIgnoreCase("TRAVEL")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else if (type.equalsIgnoreCase("ENTERTAINMENT")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else if (type.equalsIgnoreCase("BILLS")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else if (type.equalsIgnoreCase("SHOPPING")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else if (type.equalsIgnoreCase("TECH")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else if (type.equalsIgnoreCase("TRANSPORTATION")) {
            return Expense.Category.valueOf(type.toUpperCase());
        } else {
            return Expense.Category.valueOf(type.toUpperCase());
        }
    }
}
