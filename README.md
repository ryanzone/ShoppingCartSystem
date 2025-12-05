# Project: Shopping Cart System #
The Shopping Cart System is a desktop-based application developed using Java (Swing) for the 
frontend and SQLite for the backend database. 

This project provides a simplified digital shopping experience where users can create an account, 
browse products, add items to their cart, and complete their purchase securely. 
The frontend interface is designed with a focus on user-friendliness, modern UI design, and 
smooth navigation between windows.
Each module: Login, Signup, View Browse, Cart, 
Purchase, and Receipt, works together to simulate a real-world shopping workflow. 
The backend ensures data persistence by storing user details, product information, and 
transaction history using SQLite, which allows for lightweight and efficient data management 
without requiring an external server. 

The system demonstrates the integration of GUI-based application development and database 
connectivity in Java, providing a practical example of combining frontend and backend 
technologies in a single cohesive project. 
This application can be extended further by integrating features like search filtering, admin 
management, and online payment gateways in future iterations. 




## Tech Stack ##

● Programming Language: Java (JDK 8 or above) – core language for application 
logic. 

● Graphical User Interface (GUI): Java Swing and AWT – for building windows, 
buttons, tables, and interactive components. 

● Database: SQLite  lightweight, file-based database for storing user, product, and 
transaction data. 

● IDE (Integrated Development Environment): IntelliJ IDEA – for coding, 
debugging, and project management. 

● Database Connectivity: SQLite JDBC Driver – enables Java programs to 
interact with SQLite databases. 

● Layout Managers: BorderLayout, FlowLayout, GridLayout – used for arranging 
GUI components dynamically. 

● Event Handling: ActionListener, MouseListener – for handling button clicks and 
user interactions. 


## Front End Design ##
The frontend of the Shopping Cart System is developed using Java Swing, providing a 
lightweight and platform-independent environment for building interactive desktop applications. 
The system follows a modular window-based architecture, where each JFrame is dedicated to a 
specific function, ensuring a clear structure, intuitive navigation, and easy maintenance. 

● Login Page: Handles user authentication, verifying credentials before granting access to 
the shopping interface. 

● Signup Page: Allows new users to create an account by securely storing their registration 
details in the database. 

● View/Browse Page: Displays all available products in an organized table format, enabling 
users to explore items by category, name, or price. 

● Cart Page: Lets users review their selected items, modify quantities, or remove products, 
with the total price updating dynamically. 

● Purchase Page: Facilitates the final checkout process, allowing users to confirm their 
order and simulate payment actions. 

● Receipt Page: Generates a detailed receipt summarizing the transaction, including order 
ID, purchased items, quantity, total cost, and timestamp. 

The user interface extensively uses Swing components such as JTable, JButton, JLabel, 
JTextField, and JPanel to deliver a responsive and interactive experience. All visual elements are 
consistently styled with proper alignment, labeling, and spacing to enhance readability and 
usability. Layout managers ensure that components adjust dynamically to different window sizes, 
maintaining a clean and balanced appearance. 

The design emphasizes simplicity, accessibility, and user-centered interaction, allowing seamless 
transitions between windows such as Login → Browse → Cart → Purchase → Receipt. By 
integrating clear navigation flow and organized visual hierarchy, the system demonstrates how an 
effective desktop shopping experience can be achieved using fundamental GUI and event-driven 
programming concepts. 

![image alt](https://github.com/ryanzone/ShoppingCartSystem/blob/master/Screenshot%202025-11-04%20224433.png)

![image alt](https://github.com/ryanzone/ShoppingCartSystem/blob/master/Screenshot%202025-11-04%20224458.png)

![image alt](https://github.com/ryanzone/ShoppingCartSystem/blob/master/Screenshot%202025-11-04%20224541.png)


## Table Design ##
The backend database, shopping.db, is implemented using SQLite and consists of three main 
tables: users, products, and cart. Each table is designed to store essential information for the 
shopping system and is linked through logical relationships based on user and product data. 
The users table stores information about registered customers, including their username, email, 
password, and phone number. Each user is identified by a unique user_id, which serves as the 
primary key. The created_at column automatically records the timestamp when a user account is 
created. This table ensures secure management of user credentials and forms the foundation for 
authentication and personalized operations within the system. 

The products table contains details about all items available for purchase. Each product is 
identified by a unique product_id. The table includes key attributes such as product_name, 
category, price, stock, and description. These fields help the backend manage product listings, 
stock levels, and pricing. Any updates or deletions to this table are handled through the 
backend’s ProductService and ProductDAO classes to ensure data consistency. 

The cart table temporarily holds the products that a user adds to their shopping cart. It contains 
the cart_id as the primary key and uses user_id and product_id as references to link each entry to 
a specific user and product. The quantity column stores how many units of a product are added, 
and the added_at timestamp records when the item was added. This table supports multiple 
products per user and acts as an intermediary between user activity and the product inventory.


![image alt](https://github.com/ryanzone/ShoppingCartSystem/blob/master/Screenshot%202025-11-04%20214837.png)


## Back End Design ##

The backend of the Shopping Cart System was developed using Java and SQLite, structured with 
a layered architecture consisting of DAO, Model, and Service packages. This structure ensures 
high modularity, clean separation of concerns, and easy maintenance. The backend is responsible 
for managing all core operations such as user authentication, product management, and shopping 
cart handling, while the GUI layer handles user interaction. 

The system uses SQLite (shopping.db) as the database management system, chosen for its 
simplicity and portability. All interactions between the Java application and the database are 
handled using JDBC (Java Database Connectivity). This allows secure execution of SQL 
statements and efficient data manipulation through prepared statements. The goal of the backend 
is to provide reliable data flow and logical consistency across all shopping operations including 
login, signup, product browsing, and cart management without relying on external frameworks.


![image alt](https://github.com/ryanzone/ShoppingCartSystem/blob/master/Screenshot%202025-11-04%20221051.png)



