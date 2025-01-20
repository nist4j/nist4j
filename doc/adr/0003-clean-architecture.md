# Scope of the library
Date: 2024-05-30

## Status
Accepted

## Context
The library must be upgradeable, understandable and avoid breaking change on evolution.
The library does not need application, or infrastructure implementation.

## Decision
This library will implement the clean architecture organisation and naming.
[S.O.L.I.D](https://www.baeldung.com/solid-principles) principles will be applied.
1. Single Responsibility : one class, one Responsibility
2. Open for Extension, Closed for Modification :
use of Interface, make final class on intend class etc...
3. Liskov Substitution :
add Interface with method for further used (example : `NistRecordBuilder.beforeBuild()`)
4. Interface Segregation :
If 2 capabilities then use 2 interfaces (example : `RecordReader` and `RecordWriter` in `DefaultTextRecordSerializer.java`)
5. Dependency Inversion
For example entities constructors `NistFileImmutableImpl` use interface of the Builder  but the implementation of this builder is in `user_case.helpers`

## Consequences
The library package will be :

```
- entities/
- enums/
- exceptions/
- use_cases/
  - DoSomething.java => is a UseCase class implementing only execute method
  - helpers/
    - builders/
    - calculators/
    - serializer/
    - validators/
```

The Test LayeredArchitectureTest is implemented in order to respected call hierarchy between packages

Only this call are accepted :
```
* use_cases -> entities
* (use_cases,entities) -> enums
* (use_cases,entities,enums) -> exceptions
```
