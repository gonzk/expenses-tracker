# My Personal Project

## An Expenses Tracker

**What will the application do?**

The ***Expenses Tracker*** application will allow user sto keep track of
all of their expenses. For each expense, they can select at least one category
to describe it (including the name of the item and  when they purchased it) and enter 
how much they spend on the item. It will also provide a pie chart that allows the user to visually
determine what they spend the most on each month. This pie chart will be split up into the different 
categories provided in the application.

**Who will use it? Why is this project of interest to me?**

Anyone who is interested in keeping track of their expenses should definitely
use this application! I enjoy buying food and shopping. However, it can be quite hard to keep 
track of all of my expenses. As such, I am interested in developing my own application to help
keep track of my expenses. It would also be interesting as to what category (e.g., food) I tend to 
spend most of my money on.

### User Stories

* As a user, I want to be able to add an expense to my expenses log
* As a user, I want to be able to view my monthly expenses for a given month and year
* As a user, I want to be able to type an item name and see a list of my expenses that contains the given item name
* As a user, I want to be able to select a category and see a list of my expenses for that category

* As a user, whenever I add an expense to my expenses log, I want it to be saved automatically to a file 
* As a user, when I startPanel the Expenses Tracker, after previously quitting it, I want to able to retrieve what 
I've previously saved in my log

#### Other User Stories I've Also Implemented
* As a user, I want to be able to view all of my expenses in my expenses log
* As a user, I want to be able to see my total expenses for a given category when I see a list of 
expenses for that category
* As a user, I want to be able to see my total expenses for a given item when I see a list of 
expenses that contains the given item 
* As a user, I want to be able to remove an expense from my expenses log
* As a user, I want to be able to see a pie chart of my expenses and total for a given month and year

### Instructions for Grader
* You can generate the first required event by using the filter box in the "Expenses Log" page to filter 
the expenses by item or category.
* You can generate the second required event by selecting the "Log" button on the home page clicking a row on the log
table and then clicking the "Remove" button.
* You can locate my visual component by clicking on the "Monthly chart" button. Then you will be asked to enter a month
and year. If there is at least one expense for the given month and year, a pie chart will appear. Otherwise, you will 
just see an empty frame without a chart.
* You can save the state of my application by clicking on the "ADD" button once you've added an expense.
* You can reload the state of my application by clicking on the log button.
* (Note: once the application starts, all the information in the expenses log is automatically loaded. You can see the 
log by following the previous instruction)

### Phase 4: Task 2
> Include a type hierarchy in your code other than the one that uses the Saveable interface introduced in Phase 2.  
You must have more than one subclass and your subclasses must have distinct functionality.  They must therefore 
override at least one method inherited from a super type and override it in different ways in each of the subclasses.

The superclass is the ExpensesTracker panel. The subclasses are AddPanel, LogPanel, and MonthlyPanel. The methods that
are overriden are:
* `setFrame()`
* `setHome()`
* `setHomeButton()`

### Phase 4: Task 3
There was poor cohesion in the MonthlyPanel class. Before refactoring, it contained methods that specifically pertained
to the month and year spinners. However, the MonthlyPanel class' main purpose is to just set the layout of the 
monthly chart frame.

*How I solved the problem:*
* I designed the `Spinner` interface, `MonthSpinner` class, and `YearSpinner` class. This improved cohesion because the
methods that I implemented within the `MonthSpinner` and `YearSpinner` classes are more focused on dealing with the 
month and year, respectively.

I originally had lots of duplicated code especially within my `ExpensesTrackerPanel` class. Most of them pertained to
setting the layout of the buttons and formatting the JLabels. 

*How I solved the problem:*
* I refactored them into different methods and included parameters that were the only differences between the duplicated
code. For instance, I originally had `setAddButton`, `setLog`, and `setMonthly` which all had very similar effects, but
each method was for a specific button. I refactored these three similar buttons into `setButton` and `setActionListener`

**Other Problems**
* There was also poor cohesion within the `ExpensesTracker` class, so I designed  the classes`Add` and `Log` to deal 
with the adding of the expenses and displaying of the expenses log, respectively.

* There is tight coupling between the `MonthlyPanel` and `Chart`