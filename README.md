[![Java CI with Maven](https://github.com/fabioformosa/quartz-manager/actions/workflows/maven.yml/badge.svg)](https://github.com/fabioformosa/quartz-manager/actions/workflows/maven.yml)
[![npm CI](https://github.com/fabioformosa/quartz-manager/actions/workflows/npm.yml/badge.svg)](https://github.com/fabioformosa/quartz-manager/actions/workflows/npm.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/it.fabioformosa.quartz-manager/quartz-manager-starter-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/it.fabioformosa.quartz-manager/quartz-manager-starter-api)

************************************************
!!! THE UPDATE OF THIS README IS IN PROGRESS !!!
************************************************

# QUARTZ MANAGER
In the last decade, the [Quartz Scheduler](http://www.quartz-scheduler.org/) has become the most adopted opensource job scheduling library for Java applications.  

**Quartz Manager** enriches it with a REST API layer and a handy console UI to easily control and monitor a Quartz scheduler.  
Quartz Manager is a Java library you can import in your Spring-Based Web Application to run scheduled jobs, start&stop them and get the job outcomes. You can do it through HTTP calls against the Quartz Manager API or in a visual manner through the Quartz Manager UI dashboard.  


## UI DASHBOARD
THE UI is a single-page-application provided by the Quartz Manager Java library itself. It leverages the websockets to receive in real-time the trigger updates and the outcomes of the job executions.  

![](https://github.com/fabioformosa/quartz-manager/blob/develop/quartz-manager-parent/quartz-manager-web-showcase/src/main/resources/quartz-manager-4-screenshot.png)

## API
Quart-Manager exposes REST controllers which are documented by an OpenAPI Specification. 

![](https://github.com/fabioformosa/quartz-manager/blob/develop/quartz-manager-parent/quartz-manager-web-showcase/src/main/resources/quartz-manager-4-swagger.png)


# HOW IT WORKS
Quartz Manager can either coexist, in your project, with your existing instance of Quartz or it can import itself the Quartz dependency. In the latter case, Quartz Manager speeds up the setup of a new project, configuring the Quartz Scheduler and protecting the API and the UI with an authentication model based on [JWT](https://jwt.io). You can, for instance, enable the Quartz persistance with a database, without knowing how to do it in details.


**FEATURES**
* You can schedule a [Quartz Simple Trigger](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-05.html) which allows you to start a job now or in a specific datetime and to set it as recurring job with a certain time frequency, in a unlimited way or, alternatevely, limited on the number of fires or within a certain end date.
* The job classes, which can be eligible to be scheduled and monitored by Quartz Manager, have to extend the  `AbstractQuartzManagerJob`.
* You can start, pause and resume the quartz scheduler via API or clicking the play button.
* Leveraging on an active web-socket, the `Quartz-Manager-UI` updates in real time the progress bar and it displays the list of the latest logs produced by your quartz job.
* You can enable a secure layer, if your project doesn't have any, to give access at the API and the UI only to authenticated users.
* You can enable a persistent layer, to persist the config and the progress of your trigger, in a postgresql database.

# QUICK START

**Requirements** 
  Java 9+, Spring Framework 5+ (Spring Boot 2.x)
  
Quart Manager is a modular library:
* quartz-manager-api (mandatory)
* quartz-manager-ui (optional)
* quartz-manager-security (optional)
* quartz-manager-persistence (optional)


Below the list of dependencies and the spring application.properties you can set.

### Quart Manager API Lib

* **Dependency**

**Maven** 
```
<dependency>
  <groupId>it.fabioformosa.quartz-manager</groupId>
  <artifactId>quartz-manager-starter-api</artifactId>
  <version>4.0.0</version>
</dependency>
```
**Gradle**  
```
implementation group: 'it.fabioformosa.quartz-manager', name: 'quartz-manager-starter-api', version: '4.0.0'
```

* **Quartz Manager Job Classes**
The job classes, which can be eligible for triggers managed by Quartz Manager, must extend the super-class `AbstractLoggingJob`. 
In this way, Quartz Manager is able to collect and display the outcomes at the UI console.

 ```
 public class SampleJob extends AbstractLoggingJob {

    @Override
    public LogRecord doIt(JobExecutionContext jobExecutionContext) {
        ... do stuff ...
        return new LogRecord(LogType.INFO, "Hello from QuartManagerDemo!");
    }

}
```

* **App Props**

TBD
| Property    | Values      | Description     |
| :---        |:---         |:---             |
| quartz-manager.foo              | boolean             | TBD   |
| quartz-manager.foo        | string              |       |
| quartz-manager.foo    | string              |       |


* **REST API**  
You can access the REST API, through the swagger-ui. Open the URL:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

(Change the port and the contextPath accordingly with the setup of your webapp)

* **add a `quartz.properties` file in the classpath (`src/main/resources`)**

```
org.quartz.scheduler.instanceName=example
org.quartz.threadPool.threadCount=1
```
`quartz.properties` is a configuration file required by [Quartz Scheduler](http://www.quartz-scheduler.org/). For further details, click [here](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/).

* **Create the job class that you want to schedule**
 



* **Enable quartz-manager adding into the application.yml**

```
quartz:
  enabled: true

quartz-manager:
  jobClass: <QUALIFIED NAME OF THE YOUR JOB CLASS>
```

### Quart Manager API

Import  `Quartz-Manager-Starter-UI` as well, if you want to use the Quartz Manager API by the angular frontend.  

TBD

* **Frontend**  
If you've imported the `quartz-manager-starter-ui` you can open the UI at URL:  
[http://localhost:8080/quartz-manager-ui/index.html](http://localhost:8080/quartz-manager-ui/index.html)

(Change the port and the contextPath accordingly with the setup of your webapp)

### Quart Manager SEcurity

TBD

### Quart Manager Persistence

TBD






## HOW-TO

* **HOW-TO ENABLE A SECURITY LAYER**

If you want enable a security layer and allow the access to the REST API and to the UI only to authenticated users, add the dependency:

MAVEN

```
<dependency>
  <groupId>it.fabioformosa.quartz-manager</groupId>
  <artifactId>quartz-manager-starter-security</artifactId>
  <version>3.1.0</version>
</dependency>
```

GRADLE

```
compile group: 'it.fabioformosa.quartz-manager', name: 'quartz-manager-starter-security', version: '3.1.0'
```

and in your application.yml:

```
quartz-manager:
  security:
    login-model:
      form-login-enabled: true
      userpwd-filter-enabled : false
    jwt:
      enabled: true
      secret: "PLEASE_TYPE_HERE_A_SECRET"
      expiration-in-sec: 28800  # 8 hours
      header-strategy:
        enabled: false
        header: "Authorization"
      cookie-strategy:
        enabled: true
        cookie: AUTH-TOKEN
  accounts:
    in-memory:
      enabled: true
      users:
        - name: admin
          password: admin
          roles:
            - ADMIN      

```

* **HOW-TO ENABLE A PERSISTENCE LAYER**

If you don't want to lose your scheduler config and the progress of your trigger, when you stop&start your webapp, you have to enable a security layer which persists data on a postgresql database. The `quartz-manager-persistence-module` needs a postgresql datasource to create its tables. To import the `quartz-manager-persistence-module`, please add the following dependency:

MAVEN

```
<dependency>
  <groupId>it.fabioformosa.quartz-manager</groupId>
  <artifactId>quartz-manager-starter-persistence</artifactId>
  <version>3.1.0</version>
</dependency>
```

GRADLE

```
compile group: 'it.fabioformosa.quartz-manager', name: 'quartz-manager-starter-persistence', version: '3.1.0'
```

and in your application.yml:

```
quartz-manager:
  persistence:
    quartz:
      datasource:
        url: "jdbc:postgresql://localhost:5432/quartzmanager"
        user: "quartzmanager"
        password: "quartzmanager"   

```

* **HOW-TO BROWSE REST API DOC**

Swagger has been added as library. So, you can get REST API doc opening: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

* **DEMO**

Take a loot to the project [Quartz-Manager Demo](https://github.com/fabioformosa/quartz-manager-demo), it is an example of how-to:
 * import the quartz-manager-api library in your webapp
 * include the quartz-manager frontend (angular based) through a webjar
 * set properties into the application.yml
 * add a secure layer to allow the API only to logged users
 * schedule a custom job (a dummy `hello world`)

## APPLICATION PROPERTIES

| Property    | Values      | Description     |
| :---        |:---         |:---             |
| quartz-manager.accounts.in-memory.enabled              | boolean             | Enable in memory users to login   |
| quartz-manager.accounts.in-memory.users[0].name        | string              |       |
| quartz-manager.accounts.in-memory.users[0].password    | string              |       |

## LIMITATIONS
Initially `Quartz-Manager` was born like a pet-project to start&monitor a repetitive job. Now there's a work-in-progress roadmap to convert it in full-fledged library to manager a [Quartz Scheduler](http://www.quartz-scheduler.org/).  
At the moment, these are the limitations:

* You cannot start multiple triggers or multiple jobs.
* You can start only a simple trigger based on a daily frequency and a max number of occurencies.
* You cannot start/stop a trigger, but the entire scheduler.

Take a look a the [Project Roadmap](https://github.com/fabioformosa/quartz-manager/projects) and feel free to open an issue or add a commment on an existing one, to give your feedback about planned enhancements. Your opinion is important to understand the priority.

## ROADMAP
Open the [Project Roadmap](https://github.com/fabioformosa/quartz-manager/projects) to take a look at the plan of Quartz Manager.  

Next steps in the roadmap are:
* Give to change to import `quartz-manager` in projects which have already imported [Quartz Scheduler](http://www.quartz-scheduler.org/)
* Manage multiple triggers and jobs
* Redesign the API and re-styling the UI
* to add a complete setup UI panel for quartz, in term of cronjobs and multiple jobs.
* Enabling adapters for integrations: kafka, etc.


## HOW-TO CONTRIBUTE  

### PROJECT STRUCTURE
* `quartz-parent/quartz-manager-starter-api` is the library that can be imported in webapp to have the quartz-manager API.
* `quartz-parent/quartz-manager-starter-ui` is a maven module in charge to build and package the angular frontend in a webjar.
* `quartz-parent/quartz-manager-starter-security` is a library that can be imported in a webapp to have a security layer (login) over the quartz-manager API.
* `quartz-parent/quartz-manager-starter-persistence` is a library that can be imported in a webapp to persist the config and the progress of the trigger a Postgresql database.
* `quartz-parent/quartz-manager-web-showcase` is an example of webapp that imports quartz-manager-api. Useful to develop the frontend started locally with the webpack dev server.
* `quartz-frontend` is the angular app that interacts with the Quartz Manager API.

### PROJECT DETAILS
**[requirements]** Make sure you have installed
* [Java 8](https://java.com/download/) or greater
* [Maven](https://maven.apache.org/)
* [npm](https://www.npmjs.com/get-npm), [node](https://nodejs.org) and [angular-cli](https://cli.angular.io/)

To build&run quartz-manager in your machine:

```
#CLONE REPOSITORY
git clone https://github.com/fabioformosa/quartz-manager.git

# START QUARTZ-MANAGER-WEB
cd quartz-manager/quartz-parent
mvn install
cd quartz-manager/quartz-parent/quartz-manager-web-showcase
mvn spring-boot:run

# START QUARTZ-MANAGER-FRONTEND
cd quartz-manager/quartz-manager-frontend
npm install
npm start

```

1. Open browser at [http://localhost:4200](http://localhost:4200)
1. Log in with **default credentials**: `admin/admin`

If you are not confident with maven CLI, you can start it by your IDE. For more details [spring boot ref.](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)


## HOW TO RUN YOUR SCHEDULED JOB
By default, `quartz-manager-web-showcase` executes the dummy job that logs "hello world!".
Replace the dummy job (class: `it.fabioformosa.quartzmanager.jobs.SampleJob`) with yours. Follow these steps:

1. Extend the super class `it.fabioformosa.quartzmanager.jobs.AbstractLoggingJob`
1. set property `quartz-manager.jobClass` with qualified name of your custom Job Class (default job is SampleJob.class)

## HOW TO CHANGE SETTINGS
* Num of Threads: `/quartz-manager-parent/quartz-manager-web/src/main/resources/quartz.properties`
* Credentials: To change admin's password, set ENV var `quartz-manager.account.pwd`
* quartz-manager backend context path (default `/quartz-manager`) and port (default `8080`): `/quartz-manager/src/main/resources/application.properties`

## Tech Overview

**Backend Stack** Java 8, Spring Boot 2.1.4 (Spring MVC 5.1.6, Spring Security 5.1.5, Spring AOP 5.1.6), Quartz Scheduler 2.3.1

**Application Server** Tomcat (embedded)

**Frontend** Angular 9.1.4, Web-Socket (stompjs 2.3.3)

**Style** angular material, FontAwesome 5

From quartz manager ver 2.x.x, the new structure of project is:
 * REST API backend
 * Single Page Application frontend (angular 9)

(The first version of quartz manager was a monolithic backend that provided also frontend developed with angularjs 1.6.x. You can find it at the branch 1.x.x)

## Contributes

Every contribution is welcome. Open a issue, so we can discuss about new features and implement them. 

## Credits

* this project has been created from [angular-spring-starter](https://github.com/bfwg/angular-spring-starter)


