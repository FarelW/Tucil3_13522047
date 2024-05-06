@echo off
echo Starting JavaFX application...

java --module-path "D:\Coding\Lib\javafx-sdk-22.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp bin Main
