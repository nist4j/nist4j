# Nist4j

## Description

Java library for read, write, create and validate Nist format files (NBIS).

## Record Types and features implementation

| RT   | Read | Write | Build | Validate |
|------|------|-------|-------|----------|
| RT1  | Y    | Y     | Y     | Y        |
| RT2  | Y    | Y     | Y     | Y        |
| RT3  | Y    | Y     | Y     | Y        |
| RT4  | Y    | Y     | Y     | Y        |
| RT5  | Y    | Y     | Y     | Y        |
| RT6  | Y    | Y     | Y     | Y        |
| RT7  | Y    | Y     | Y     | N        |
| RT8  | Y    | Y     | Y     | N        |
| RT9  | Y    | Y     | Y     | N        |
| RT10 | Y(1) | Y(1)  | Y(2)  | N        |
| RT11 | Y(1) | Y(1)  | Y(2)  | N        |
| RT12 | Y(1) | Y(1)  | Y(2)  | N        |
| RT13 | Y    | Y     | Y     | Y        |
| RT14 | Y    | Y     | Y     | Y        |
| RT15 | Y    | Y     | Y     | N        |
| RT16 | Y(1) | Y(1)  | Y(2)  | N        |
| RT17 | Y(1) | Y(1)  | Y(2)  | N        |
| RT18 | Y(1) | Y(1)  | Y(2)  | N        |
| RT19 | Y(1) | Y(1)  | Y(2)  | N        |
| RT20 | Y(1) | Y(1)  | Y(2)  | N        |
| RT21 | Y(1) | Y(1)  | Y(2)  | N        |
| RT22 | Y(1) | Y(1)  | Y(2)  | N        |
| RT98 | Y(1) | Y(1)  | Y(2)  | N        |
| RT99 | Y(1) | Y(1)  | Y(2)  | N        |

- (1) Using default serializer
- (2) You can build using fields id but no Fields enum is provided

## Usage

### Creating a NistFile

```java
NistRecord rt1 = new RT1TransactionInformationNistRecordBuilderImpl(CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE)
        .withField(RT1FieldsEnum.VER, newFieldText("0400"))
        .withField(RT1FieldsEnum.DAT, newFieldText("20091117"))
        .withField(RT1FieldsEnum.DAI, newFieldText("DAI000000"))
        .build();
NistFile nistFile = new CreateNistFile().execute()
    .withRecord(RT1, rt1)
    .build();
```

### Read a NistFile

```java
NistFile nistFile = new ReadNistFile().execute(Files.newInputStream("input.nist"));
```

### Write a NistFile

```java
File outputFile = File.createTempFile("output", ".nist");
WriteNistFile writeNistFile = new WriteNistFile();
OutputStream os = writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath()));
```

### Validate a NistFile

```java
ValidateNistFileWithStandardFormat validateNistFileWithStandardFormat = new ValidateNistFileWithStandardFormat();
List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);
```

## License

Nist4j is licensed by Sopra Steria
under the Apache License, version 2.0.
A copy of this license is provided in the LICENSE file.

## Inception

The Initial Developer of some parts of the framework, which are copied from, derived from, or
inspired by Jnbis (https://github.com/mhshams/jnbis) under Apache 2.0 License is Mohammad Sarbandi.
