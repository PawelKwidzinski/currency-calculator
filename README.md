# Currency-calculator - recruitment task
Use Spring/Spring Boot to create simple API for currency exchange.
### Basic functions and assumptions:
* service should to return data in JSON format using REST
* data are not relevant (can be mocked, hardcoded, randomised)
* return a list of available currency symbols (USD - US dollar, etc. - a few will suffice)
* information on the exchange rate of a particular currency (against all or another specific currency)
* historical information on the exchange rate of a specific currency (exchange rate for a day)
* conversion of currency X of amount K into currency Y
#### [bonus] methods that allow the administrator(spring security) to:
* adding a new symbol/currency
* deleting an existing symbol/currency
* updating an existing symbol/currency
## Main features
Before starting App should be add default user and default password for ADMIN ROLE in application.properties file. 
* CRUD (Currency, Currency Rate, Currency Symbol)
* Searching for currency rates by date
* Calculate specific currency to another
* Test data are from DataInitializer class
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
