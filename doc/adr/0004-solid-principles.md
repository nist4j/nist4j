# Scope of the library
Date: 2024-05-30

## Status
Accepted

## Context
The library must be evolutive, understandable and avoid breaking change on evolution.

## Decision
[S.O.L.I.D](https://www.baeldung.com/solid-principles) principles will be applied.

## Consequences

Some consequences :
1. Single Responsibility : one class, one Responsibility

So the Builders are split from the entities and lombok is not used for entities builders.

2. Open for Extension, Closed for Modification :
use of Interface, make final class on intend class etc...

3. Liskov Substitution :
add Interface with method for further used (example :
```java
NistRecordBuilder withBeforeBuild(Callback<NistRecordBuilder> callback);
```

4. Interface Segregation :
If 2 capabilities then use 2 interfaces. The Serializer use 2 interfaces for RecordReader and RecordWriter capabilities.

```java
public class DefaultTextRecordSerializer
        extends AbstractTextRecordSerializer<AbstractNistRecordBuilderImpl>
        implements RecordReader, RecordWriter { }
```

5. Dependency Inversion
For example entities constructors `NistFileImmutableImpl` use interface of the Builder
but the implementation of this builder is in `user_case.helpers`
