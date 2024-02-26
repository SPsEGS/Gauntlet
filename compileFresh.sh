#!/bin/bash

# Compilation script for the project !
# This file has to be used when the compilation is done for the first time.
# The out folder is removed and the project is compiled, then the resources are copied to the folder
# that contains the compiled project.
# The project is ran after.

# Removing the old folder that contains the compiled files
rm -r out

# Compiling the java project
javac -d out -cp src src/controller/*.java
javac -d out -cp src src/EditorMain.java

# Copying the resources
find src/components -name "*.fxml" -exec cp {} out/components \;
find src/view -name "*.fxml" -exec cp {} out/view \;
cp -r src/style out/style
cp -r src/textures out/textures

# Running the project
java -cp out EditorMain

