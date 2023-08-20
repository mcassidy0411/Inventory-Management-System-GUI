# Inventory Management System GUI (JavaFX)
## Purpose
```text
This application is an appointment management system designed to help users organize and manage their
tasks and to-do lists. It provides a graphical user interface (GUI) where users can create, update,
and track their tasks, set deadlines, and prioritize tasks based on their importance. The application
is built using object-oriented principles, follows the Model-View-Controller (MVC) architecture, and
integrates with a database to store task-related information.
```

## Key Features
```text
-User Authentication: Users can create accounts and log in to the application to access their personalized task lists.
-Task Creation and Management: Users can create new tasks, assign due dates, set priorities, and add descriptions to each task.
-Task Listing and Sorting: Tasks are listed in the main interface, and users can sort tasks based on deadlines or priorities.
-Task Editing and Deletion: Users can edit task details or mark tasks as completed. Completed tasks can be archived or removed.
-User-Friendly Interface: The GUI provides an intuitive and visually appealing layout for easy interaction.
-Error Handling and Logging: The application employs error-handling mechanisms to gracefully handle unexpected situations and logs events for troubleshooting.
-Database Integration: Task data is stored in a database, allowing users to persist their tasks across application sessions.
-Documentation and Comments: The codebase includes thorough documentation and comments to explain its structure and functionality.
```

## Dependencies
```text
-Java 19 or greater
-JavaFX
-JDBC Driver for MySQL
-MySQL
-Java IDE (IntelliJ is recommended)
```

## Installation
```text
1. Install the latest version of Java (https://www.java.com/en/)
2. Install the latest version of MySQL (https://www.mysql.com/)
3. Install the JavaFX (https://openjfx.io/)
4. Install JDBC Driver for MySQL (https://www.mysql.com/products/connector/)

```

# Detailed Project Requirements
## Introduction

```text
Throughout your career in software design and development, you will be asked to create applications
with various features and functionality based on business requirements. When a new system is
developed, typically the process begins with a business analyst gathering and writing these business
requirements, with the assistance of subject matter experts from the business. Then a system analyst
works with several application team members and others to formulate a solution based on the
requirements. As a developer, you would then create a design document from the solution and finally
develop the system based on your design document.

For this assessment, you will create a Java application using the solution statements provided in the
requirements section.
```

## Scenario

```text
You are working for a small manufacturing organization that has outgrown its current inventory system.
They have been using a spreadsheet program to manually enter inventory additions, deletions, and other
data from a paper-based system but would now like you to develop a more sophisticated inventory
program.

They have provided you with a mock-up of the user interface to use in the design and development of the
system (see the attached “GUI Mock-Up”) and a class diagram to assist you in your work (see the attached
“UML Class Diagram”). The organization also has specific business requirements that must be included as
part of the application. A system analyst from your company created the solution statements outlined in
the requirements section based on the manufacturing organization’s business requirements. You will use
these solution statements to develop your application.
```

## Requirements

```text
I. User Interface

Create a JavaFX application with a graphical user interface (GUI).
Write code to display each of the following screens in the GUI:

A. A main screen, showing the following controls:
  • buttons for “Add”, “Modify”, “Delete”, “Search” for parts and products, and “Exit”
  • lists for parts and products
  • text boxes for searching for parts and products
  • title labels for parts, products, and the application title

B. An add part screen, showing the following controls:
  • radio buttons for “In-House” and “Outsourced” parts
  • buttons for “Save” and “Cancel”
  • text fields for ID, name, inventory level, price, max and min values, and company name or machine
  ID
  • labels for ID, name, inventory level, price/cost, max and min values, the application title, and
  company name or machine ID

C. A modify part screen, with fields that populate with presaved data, showing the following controls:
  • radio buttons for “In-House” and “Outsourced” parts
  • buttons for “Save” and “Cancel”
  • text fields for ID, name, inventory level, price, max and min values, and company name or machine
  ID
  • labels for ID, name, inventory level, price, max and min values, the application title, and company
  name or machine ID

D. An add product screen, showing the following controls:
  • buttons for “Save”, “Cancel”, “Add” part, and “Delete” part
  • text fields for ID, name, inventory level, price, and max and min values
  • labels for ID, name, inventory level, price, max and min values, and the application
  • a list for associated parts for this product
  • a “Search” button and a text field with an associated list for displaying the results of the search

E. A modify product screen, with fields that populate with presaved data, showing the following controls:
  • buttons for “Save”, “Cancel”, “Add” part, and “Delete” part
  • text fields for ID, name, inventory level, price, and max and min values
  • labels for ID, name, inventory level, price, max and min values, and the application
  • a list for associated parts for this product
  • a “Search” button and a text field with associated list for displaying the results of the search

II. Application

Now that you’ve created the GUI, write code to create the class structure provided in the attached “UML
(unified modeling language) Class Diagram”. Enable each of the following capabilities in the application:

F. Using the attached “UML Class Diagram”, create appropriate classes and instance variables with the
following criteria:
  • five classes with the 16 associated instance variables
  • variables are only accessible through getter methods
  • variables are only modifiable through setter methods

G. Add the following functionalities to the main screen, using the methods provided in the attached “UML
Class Diagram”:
  • redirect the user to the “Add Part”, “Modify Part”, “Add Product”, or “Modify Product” screens
  • delete a selected part or product from the list
  • search for a part or product and display matching results
  • exit the main screen

H. Add the following functionalities to the part screens, using the methods provided in the attached
“UML Class Diagram”:
1. “Add Part” screen
  • select “In-House” or “Outsourced”
  • enter name, inventory level, price, max and min values, and company name or machine ID
  • save the data and then redirect to the main screen
  • cancel or exit out of this screen and go back to the main screen
2. “Modify Part” screen
  • select “In-House” or “Outsourced”
  • modify or change data values
  • save modifications to the data and then redirect to the main screen
  • cancel or exit out of this screen and go back to the main screen

I. Add the following functionalities to the product screens, using the methods provided in the attached
“UML Class Diagram”:
  1. “Add Product” screen
    • enter name, inventory level, price, and max and min values
    • save the data and then redirect to the main screen
    • associate one or more parts with a product
    • remove or disassociate a part from a product
    • cancel or exit out of this screen and go back to the main screen
  2. “Modify Product” screen
    • modify or change data values
    • save modifications to the data and then redirect to the main screen
    • associate one or more parts with a product
    • remove or disassociate a part from a product
    • cancel or exit out of this screen and go back to the main screen

J. Write code to implement exception controls with custom error messages for one requirement out of
each of the following sets (pick one from each):
  1. Set 1
    • entering an inventory value that exceeds the minimum or maximum value for that part or
    product
    • preventing the minimum field from having a value above the maximum field
    • preventing the maximum field from having a value below the minimum field
    • ensuring that a product must always have at least one part
  2. Set 2
    • including a confirm dialogue for all “Delete” and “Cancel” buttons
    • ensuring that the price of a product cannot be less than the cost of the parts
    • ensuring that a product must have a name, price, and inventory level (default 0)

K. Demonstrate professional communication in the content and presentation of your submission.
```
