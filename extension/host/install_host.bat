REG ADD "HKCU\Software\Google\Chrome\NativeMessagingHosts\com.google.chrome.example.echo" /ve /t REG_SZ /d "%~dp0com.google.chrome.example.echo-win.json" /f
pip install psutil