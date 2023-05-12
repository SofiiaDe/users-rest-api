Users REST API is an application which can be considered as a reusable part of a system providing functionality to operate user entities, add them to groups, schedule visits, etc.
The API implements best practices of RESTful API (using HATEOAS) as well as a couple of design patterns.

Developed functionality is as follows: 

1. Create user. It allows to register users who are more than [18] years old. The value [18] should be taken from properties file.
2. Update one/some user fields and all user fields.
3. Delete user.
4. Search for users by birth date range using validation.
5. Template Method design pattern (methods onValidate(), onExecute())
6. Factory Method design pattern (ActionFactory, ActionParamsFactory)
The advantages of the abovementioned design patterns are the following:
- Using one method in ActionController allows to add new commands easily and quickly, extend system functionality, override specific steps of the algorithm without changing its structure in the relevant subclasses.
- To create a new action, we just need to add new Factory implementation and according front-end code.
- This functionality may be used from any place in the system.
- The functionality for data validation is embedded into the system.
7. Code is covered by unit and integration tests.
8. Code has error handling mechanism.
9. Spring Boot 2.7.3, Java 17.
10. MapStruct to transfer and map models. 
11. Specifications for filtering and sorting data.
12. Postman Collection is created for endpoints testing (with request and response examples included).
