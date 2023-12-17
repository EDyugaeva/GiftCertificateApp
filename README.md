# Task

Business requirements
1.	Developing web service for Gift Certificates system with the following entities (many-to-many)

   ![image](https://github.com/EDyugaeva/GiftCertificateApp/assets/94297798/00322936-e238-4421-be22-3069b70dde9a)

 
•	CreateDate, LastUpdateDate - format ISO 8601 (https://en.wikipedia.org/wiki/ISO_8601). Example: 2018-08-29T06:12:15.156.
•	Duration - in days (expiration period)

2. The system should expose REST APIs to perform the following operations:
•	CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Patch insert is out of scope.
•	CRD operations for Tag.
•	Get certificates with tags (all params are optional and can be used in conjunction):
o	by tag name (ONE tag)
o	search by part of name/description (can be implemented, using DB function call)
o	sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).


## Application requirements
1.	JDK version: 8 – use Streams, java.time.*, etc. where it is possible. (the JDK version can be increased in agreement with the mentor/group coordinator/run coordinator)
2.	Application packages root: com.epam.esm
3.	Any widely-used connection pool could be used.
4.	JDBC / Spring JDBC Template should be used for data access.
5.	Use transactions where it’s necessary.
6.	Java Code Convention is mandatory (exception: margin size – 120 chars).
7.	Build tool: Maven/Gradle, latest version. Multi-module project.
8.	Web server: Apache Tomcat/Jetty.
9.	Application container: Spring IoC. Spring Framework, the latest version.
10.	Database: PostgreSQL/MySQL, latest version.
11.	Testing: JUnit 5.+, Mockito.
12.	Service layer should be covered with unit tests not less than 80%.
13.	Repository layer should be tested using integration tests with an in-memory embedded database (all operations with certificates).


## How to run


`docker-compose up`

`mvn clean package`

`mvn jetty:run '-Dspring.profiles.active=prod'`

standard port (8080) is used

## Endpoints

### TagController:

POST: /tag - create new tag, name of new tag in body

GET: /tag - get all tags

GET: /tag/{id} - get tag with {id}

DELETE: /tag/{id} - delete tag with {id}

### GiftCertificateController:

POST: /certificate - create new gift certificate

GET: /certificate - get all gift certificates

GET: /certificate/{id} - get gift certificate with {id}

DELETE: /certificate/{id} - delete gift certificate with {id}

PUT: /certificate/{id} - update gift certificate with {id}, in body could be gift certificate with not all parameters

GET: /certificate/search - get gift certificate with sorting and filtering. Parameters are not required.

for filtering:

    - name
    - description
    - tagName

for creating order:

    - ordering


example:

/certificate/search?**name**=gift&**description**=description&**tagName**=new tag name&**ordering**=date desc, name desc

get all gift certificates having in name "gift", in description "description", tag name with value "new tag name" ordering by date and name desc

