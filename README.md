# unify4j

## Introduction

unify4J: Java 1.8 skeleton library offering a rich toolkit of utility functions for collections, strings, date/time,
JSON, maps, and more.

## Features

- Comprehensive set of utility functions.
- Written in Java 1.8.
- Well-documented code for easy understanding.
- Regular updates and maintenance.

## Installation

```bash
git clone --depth 1 https://github.com/sivaosorg/unify4j.git
```

## Generation Plugin Java

```bash
curl https://gradle-initializr.cleverapps.io/starter.zip -d type=groovy-gradle-plugin  -d testFramework=testng -d projectName=unify4j -o unify4j.zip
```

## Modules

Explain how users can interact with the various modules.

### Tidying up

To tidy up the project's Java modules, use the following command:

```bash
./gradlew clean
```

or

```bash
make clean
```

### Building SDK

```bash
./gradlew jar
```

or

```bash
make jar
```

### Upgrading version

- file `gradle.yml`

```yaml
ng:
  name: unify4j
  version: v1.0.0
  enabled_link: false # enable compression and attachment of the external libraries
  jars:
    - enabled: false # enable compression and attachment of the external libraries
      source: "" # lib Jar
    - enabled: false
      source: ""
```

## Integration

1. Add dependency into file `build.gradle`

```gradle
implementation files('libs/unify4j-v1.0.0.jar') // filename based on ng.name and ng.version
```
