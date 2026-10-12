# PNG Electoral Commission (PNGEC) Mobile App - Setup & Run Guide

## 1. Project Overview
- **App Name**: PNG Electoral Commission Mobile Application (`PNGElectoral`)
- **Package ID**: `com.pngelectoral`
- **Architecture**: Native Android (Kotlin + Java)
- **Minimum SDK**: Android 4.4 (API 19)
- **Target / Compile SDK**: Android 11 (API 30)
- **Hardware Integration**: Mantra MFS100 Biometric Fingerprint Scanner (`libMFS100V9032.so` + `mantra.mfs100.jar`)

---

## 2. Issues Fixed Before Building
The project had multiple legacy blockers which have now been completely resolved:
1. **Sunsetted JCenter Dependencies**:
   - `jcenter()` repository shut down by JFrog, breaking `fetch2`, `permissionhelper`, `anko`, and `AVLoadingIndicatorView`.
   - **Fix**: Added Appodeal's public Artifactory mirror (`https://artifactory.appodeal.com/appodeal-public/`) in `build.gradle` and updated repository order.
2. **Java 17 Module Encapsulation (`java.io.File.path` & `jdk.compiler`)**:
   - Modern JDK 17 strictly enforces module boundaries, causing reflection errors in AGP 4.1.3 and Kotlin `kapt`.
   - **Fix**: Configured comprehensive `--add-opens` and `--add-exports` JVM flags in `gradle.properties` for both Gradle and Kotlin daemons.
3. **Corrupted `mantra.mfs100.jar`**:
   - The local JAR had a corrupted ZIP central directory header and truncated bytecode (`ZipException: invalid entry size`).
   - **Fix**: Repaired with a clean, fully intact SDK JAR and added Jetifier ignore rules.

---

## 3. Product Flavors & Backend Endpoints

| Flavor | Environment | Base API URL | Build Variant Name |
| :--- | :--- | :--- | :--- |
| **`dev`** | Development | `http://20.231.26.70:5003/api/mobile/` | `devDebug` / `devRelease` |
| **`training`** | Training | `http://20.231.26.70:5003/api/mobile/` | `trainingDebug` / `trainingRelease` |
| **`production`** | Production | `http://20.231.26.70:5002/api/mobile/` | `productionDebug` / `productionRelease` |

---

## 4. How to Build the App (Command Line)

Open PowerShell in the project directory:
```powershell
cd c:\Users\qfn\Desktop\shahbaz\png_mobile
```

### A. Build Dev Debug APK (Recommended for Testing):
```powershell
.\gradlew.bat assembleDevDebug
```
**Output Location**:
`app\build\outputs\apk\dev\debug\PNGEC2026.6.02(21)_YYYY_MM_DD-dev-debug.apk`

### B. Build Production Debug APK:
```powershell
.\gradlew.bat assembleProductionDebug
```

### C. Build Signed Release APK:
Keystore details are located in `keypassword`:
- **Keystore file**: `pngprovincialapp.jks`
- **Alias**: `pngec`
- **Password**: `pngec2022`

```powershell
.\gradlew.bat assembleProductionRelease
```

---

## 5. How to Run the App on a Device or Emulator

### Method 1: Direct Install on a Physical Android Phone via USB (Best for Fingerprint Hardware)
1. **Enable Developer Options on phone**:
   - Settings > About Phone > Tap **Build Number** 7 times.
2. **Enable USB Debugging**:
   - Settings > System / Developer Options > Enable **USB Debugging**.
3. **Connect Phone via USB** to PC and allow the "USB Debugging Authorization" prompt on the screen.
4. **Verify device connection**:
   ```powershell
   & "C:\Users\qfn\AppData\Local\Android\Sdk\platform-tools\adb.exe" devices
   ```
   *(You should see your device ID with status `device`)*.
5. **Install and run the APK**:
   ```powershell
   & "C:\Users\qfn\AppData\Local\Android\Sdk\platform-tools\adb.exe" install -r "c:\Users\qfn\Desktop\shahbaz\png_mobile\app\build\outputs\apk\dev\debug\PNGEC2026.6.02(21)_2026_10_12-dev-debug.apk"
   ```
6. **Launch the App via ADB**:
   ```powershell
   & "C:\Users\qfn\AppData\Local\Android\Sdk\platform-tools\adb.exe" shell am start -n com.pngelectoral/.ui.activity.SplashActivity
   ```

---

### Method 2: Open and Run via Android Studio
1. Open **Android Studio** (`C:\Program Files\Android\Android Studio\bin\studio64.exe`).
2. Click **File > Open** and select the folder:
   `c:\Users\qfn\Desktop\shahbaz\png_mobile`
3. Wait for Gradle Sync to complete.
4. Open the **Build Variants** tab (bottom-left) and select:
   - **Active Build Variant**: `devDebug` (or `productionDebug`).
5. Select your connected phone or emulator in the device selector dropdown.
6. Click the green **Run** button (▶) or press `Shift + F10`.

---

### Method 3: Run on Android Emulator
1. In Android Studio, go to **Tools > Device Manager**.
2. Click **Create Device** (e.g. Pixel 4 or Pixel 5).
3. Select system image (API 29 or API 30 recommended).
4. Launch the emulator.
5. In PowerShell, install the APK:
   ```powershell
   & "C:\Users\qfn\AppData\Local\Android\Sdk\platform-tools\adb.exe" install -r "c:\Users\qfn\Desktop\shahbaz\png_mobile\app\build\outputs\apk\dev\debug\PNGEC2026.6.02(21)_2026_10_12-dev-debug.apk"
   ```

> [!NOTE]
> Physical USB OTG devices like the **Mantra MFS100 fingerprint scanner** require a real Android phone with OTG support to function properly. Emulators do not support USB OTG fingerprint scanners directly.

---

## 6. Key Configuration Files
- `build.gradle` - Top-level build script with repositories and plugin versions.
- `app/build.gradle` - Module build config, flavors, SDK versions, dependencies.
- `gradle.properties` - Memory and JVM encapsulation arguments.
- `app/src/main/AndroidManifest.xml` - App permissions, activities, hardware features.
