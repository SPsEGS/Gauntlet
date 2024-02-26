#!/bin/bash

# Compilation script for the project !
# WARNING : The project will be compiled only if the script compileFresh.sh has been used once.
# All the java class files are removed and the project is recompiled.
# The project is ran after.

# Removing all the .class files
find . -name '*.class' -exec rm {} \;

# Compiling the java project

javac -d out -cp src src/controller/*.java
javac -d out -cp src src/EditorMain.java

# Running the project
java -cp out EditorMain
