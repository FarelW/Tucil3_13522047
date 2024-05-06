@echo off

set MODULE_PATH=D:\Coding\Lib\javafx-sdk-22.0.1\lib

echo Building the JavaFX application...
javac --module-path "%MODULE_PATH%" --add-modules javafx.controls,javafx.fxml -d bin src\*.java src\resources\*.java src\utils\*.java


echo Creating resource directories...
if not exist "bin\resources\" (
    echo Creating directory bin\resources...
    mkdir "bin\resources"
)

echo Copying FXML and CSS files...
xcopy /Y "src\resources\Mainscene.fxml" "bin\resources\"
xcopy /Y "src\resources\style.css" "bin\resources\"

echo Build completed successfully.