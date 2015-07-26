# Hands On Ratpack

A test driven [Ratpack](http://ratpack.io/) workshop.

## Prerequisites

* Java 8
* Git

## Setup

1. Clone this repo
  ```
  git clone https://github.com/rhart/hands-on-ratpack.git
  ```

1. Run a Gradle build, using the Gradle Wrapper, to download the required dependencies
  ```
  cd hands-on-ratpack
  ./gradlew build
  ```

## Instructions

The workshop is comprised of a number of "labs" and each lab is on its own branch.  To work on a lab, checkout the relevant branch e.g.
```
git checkout lab-01
```

In the root of a lab branch is a `LAB.md` file.  This contains the details and instructions for the lab.

Each lab also has an "answer branch", which shows _a_ possible solution.  There are many ways to solve the labs, the answer branch is just one of them.
```
git checkout lab-01-answer
```

## Labs

* `lab-01` - Handlers
* `lab-02` - Handler Refactor
* `lab-03` - Context
* `lab-04` - Google Guice (part 1)
* `lab-05` - Renderers
