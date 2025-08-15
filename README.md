# Feed Processing Backend

This sample application supports handling incoming POST requests from external providers.

Currently supported provider endpoints:

*Provider Alpha:*
```
/provider-alpha/feed
```

*Provider Beta:*
```
/provider-beta/feed
```

## Running the application

After cloning this repository, you can run the app by building in the command line, from the project root. This will require JDK installed (minimum version: Java 17). Server will listen to incoming connections at http://localhost:8080.

```shell
./gradlew build
java -jar build/libs/feedprocessing-0.0.1-SNAPSHOT.jar
```

## Running tests

You can find tests under the `src/test/java` directory. They can be run using your favorite IDE or using the command line, from the project root:

```shell
./gradlew test
```
