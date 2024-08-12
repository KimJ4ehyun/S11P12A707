@echo off

REM Python 설치 여부 확인
python --version >nul 2>&1
IF %ERRORLEVEL% EQU 0 (
    echo Python is already installed.
) ELSE (
    echo Python is not installed. Installing now...

    REM Python 설치 프로그램 다운로드
    curl -o python-installer.exe https://www.python.org/ftp/python/3.11.4/python-3.11.4-amd64.exe

    REM Python 설치 프로그램 실행
    python-installer.exe /quiet InstallAllUsers=1 PrependPath=1

    REM 설치 확인
    python --version
)

REM Pip 설치 여부 확인
pip --version >nul 2>&1
IF %ERRORLEVEL% EQU 0 (
    echo Pip is already installed.
) ELSE (
    echo Pip is not installed. Installing now...

    REM Pip 설치
    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python get-pip.py
    pip install --upgrade pip
)

REM Psutil 설치
pip install psutil

REM 레지스트리 추가
REG ADD "HKCU\Software\Google\Chrome\NativeMessagingHosts\com.google.chrome.top" /ve /t REG_SZ /d "%~dp0com.google.chrome.top.json" /f

REM 설치 확인
python --version
pip --version

pause
