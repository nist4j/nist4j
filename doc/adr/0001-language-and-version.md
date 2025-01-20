# Language and version
Date: 2024-05-30

## Status
Accepted

## Context
Several projects cas have a use of the library

| Project | Language |
|---------|----------|
| NSIS    | Java 8   |
| PAC     | Java 11  |
| CCAF    | Java 17  |
| Faedv3  | Java 21  |
| PFSE3   | Java 21  |

## Decision
Use of **Java 8** to be compatible with most projects

## Consequences
This language is set in maven pom.xml

```xml
<properties>
    <java.version>8</java.version>
</properties>
```
