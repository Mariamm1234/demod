# REST API for an E-Commerce Application

- This API performs fundamental CRUD operations for an e-commerce platform.
## E-R Diagram for the Application

 ```markdown
     ![E-Commerce API ER Diagram](images\Database ER diagram (crow's foot) (1).png)
     ```

### Features
* Customer and vendor features:
- User authentication and validation with token
- User login and register
-  Cart management adding and removing
-  Checkout managemnt
* Vendor features:
-  Product management adding, editing, deleting and featch all of products by vendor
-  Vendor transactions recording
  *Shopify:
-  Integreation with shopify


### Technologies Used

-Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Postgresdb
- Docker
- Shopify
- Postman for API documentation

### Setup Instructions

* Before running the API server, you should update the database config inside the [application.properties](demod\src\main\resources\application.properties) file. 
* Update the port number, username and password as per your local database config.
1. Clone the repository:
   ```bash
   git clone[ https://github.com/your-repo.git](https://github.com/Mariamm1234/Demod.git)
   ```
2. Run docker and use postgres image on it
```
   $ docker run --name some-postgres -e POSTGRES_PASSWORD_FILE=/run/secrets/postgres-passwd -d postgres
   ```
4. Run database file from cmd
   ```
   $ docker cp test2.sql [docker container name]:/
   $ docker container exec -it [docker container name] bash
   $psql -U postgres --file test2.sql
   ```
5. Run progect from editor terminal
   ```
   ./mvnw clean spring-boot:run 
   ```
## API Root Endpoint

`https://localhost:8080/api`
   
## API Module Endpoints

### Customer

* `POST /regestier` : Register a new customer
* `GET /login` : Logging in customer with valid token and email
* `GET /token`: Get token with valid email, password and bussines name if vendor
* `POST /cart/addtocart` : Add product to cart
* `DELETE /cart/deletecart` : Delete from cart with valid email and product id

### Vendor

* `POST /regestier` : Register a new customer
* `GET /products/allproducts` : Featch all products
* `POST /products/addproduct` : Add product 
* `DELETE /products/deleteproduct` : Delete product
* `PUT /products/editproduct` : Edit specific product
* `GET /transactions/vendortransaction` : Featch all vendor transactions
* `POST /transactions/addtransaction` : Add transaction

    
