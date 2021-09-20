# MyLocation [![Download](https://img.shields.io/github/v/release/mirfatif/MyLocation?label="Download")](https://github.com/mirfatif/MyLocation/releases/latest)
Know your geo coordinates using on-device GPS and Network location providers

<a href="https://f-droid.org/packages/com.mirfatif.mylocation"><img alt="Get it on F-Droid" src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" height="100"></a>
<a href="https://play.google.com/store/apps/details?id=com.mirfatif.mylocation.ps"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" height="100"></a>
<a href="https://apt.izzysoft.de/fdroid/index/apk/com.mirfatif.mylocation"><img alt="Get it on F-Droid" src="https://gitlab.com/IzzyOnDroid/repo/-/raw/master/assets/IzzyOnDroid.png" height="100"></a>

## Features

My Location finds your device's location in the following ways:

* <b>GPS</b> is usually the most accurate method. But a position fix may take some time or may not work at all due to signal loss. <b>Lock GPS</b> feature runs a persistent service to keep connected with the satellites.

  You can also see the list of visible satellites with their PRNs (unique identifiers) and SNR (signal quality).
 
* <b>Network Location Provider</b> uses Wi-Fi or Cellular ids to estimate the location. On the devices with Google Play Services installed, NLP usually uses Google Location Service at backend.
* <b>UnifiedNLP</b> is an open source API which has been used to develop multiple NLP backends (https://github.com/microg/UnifiedNlp/wiki/Backends).

Furthermore:

* Location coordinates can be copied to clipboard or opened in a maps app, if installed.
* Clearing A-GPS aiding data is also supported.

<b>Note</b> that My Location is not a location provider like UnifiedNLP (or microG GmsCore). It just shows the location information received from AOSP location providers (GPS and Network) or directly from UnifiedNLP Backends (whichever are available on the device).

## Screenshots

<img src="fastlane/metadata/android/en-US/images/phoneScreenshots/1.jpg" width="250"> <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/2.jpg" width="250"> <img src="fastlane/metadata/android/en-US/images/phoneScreenshots/3.jpg" width="250">

## Translations [![Crowdin](https://badges.crowdin.net/my-location/localized.svg)](https://crowdin.com/project/my-location)
[Crowdin](https://crowdin.com/project/my-location)

## Third-Party Resources

* https://github.com/androidx/androidx
* https://github.com/material-components/material-components-android
* https://github.com/microg/android_external_UnifiedNlpApi
* https://github.com/square/leakcanary
* https://github.com/sherter/google-java-format-gradle-plugin
* https://github.com/saket/Better-Link-Movement-Method

## License [![License](https://img.shields.io/github/license/mirfatif/MyLocation?label="License")](https://github.com/mirfatif/MyLocation/blob/master/LICENSE)

You **CANNOT** use and distribute the app icon in anyway, except for **My Location** (`com.mirfatif.mylocation`) app.

    My Location is free software: you can redistribute it and/or modify
    it under the terms of the Affero GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    Affero GNU General Public License for more details.

    You should have received a copy of the Affero GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

## Contact Us

**Telegram:** [https://t.me/MyLocationApp](https://t.me/MyLocationApp)  
**XDA Thread:** [https://forum.xda-developers.com...](https://forum.xda-developers.com/t/app-5-0-my-location-know-your-geo-coordinates.4306185/)  
**Email:** [mirfatif@gmail.com](mailto:mirfatif@gmail.com)
