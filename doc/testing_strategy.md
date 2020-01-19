# testing Strategy

## Cucumber

We use cucumber to implement automated functional tests for our APIs.

These tests are useful to make sure if the application returns the correct http status code and whether the body of the response (if there is one) has a correct shape.

## Jmeter

We used Jmeter to load and performance test our app.

### 1000 load
![pour1000](pics/Pour01000.PNG)

### 5000 load
![pour5000](pics/Pour05000.PNG)

### 10000 load
![pour10000](pics/Pour10000.PNG)

### Opinion

Our tests seem fairly complete and in working order.

Similar to our last project all our tests are run manually but for larger and longer projects we would need a better testing strategy like setting up a testing pipeline.
