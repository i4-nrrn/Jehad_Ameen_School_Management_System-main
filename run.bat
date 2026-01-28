@echo off
echo Starting School Management System...

REM Check if compiled classes exist
if not exist "src\SchoolApp.class" (
    echo Classes not found. Running build first...
    call build.bat
    if %ERRORLEVEL% NEQ 0 (
        echo Build failed. Cannot run application.
        pause
        exit /b 1
    )
)

REM Run the application
cd src
echo Launching GUI Application...
java -cp ".;..\lib\*" SchoolApp

cd ..
pause
