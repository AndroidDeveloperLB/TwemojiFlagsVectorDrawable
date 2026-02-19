@echo off
setlocal enabledelayedexpansion

set "DEST_FOLDER=Twemoji_Android_Flags"

:: 1. Empty the folder first
if exist "%DEST_FOLDER%" (
    echo Cleaning out %DEST_FOLDER%...
    del /q "%DEST_FOLDER%\*"
) else (
    mkdir "%DEST_FOLDER%"
)

echo Filtering and renaming flags...

:: 2. Target ONLY standard 2-part country flags (e.g., 1f1e6-1f1f4)
for %%F in (1f1??-1f1??.svg) do (
    set "fname=%%F"
    set "fname=!fname:-=_!"
    copy "%%F" "%DEST_FOLDER%\twemoji_flag_!fname!" >nul
)

:: 3. Target ONLY the 3 specific UK subdivision flags
for %%F in (1f3f4-e0067-*.svg) do (
    set "fname=%%F"
    set "fname=!fname:-=_!"
    copy "%%F" "%DEST_FOLDER%\twemoji_flag_!fname!" >nul
)

echo ---
echo Done! Only Country and UK flags are in '%DEST_FOLDER%'.
echo All files start with 'twemoji_' and use underscores.
echo ---