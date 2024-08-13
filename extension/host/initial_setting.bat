@echo off

python --version >nul 2>&1
IF %ERRORLEVEL% EQU 0 (
    echo Python is already installed.
) ELSE (
    echo Python is not installed. Installing now...

    curl -o python-installer.exe https://www.python.org/ftp/python/3.11.4/python-3.11.4-amd64.exe

    python-installer.exe /quiet InstallAllUsers=1 PrependPath=1

    python --version
)

pip --version >nul 2>&1
IF %ERRORLEVEL% EQU 0 (
    echo Pip is already installed.
) ELSE (
    echo Pip is not installed. Installing now...

    timeout /t 10

    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python get-pip.py
    pip install --upgrade pip
)

pip install psutil

python --version
pip --version

pause
