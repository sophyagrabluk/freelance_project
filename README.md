# Application for searching freelancers and posting services
<hr>
This application is an analogue of a freelance rialto.

## Database

Application use PostgreSQL database. For start the application you need Postgres server (jdbc:postgresql://localhost:5432/freelance_db) with created database 'freelance_db'. Database contains five tables.

* Table _users_table_ - contains information about application users;
* Table _services_table_ - contains information about application services;
* Table _orders_table_ - contains information about application orders between users and services;
* Table _feedback_table_ - contains information about feedbacks for services;
* Table _l_users_services_ - link table between users and services (user can add any service to their favourite).

## Available endpoints for users

* http://localhost:8080/user - GET method, show all available users
* http://localhost:8080/user/{id} - GET method, show information about one user
* http://localhost:8080/user - POST method, create user
* http://localhost:8080/user/addFav - POST method, add service to user's favourite
* http://localhost:8080/user - PUT method, update user
* http://localhost:8080/user/changePassword - PUT method, change user's password
* http://localhost:8080/user - DELETE method, delete current user's account
* http://localhost:8080/user/removeFav - DELETE method, remove service from user's favourite

* http://localhost:8080/service - GET method, show all user available services
* http://localhost:8080/service/{id} - GET method, show information about one service
* http://localhost:8080/service/fromUser/{userId} - GET method, show services provided by one user
* http://localhost:8080/service/section/{section} - GET method, show services from one section
* http://localhost:8080/service/sortByRating - GET method, show services according rating
* http://localhost:8080/service - POST method, create service
* http://localhost:8080/service - PUT method, update service
* http://localhost:8080/service - DELETE method, delete service

* http://localhost:8080/order/{id} - GET method, show information about one order
* http://localhost:8080/order/myActiveOrders/{userId} - GET method, show information about user's active orders
* http://localhost:8080/order/myFinishedOrders/{userId} - GET method, show information about user's finished orders
* http://localhost:8080/order - POST method, create order
* http://localhost:8080/order/finish - PUT method, finish order

* http://localhost:8080/feedback/{toWhichServiceId} - GET method, show all available feedbacks for one service
* http://localhost:8080/feedback - POST method, create feedback
* http://localhost:8080/feedback - PUT method, update feedback's comment
* http://localhost:8080/feedback - DELETE method, delete one feedback


## Available endpoints for admins

* http://localhost:8080/user - GET method, show all available users
* http://localhost:8080/user/{id} - GET method, show information about one user
* http://localhost:8080/user/admin - GET method, show all users' information
* http://localhost:8080/user - POST method, create user
* http://localhost:8080/user/addFav - POST method, add service to user's favourite
* http://localhost:8080/user/changePassword - PUT method, change user's password
* http://localhost:8080/user/removeFav - DELETE method, remove service from user's favourite
* http://localhost:8080/user/admin - DELETE method, delete user's account

* http://localhost:8080/service - GET method, show all user available services
* http://localhost:8080/service/{id} - GET method, show information about one service
* http://localhost:8080/service/fromUser/{userId} - GET method, show services provided by one user
* http://localhost:8080/service/section/{section} - GET method, show services from one section
* http://localhost:8080/service/sortByRating - GET method, show services according rating
* http://localhost:8080/service/admin - GET method, show all user services' information
* http://localhost:8080/service/admin/{id} - GET method, show all information about one service
* http://localhost:8080/service/admin/fromUser/{userId} - GET method, show all services provided by one user
* http://localhost:8080/service/admin/section/{section} - GET method, show all services from one section
* http://localhost:8080/service - POST method, create service
* http://localhost:8080/service - PUT method, update service
* http://localhost:8080/service/admin - DELETE method, delete service

* http://localhost:8080/feedback/{toWhichServiceId} - GET method, show all available feedbacks for one service
* http://localhost:8080/feedback/admin/{toWhichServiceId} - GET method, show all feedbacks for one service
* http://localhost:8080/feedback - DELETE method, delete one feedback
* http://localhost:8080/feedback - POST method, create feedback
* http://localhost:8080/feedback - PUT method, update feedback's comment
* http://localhost:8080/feedback/admin - DELETE method, delete one feedback

* http://localhost:8080/order/{id} - GET method, show information about one order
* http://localhost:8080/order/myActiveOrders/{userId} - GET method, show information about user's active orders
* http://localhost:8080/order/myFinishedOrders/{userId} - GET method, show information about user's finished orders

<hr>

The technology stack of this project: Java, Spring (Boot, Security, Data), Hibernate, PostgreSQL,
Lombok, AOP, Swagger, Mockito.

<br>