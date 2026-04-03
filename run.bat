@echo off
echo Starting Smart Permission Request System...
echo ==============================================

if not exist bin mkdir bin

echo Compiling Java sources...
javac -d bin src\model\*.java src\util\*.java src\dao\*.java src\service\*.java src\main\*.java

if %ERRORLEVEL% == 0 (
    echo Compilation successful!
    echo Running Application...
    REM Ensure lib folder has the SQLite connector
    java -cp "bin;lib\*" main.MainApp
) else (
    echo Compilation failed. Please check the errors above.
)
pause
