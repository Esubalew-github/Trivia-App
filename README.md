# Trivia Application using Spring Boot and React

This repository contains a simplified application for creating and solving quizzes made for the purpose of assessment. The task is to implement a Java application with two REST APIs and a user interface to offer a set of Trivia multiple-choice questions to a user and the possible answers for each. The user selects the answer and is shown whether he/she is correct or not by the application.

I used Spring Boot to implement the backend part of the above task & created a controller that provides APIs for retrieving the questions and checking the user's answer. The `QuizController` class is a `RestController` which has request mapping methods (`Get` and `Post`) for the RESTful requests. A [React](https://reactjs.org/) web frontend that consumes these two REST APIs also implemented.

The quiz setting in the frontend allows the user to select the `category` and `difficulty` of the quiz. These preferences are used in the trivia API call. The class `WebServiceRespository` uses a retrofit instance to call the trivia API with the parameters entered by the user. The returned JSON is mapped to an `TriviaResult` POJO using GSON. This `TriviaResult` class is a container for a list of `TriviaQuestion` objects, which contain all relevant information about each question. `WebServiceRespository` class will provide this list of `TriviaQuestion` objects, when it is called by the `QuizController` class in the controller.

## Running the application

- First clone the application from github:

```
git clone https://github.com/Esubalew-github/Trivia-App
```

- Then, the Spring Boot Maven plugin can be used to quickly compile and run the application. Hence, you can run the app by typing the following command:

```
mvn spring-boot:run
```

The server will start on port `8080`.

## Operations and their results

---

### Getting all questions

Below is an example of a response from the `Get` request which contains a JSON with 5 multiple questions:

```
curl -X GET http://localhost:8080/api/questions?amount=5&category=10&difficulty=easy
```

```json
[
  {
    "question": "George Orwell wrote this book, which is often considered a statement on government oversight.",
    "correct_answer": "1984",
    "category": "Entertainment: Books",
    "difficulty": "easy",
    "incorrect_answers": [
      "The Old Man and the Sea",
      "Catcher and the Rye",
      "To Kill a Mockingbird"
    ]
  },
  {
    "question": "&quot;Green Eggs And Ham&quot; is a book by which author?",
    "correct_answer": "Dr. Seuss",
    "category": "Entertainment: Books",
    "difficulty": "easy",
    "incorrect_answers": ["Beatrix Potter", "Roald Dahl", "A.A. Milne"]
  },
  {
    "question": "Which famous spy novelist wrote the childrens&#039; story &quot;Chitty-Chitty-Bang-Bang&quot;?",
    "correct_answer": "Ian Fleming",
    "category": "Entertainment: Books",
    "difficulty": "easy",
    "incorrect_answers": ["Joseph Conrad", "John Buchan", "Graham Greene"]
  },
  {
    "question": "Who wrote the young adult novel &quot;The Fault in Our Stars&quot;?",
    "correct_answer": "John Green",
    "category": "Entertainment: Books",
    "difficulty": "easy",
    "incorrect_answers": [
      "Stephenie Meyer",
      "Suzanne Collins",
      "Stephen Chbosky"
    ]
  },
  {
    "question": "What is the name of the three headed dog in Harry Potter and the Sorcerer&#039;s Stone?",
    "correct_answer": "Fluffy",
    "category": "Entertainment: Books",
    "difficulty": "easy",
    "incorrect_answers": ["Spike", "Poofy", "Spot"]
  }
]
```

---

### Checking user's answer

To check the user's answer, we need to pass the answer in `JSON` format and the current question index [using `@Pathvaraiable` annotation] via `POST` request.
The result of the user's answer is determined by the value of the `boolean` key in the response json and it will return `true` if it is correct or `false` if it is incorrect. The response json also conatains the corresponding correct answer.

Here is an example with `curl`:

```
curl -X POST -H 'Content-Type: application/json' \
http://localhost:8080/api/checkanswer/1/ --data '{"userAnswer":"Roald Dahl"}'
```

```
[false,"Dr. Seuss"]
```

```
curl -X POST -H 'Content-Type: application/json' \
http://localhost:8080/api/checkanswer/1/ --data '{"userAnswer":"Dr. Seuss"}'
```

```
[true,"Dr. Seuss"]
```

---

## Test the application

`QuizControllerTest` is the main test class used for testing the request methods implemented in the `QuizController.java` class.

After running it using:

```
mvn test
```

the result shows that all five tests [that I tried] passed the test.

---

# Web Frontend

In the project directory

```
cd src/main/webapp/frontend
```

You can run:

```
npm install
```

```
npm start
```

and open [http://localhost:3000](http://localhost:3000) to view it in the browser.
