I'm using linux(ubuntu 22) user, And at the moment I didn't have a windows to test this :(

So here is my workflow anyway:

downloaded jdk from "https://www.oracle.com/java/technologies/downloads/#java21" 

and installed mysql-connector-j_9.4.0-1ubuntu22.04_all.deb

and files can be find at "ls /usr/share/java/
java-atk-wrapper.jar
java_defaults.mk
libintl-0.21.jar
libintl.jar
mysql-connector-j-9.4.0.jar
mysql-connector-java-9.4.0.jar"

then I coppied into this project structure : cp /usr/share/java/mysql-connector-j-9.4.0.jar ~/SuckingJavaProjects/student-db/lib/

Instructions:

# Student Management System (Console + JDBC)


## Tech Stack
- Java 8+
- JDBC (MySQL Connector/J)
- MySQL
- Console-based input/output


## Setup Instructions
1. Install MySQL and create a database `student_db`.
2. Run the schema definition from `schema.sql` in MySQL.