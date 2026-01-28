@echo off
echo Building School Management System...

REM Create lib directory if it doesn't exist
if not exist "lib" mkdir lib

REM Check if SQLite JDBC driver exists
if not exist "lib\sqlite-jdbc-*.jar" (
    echo ERROR: SQLite JDBC driver not found in lib directory
    echo Please download sqlite-jdbc-x.x.x.jar from:
    echo https://github.com/xerial/sqlite-jdbc/releases
    echo and place it in the lib directory
    pause
    exit /b 1
)

REM Compile Java files
echo Compiling Java source files...
cd src
javac -cp "..\lib\*" *.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo.
    echo To run the application:
    echo   java -cp ".;..\lib\*" SchoolApp
    echo.
    echo To run tests:
    echo   java -cp ".;..\lib\*" TestSystem
) else (
    echo Compilation failed!
)

cd ..
pause
