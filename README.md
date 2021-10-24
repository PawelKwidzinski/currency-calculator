# Currency-calculator - recruitment task
Use Spring/Spring Boot to create simple API for currency exchange.
#### Main points
* service should return data in JSON format using REST
* data is not relevant (can be mocked, hardcoded, randomised)
* return list of available currency symbols (USD - US dollar, etc. - a few will suffice)
* information on the exchange rate of a particular currency (against all or another specific currency)
* historical information on the exchange rate of a specific currency (exchange rate for a day)
* conversion of currency X of amount K into currency Y
#### [Bonus] methods that allow the administrator(spring security) to:
* add a new symbol/currency
* delete an existing symbol/currency
* update an existing symbol/currency
## General
Application with "Currency", "Currrency Rate" and "Currency Symbol" as model displaying in JSON format. Data is stored in a MySQL database. Test data are from DataInitializer class. Before starting App should default user and default password be added for ADMIN ROLE in application.properties file.
## Main features
* CRUD (Currency, Currency Rate, Currency Symbol)
* HTTP methods: POST, PATCH and DELETE are available for ADMIN role
* Searching for currency rates by date
* Calculate specific currency to another
## Technologies
* Java 11
* Maven
* Spring Boot 2.4.11
* Spring Data
* Spring Security
* MySql
* Lombok
* Swagger 2.9.2
## Endpoints
![alt text](https://github.com/PawelKwidzinski/currency-calculator/blob/master/pr_scr/currency-controller.png)
![alt text](https://github.com/PawelKwidzinski/currency-calculator/blob/master/pr_scr/currency-rate-controller.png)
![alt text](https://github.com/PawelKwidzinski/currency-calculator/blob/master/pr_scr/currency-symbol-controller.png)
#### application.properties file:
* `spring.datasource.url=`
* `spring.datasource.username=`
* `spring.datasource.password=`
* `spring.jpa.hibernate.ddl-auto=create`
* `spring.datasource.driver-class-name=`
* `server.error.include-message=always`
* `default.username=`
* `default.username.password=`
