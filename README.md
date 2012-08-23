# JAX-RS Bootstrap Template Application

The goal of this project is to provide a template for an enterprise level Java REST service or web project.
It provides a more complete sample than most trivial sample apps and aims to provide a solid foundation for building,
testing, deploying, extending, and modularizing.  The goal is to provide piecemeal loosely coupled web components to
help build web services and applications while trying not to lock the developers into any specific vendors.  The project
tries to use JSR specifications where possible and give developers flexibility in adding or removing most components.
The JAVA JAX-RS Bootstrap uses the following technologies.

* Maven for the build system.
* JUnit for unit testing.
* JAX-RS for rest services using the Jersey reference implementation.
* Spring 3.1 with JSR-330 annotations for dependency injection.
Spring was chosen over CDI for compatibility with common servlet containers such as Tomcat and Jetty without requiring the complexity of a heavyweight Java EE container.
Using JSR-330 annotations provides a path forward to use CDI as it games adoption in your chosen container or migrating to Google Guice.
* JPA for relational database interaction.
* Spring Data JPA for simplfying the DAO persistence layer.
* SLF4J for logging.
* Shiro for security
* Scalate for web templates.

## Building

    $mvn clean package

## Running

Windows:

    $ run.bat

Linux:
    $ run.sh

Browse localhost:8080

# Developers Guide:

We use a modular project design to split the project into multiple jars.
Model and persistence code goes into the model layer jar.
Services, validation, buisiness logic, and UI go into the webapp jar.

To add a new model and service to the project:

1.  Create a JPA @Entity _ModelName_ in com.bootstrap.models to define database schema.

2.  Create a entity persistence interface that extends BasePersistence<_ModelName_>.
    Spring-data will automatically create an implementation of this interface.
    You can get spring-data to generate more CRUD methods by defining additional
    @Queries on you JPA model classes.  See spring-data docs for more information.

3.  Create a Jax-rs service class that exposes models through REST interface.  Add
    additional business logic here.  Use @Inject on class variable of persistence
    class and spring will automatically inject an implementation of the persistence
    class on startup.
