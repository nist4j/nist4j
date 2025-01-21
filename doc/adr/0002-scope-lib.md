# Scope of the library
Date: 2024-05-28

## Status
Accepted

## Context
- The library should not be dependent on projects needs. Example : a fix on a custom class must not create a new version
- Classes from standard NIST and classes from projects implementation should be separated

## Decision
This library should contain :
- any elements inherited from NIST standard
- any elements facilitating the manipulation of nist file (create, read, write)

This library should not contain :
- custom implementations from projects

## Consequences
The library named "nist4j-sample" is available for examples of custom implementation or extensions
