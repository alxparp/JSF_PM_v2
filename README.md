## JSF Project Management

The goal of this project is to create a project management system using the flexible Scrum methodology. The result should be a workable system for joint interaction of teams, which according to calculations should be more productive than sequential.

To realize the goal, the following tasks must be completed:
- study the subject area;
- analyze similar programs;
- create an information model of the system;
- determine the database management system and programming environment;
- design a database;
- develop the interface of the main page and main subpages;
- program the main functions of the system;
- develop a chat for interaction between developers;
- implement a board for fixing tasks;
- create and add sections for creating: iterations, reports, calendar plan, as well as a history for saving actions taken by users;
- provide for the distribution of user access rights and ensure the protection of personal information;
- fill in the database, launch the application and conduct testing.


# The following changes have been added:

1. DAO pattern implemented
2. A new level Services has been added, and the logic from the Controllers has also been moved to this level
3. DTO pattern implemented
4. Some structural changes have been made
5. Requests to the database have been moved to separate interfaces
6. Various checks have been added to the methods for obtaining data from the database
7. Added logging
8. Due to the addition of Java EE annotations, the coupling between objects has decreased


# Technologies

1. Java SE 7
2. Java EE 7
3. JSF/Primefaces
4. HTML/CSS/JavaScript
5. Glassfish 4
6. MySql (mysql-connector-5.1.44)
7. NetBeans
