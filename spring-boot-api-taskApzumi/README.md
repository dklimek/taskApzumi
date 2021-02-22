# spring-boot-starter
Spring Boot Demo App for Apzumi

This folder contains the source code for the Task Apzumi

== Running Post Api

To build the full project, use `mvnw` (the Maven wrapper) at the command line like this:

[source,sh]
----
% ./mvnw clean package
----


Once the project is built, you can run the executable JAR file from the `post api` project:

[source,sh]
----
% java -jar ./target/spring-boot-starter-0.0.1-SNAPSHOT.jar
----



== Available Endpoints required by task from Apzumi

Get list of Post without userId:
(GET) http://localhost:8080/api/post/rodo

Get list of Post without userId filter by ${title}:
(GET) http://localhost:8080/api/post/ContainingTittle/{title}

Delete post with ${id}
(DELETE) http://localhost:8080/api/post/delete/{id}

Update post with ${id} and json body
(Put) http://localhost:8080/api/post/{id}
@RequestBody Example:
{
    "title":"test_title_test",
    "body": "test_body_test"
}

== Available UI Angular
To use web UI instead postman, jmeter look to angular-11-client