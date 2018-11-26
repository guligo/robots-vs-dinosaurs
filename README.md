# Robots vs Dinosaurs

## Description

Nubank is assembling an army of remote-controlled robots to fight the dinosaurs and the first step towards that is to run simulations on how they will perform. You are tasked with implementing a service that provides a REST API to support those simulations.

These are the features required:

- Be able to create an empty simulation space - an empty 50 x 50 grid;
- Be able to create a robot in a certain position and facing direction;
- Be able to create a dinosaur in a certain position;
- Issue instructions to a robot - a robot can turn left, turn right, move forward, move backwards, and attack;
- A robot attack destroys dinosaurs around it (in front, to the left, to the right or behind);
- No need to worry about the dinosaurs - dinosaurs don't move;
- Display the simulation's current state;
- Two or more entities (robots or dinosaurs) cannot occupy the same position;
- Attempting to move a robot outside the simulation space is an invalid operation.

## Tech Stack

Solution is built in Clojure programming language and using following libraries:

- [Compojure API + Swagger](https://github.com/metosin/compojure-api) for REST
- [Prismatic Schema](https://github.com/plumatic/schema) for data validation
- [Hiccup](https://github.com/weavejester/hiccup) for HTML rendering
- [Midje](https://github.com/marick/Midje) for testing
- Other misc libs and plugins:
  - [clojure.tools.logging](https://github.com/clojure/tools.logging)
  - [codox](https://github.com/weavejester/codox)
  - [ring-mock](https://github.com/weavejester/ring-mock)

For compilation, testing and running [Leiningen](https://leiningen.org) build automation tool is required.

## Instructions

In order to run project locally:

1. Execute ```lein ring server-headless```
1. Access http://localhost:3000/docs/index.html#/default in browser to make simulation REST API calls via Swagger UI
1. Access http://localhost:3000/dashboard to see visual representation of simulation

Other useful commands:

- Tests can be executed using ```lein midje```
- Code coverage can be measured using ```lein cloverage --runner :midje``` 
- Since code is documented using Docstrings, command ```lein codox``` can be used to generate documentation

## Design

Simulation is considered as list of actors. Each actor is an abstract entity and has properties ```type```, ```row```, ```col``` and ```id```. Actors are added to list and updated based on various rules:

- For example, it is impossible to add actor whose ```row``` and ```col``` properties are outside of simulation grid boundaries.
- It is impossibe to add actor to simulation if actor with such location already exists.
- etc.

Such implementation is easily extendable, since dinosaur is just an actor of type ```dinosaur``` and robot is an actor of type ```robot``` with additional property ```dirn``` which represents direction. With such design adding actor of another type to simulation is not a problem.

Even though this problem naturally maps to 2D array, I decided to go with vector structure. It makes solution more scalable since vector contains only actors that are part of simulation, whereas 2D array would reflect empty fields too. For 50 x 50 grid it makes little difference, however for larger grids it would.

## Notes

- Namespace ```com.nubank.exercise.app-integration-test``` represents integration test which takes quite a bit of time to run and hence is ignored using ```future-facts```.
