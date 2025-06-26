@echo off
echo Employee Management Database Deletion Tool
echo.


REM Check if database file exists
if not exist database.db (
    echo Error: database.db not found
    echo Please make sure you are in the correct directory
    pause
    exit /b 1
)


REM Ask for confirmation
set /p confirm=Are you sure you want to delete the database? (Y/N):
if /i not "%confirm%"=="Y" (
    echo Operation cancelled
    pause
    exit /b 0
)


REM Delete the database file
del /f database.db
if %ERRORLEVEL% EQU 0 (
    echo Database deleted successfully!
) else (
    echo Error deleting database
)

pause
