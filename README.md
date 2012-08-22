# JAX-RS Template Application

This is a template for a lightweight RESTful API using JAX-RS. The sample code is a call for getting the current time.
    
## Running the application locally

First build with:

    $mvn clean install

Then run it with:

    $ run.bat

Browse localhost:8080

# Developers Guide:

This project uses Spring 3.1 with JSR-330 annotations for dependency injection an spring-data-jpa for the persistence layer.
We use an annotation and java file config for Spring configuration.
We use SLF4J for logging.
We use Maven 3 for our build system.
We use Jax-rs Jersey for our webapp.

We use a modular project design to split the project into multiple jars.  
Model and persistence code goes into the model layer jar.
Services, validation, buisiness logic, and UI go into the webapp jar.

To add a new model and service to the project:

1.  Create a JPA @Entity _ModelName_ in com.bootstrap.models to define database schema.

2.  Create a entity persistence interface that extendes BasePersistence<_ModelName_>.
    Spring-data will automatically create an implementation of this interface.
    You can get spring-data to generate more CRUD methods by defining additional
    @Queries on you JPA model classes.  See spring-data docs for more information.

3.  Create a Jax-rs service class that exposes models through REST interface.  Add
    additional business logic here.  Use @Inject on class variable of persistence
    class and spring will automatically inject an implementation of the persistence
    class on startup.
