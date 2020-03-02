# Language As Service

Each language comes with its runtime environment and other infrastructure intricacies,. Lets put an end to this & have a generic runtime execution which aides in setting up env/infra etc once and for all. Possibly yes, using GraalVM. 

GraalVM, provides, polyglot virtual machine, as a alternative for Google’s Go (golang). This VM is a “universal/generic VM”. It can run applications written in variety of languages like JavaScript, Python, Ruby, C, and C++, and JVM based languages like Java, Kotlin, and Scala.

Checkout my project which demonstrate "Language As Service": https://lnkd.in/e2t6_s3

This provide ability to execute "python" (possibly many other languages) code as a service.  We live in the world of polyglot programming language. GraalVM provide a universal VM for languages like Java, Python, Kotlin, R etc. This service provide's - Language As Service for language "python" using Spring BOOT. This service provide ease of interaction with python. It execute script, along with the outcome of the script as , it provides error if there are any.

So let's start, stop learning "languages", and concentrate on providing business functionality/infrastructure in the language of your choice.
hashtag#graalvm
# Installation 

Clone project into a local folder.

```$shell 
$ git clone https://github.com/mgorav/language-as-service.git
```

## Maven Installation

### Maven 

Install Maven

### GraalVM 
Download and install Graal from  [GraalVM homepage](https://www.graalvm.org/). 
following instruciton at: [Getting Started with GraalVM](https://www.graalvm.org/docs/getting-started/). 



### Graal python installer
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

Docker run:

```$shell
mvn dockerfile:build

docker run -p 6464:6464 language-as-service/language-as-service
```

Time to play:

```
$ java -jar target/language-as-service-1.0.0-SNAPSHOT.jar
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

