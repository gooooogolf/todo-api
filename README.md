# Todo-API
This code is to be done at home.
Design and implement a deployable RESTful API backend that store the resource for a simple 'to do' task list.

 [API Document](https://documenter.getpostman.com/collection/view/268043-eeed086f-465b-d21b-e60b-642c17da3e1a#da0b35e1-754b-64b9-0b17-970d885582d5 "API Document")
 
 ![Example API Document](https://github.com/kupring/todo-api/blob/master/src/main/resources/1493556586707.jpg)

 [Postman Collection](https://www.getpostman.com/collections/332acc0d825dca178e14 "Postman Collection")


### How do I get set up? ###

* Install Java 1.8+
* Install Maven 3.x.x (with TAB settings)
* Create system variable JAVA_HOME with the path of your java installation
* Create system variable MAVEN_HOME with the path of your maven installation
* Add JAVA_HOME/bin and MAVEN_HOME/bin to PATH system variable

### To compile use this command ###

```
cd todo-api
mvn clean install
```

### To run use this command ###

```
cd todo-api
java -jar target/todo-api-0.0.1-SNAPSHOT.jar
```

### To check health status use this command ###
```
curl -X GET 'http://localhost:8080/health'

------200 OK------
{
  "status": "UP"
}

```

### Note ###
When app started data will reset.
