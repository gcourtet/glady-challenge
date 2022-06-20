# Glady Challenge Backend
I've decided to build an API using SpringBoot in order to respond to the challenge.
From my understanding of the problem, it wasn't necessary to use SpringBoot or to build an API but I felt that it could be more interesting to do so.

## How to run the app?
You'll need Java 11 in order to make it work.
To run the app you'll need to first get the dependencies by running:
```shell
mvn clean install
```

You can then either run the App by starting the `GladyChallengeApplication` class or run the JAR produced in the target folder with the command:
```shell
java -jar glady-challenge-1.0.0.jar
```

The App will then listen on the port 8080 for incoming request and you'll be able to test the implementation.

## Quick API description
(I could've user Swagger annotations or created a Postman collection for this but I wanted to respond to the challenge quick enough. I hope that this README will do.)

Here is a description of the different endpoints that I implemented
_(All request bodies are expected to be of type `application/json`)_:

### /api/companies

- Create company:
```shell
POST /api/companies

{
    "name": "Glady",
    "initialBalance": 999999.99
}
```

- Get company:
```shell
GET /api/companies/{id}
```

- Add money to company balance:
```shell
POST /api/companies/{id}/add-to-balance

{
    "amount": 99999.99
}
```

### /api/users

- Create user:
```shell
POST /api/users

{
    "name": "John Doe"
}

or (to add User to Company employees) :

{
    "name": "John Doe",
    "companyId": 1
}
```

- Get user:
```shell
GET /api/users/{id}
```

- Get user balance:
```shell
GET /api/users/{id}/balance
```

### /api/deposits

- Create deposit:
```shell
POST /api/deposits/new-deposit

{
  "type": "MEAL",
  "amount": 9.80,
  "companyId": 1,
  "userId": 1
}

type must be "MEAL" | "GIFT"
```

## What could be improved?
- I've tried to put a few logs but a lot more should be added before sending this app to production.
- I haven't written integration tests so some of these should be added.
- I think that my current error handling covers the most important cases but I might have missed some.
- I decided to store the User / Company and Deposit in memory but they could've been stored in a database or in an in-memory DB like H2.
- We might later decide to configure an amount until which a company can give deposits that is lower than 0.
- We could make an endpoint in order to do batch deposits (could be useful for meal deposits each month or for gift deposits during Christmas time for example).
