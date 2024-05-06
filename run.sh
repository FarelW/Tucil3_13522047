#!/bin/bash

MODULE_PATH="/D/Coding/Lib/javafx-sdk-22.0.1/lib"

echo "Starting JavaFX application..."

java --module-path "$MODULE_PATH" --add-modules javafx.controls,javafx.fxml -cp bin Main
