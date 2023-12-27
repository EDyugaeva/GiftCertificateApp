# Task

The system should be extended to expose the following REST APIs:
1. Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
2. User entity  (implement only get operations for user entity)

3. Make an order on gift certificate for a user (user should have an ability to buy a certificate).
4. Get information about user’s orders.
5. Get information about user’s order: cost and timestamp of a purchase (The order cost should not be changed if the price of the gift certificate is changed.)
6. Get the most widely used tag of a user with the highest cost of all orders. (Create separate endpoint for this query.)
7. Search for gift certificates by several tags (“and” condition).
8. Pagination should be implemented for all GET endpoints. 
9. Support HATEOAS on REST endpoints.






## Application requirements
1. JDK version: 8. Use Streams, java.time.*, an etc. where it is appropriate. (the JDK version can be increased in agreement with the mentor/group coordinator/run coordinator)
2. Application packages root: com.epam.esm.
3. Java Code Convention is mandatory (exception: margin size –120 characters).
4. Apache Maven/Gradle, latest version. Multi-module project.
5. Spring Framework, the latest version.
6. Database: PostgreSQL/MySQL, latest version.
7. Testing: JUnit, the latest version, Mockito.
8. Service layer should be covered with unit tests not less than 80%.
9. Hibernate should be used as a JPA implementation for data access.
10. Spring Transaction should be used in all necessary areas of the application.
11. Audit data should be populated using JPA features.


## How to run


`docker-compose up`

 run spring boot application

standard port (8080) is used

## Endpoints

### TagController:

POST: /tags - create new tag, name of new tag in body

GET: /tags - get all tags

GET: /tags/{id} - get tag with {id}

GET: /tags/user/{id} - get the most popular tag (with the highest total price) by user with {id}

DELETE: /tags/{id} - delete tag with {id}

### GiftCertificateController:

POST: /certificates - create new gift certificate

GET: /certificates - get all gift certificates

GET: /certificates/{id} - get gift certificate with {id}

DELETE: /certificates/{id} - delete gift certificate with {id}

PATCH: /certificates/{id} - update gift certificate with {id}, in body could be gift certificate with not all parameters

PATCH: /certificates/update/duration/{id} - update duration in gift certificate with {id}

PATCH: /certificates/update/price/{id} - update price in gift certificate with {id}

GET: /certificates/tags - get gift certificate with tag names

GET: /certificates/search - get gift certificate with sorting and filtering. Parameters are not required.

for filtering:

    - name
    - description
    - tagName

for creating order:

    - sort

example:

/certificate/search?**name**=gift&**description**=description&**tagName**=new tag name&**sort**=createDate:desc,name:desc

get all gift certificates having in name "gift", in description "description", tag name with value "new tag name" ordering by date and name desc


### UserController

GET: /users - get all users

GET: /users/{id} - get user with {id}

GET: /users/{id}/orders - get orders from user with {id}


### OrderController:

POST: /orders - create new order

GET: /orders/{id} - get order with {id}