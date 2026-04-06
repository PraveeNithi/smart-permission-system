@echo off
echo Starting Native Java Web Server and Backend Process...
echo ======================================================

if not exist bin mkdir bin

echo [1/2] Compiling Java API Controller and Services...
javac -d bin src\model\*.java src\util\*.java src\dao\*.java src\service\*.java src\main\*.java

if %ERRORLEVEL% == 0 (
    echo [2/2] Compilation successful!
    echo ======================================================
    echo Serving ReactJS UI through Built-in Java HttpServer
    echo Please open your browser to: http://localhost:8080
    echo ======================================================
    java -cp "bin;lib\*" main.WebServerApp
) else (
    echo Compilation failed. Please check the errors above.
)
pause
