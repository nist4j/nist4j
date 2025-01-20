# Java Code Style
Date: 2024-06-24

## Status
Accepted

## Context
Different developers can have different code style and may occur some conflit for merging.

## Decision
Using the Google java code style and use maven to format the code.

## Consequences
In Intellij use this plugin : `google-java-format` with the file [0006/intellij-java-code-style.xml](https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml)
In Maven `spotless-maven-plugin` with `googleJavaFormat` is used.
