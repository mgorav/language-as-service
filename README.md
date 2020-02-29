# Notebook As Service
Execute python code as a service. We live in the world of polygot programming lanauge. GraalVM provide a universal VM for lanauges like Java, Python, Kotlin, R etc. This project provide Notebook As Service for language "python" using Spring BOOT. This service provide ease of interaction with python. It execute script, along with the outcome of the scirpt as , it provides error if there are any.
    
    
# Installation 

Clone project into a local folder.

```$shell 
$ git clone https://github.com/mgorav/notebook-as-service.git
```

## Maven Installation

### Maven 

Install Maven

### GraalVM 
Download and install Graal from  [GraalVM homepage](https://www.graalvm.org/). 
following instruciton at: [Getting Started with GraalVM](https://www.graalvm.org/docs/getting-started/). 



###Graal python installer
Download [GraalVM python](https://www.graalvm.org/docs/reference-manual/languages/python/)
```$shell
$ gu -c  -L install python-installable-svm-java8-darwin-amd64-20.0.0.jar
```

Verify python installation:

```$shell
$ gu -list
```

![graal lanauges](./graal-languages.png)

### Build and Run project 


````
$ mvn package -DskipTests
````

Time to play:

```
$ java -jar target/notebook-as-service-1.0.0-SNAPSHOT.jar
```

Play time...Hit the url:
```
http://localhost:6464/swagger-ui.html

```
![Swaager](./swagger.png)

### Example 1 simple python print

- Request Body :
```json
{
  "code": "%python print('Hello Graal')", 
  "interactionId": "graal-interactionId-1"
}
```

*NOTE* interactionId provides ability to distinguish between two users. Futher it provides mechanism of continuation of the same execution context of the user.

*NOTE* %python provides mechanism to switch between different languages.

- Using classical curl :

```
$ curl -X POST  http://localhost:6464/execute  -d '{"code": "%python print(\"Hello Graal\")", "interactionId": "graal-interactionId-1"}'
```

