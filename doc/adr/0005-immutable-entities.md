# Immutable Entities
Date: 2024-05-30

## Status
Accepted

## Context
The NistFile, NistRecord can be read, write or created.
Depended on the use case, some field must be calculated or not.
When a fil is read, we should not change the calculated fields like TCN or LEN.
But when we create a new NistFile, it's required to calculate these fields.
By the way, create a new entity from another have to be possible.

## Decision
To avoid calculation on writing process, the calculation is done on Builder.build() method.
Entities must not be change after building them.
So entities like NistRecord, NistFile or DataText and DataImage have to be immutable.

Immutable entities must be implemented in a class with the notion of Immutable like :
- DataTextImmutableImpl
- NistFileImmutableImpl
- NistRecordImmutableImpl

## Consequences
Builder class get the responsibility for creation of entities and calculation when it's needed.
NistOptions is created in order to store once for all configuration (charset, calculation preference).
All constructors must get NistOptions for further use.

Builder have to implement `DataBuilder from(D data);` this method create a Builder from an existing entities.
That so a new immutable entity can be created from existing one.
