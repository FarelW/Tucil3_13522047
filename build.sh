#!/bin/bash

MODULE_PATH="/D/Coding/Lib/javafx-sdk-22.0.1/lib"

echo "Building the JavaFX application..."
javac --module-path "$MODULE_PATH" --add-modules javafx.controls,javafx.fxml -d bin src/*.java src/resources/*.java src/utils/*.java

echo "Creating resource directories..."
if [ ! -d "bin/resources" ]; then
    echo "Creating directory bin/resources..."
    mkdir -p bin/resources
fi

echo "Copying FXML and CSS files..."
cp src/resources/Mainscene.fxml bin/resources/
cp src/resources/style.css bin/resources/

echo "Build completed successfully."
