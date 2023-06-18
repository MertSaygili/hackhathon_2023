# HACKHATHON 2023 INIFIA - SCROLL UP

## Geliştirme Modeli

<p align="center">
  <img  src="asset/snm.png" height = "400" width = "450"></img>
</p>

## Geliştirilmiş Uygulamalar
- React Web Server
- React Web Application
- Python Script Code - Raspberry pi
- Android Application
- Electron JS - Raspberry pi

## Uygulamaların Özellikleri

### React Web Server - React Web Application
- Tüm cihazların yönetimi buradan gerçekleştirilir.
- Web Soket aracılığıyla cihazlar arası iletişimin sağlamasında merkez rolünü üstlenir.
- Cihazlarların playlistleri burada gösterilir ve yönetilir.
- Cihazlar arası stream yapar. Aynı anda iki resmin veya videonun farklı cihazlarda senkronize olarak oynatılmasıdır.

### Electron JS
- Gelen resim veya videolar bu uygulama sayesinde raspberry pi üzerinde gösterilir.
- Verilerin gösterimi burada gerçekleşir.

### Mobil
- Çevredeki scroll up raspberry pi cihazlar fitrelenerek kullanıcıya gösterilir.
- Gösterilen cihazlarla kullanıcı bluetooth bağlantısı kurar.
- Kurulmuş bluetooth bağlantısı ıle bağlantı kurulmul raspberry pi arasıbda soket oluşturulur.
- Kullanıcı göndermek istediği fotoğrafı seçer.
- Seçilen fotoğraf BASE64'e decode edilir.
- Decode edilmiş BASE64 stringi bluetooth bağlantısı üzerinden raspberry pi'a gönderilir.

### Python Script Code - Raspberry pi
- Raspberry pi etraftan gelecek fotoğraf BASE64 kodunu bekler.
- Gelen veri kendi formatımızda bölünerek, JSON dosyasina dönüştürülür.
- JSON dosyası okunarak fotoğrafa çevrilir.
- Fotoğraf Raspberry pi üzerinde gösterilir.


## WEB Sayfalari

<p align="center">
  <img src="asset/web1.jpeg" height = "400" width = "40%"></img>
  <img src="asset/web2.jpeg" height = "400" width = "40%"></img>
</p>

## Mobil

<p align="center">
  <img src="asset/mobil1.jpeg" height = "400" width = "25%"></img>
  <img src="asset/web2 (2).jpeg" height = "400" width = "25%"></img>
  <img src="asset/mobil3.jpeg" height = "400" width = "25%"></img>
</p>

## Raspberry pi

<p align="center">
  <img src="asset/raspberry.jpeg" height = "400" width = "40%"></img>
</p>

## KULLANILAN TEKNOLOJİLER
<p align="center">
  
  ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
  ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
  ![Electron.js](https://img.shields.io/badge/Electron-191970?style=for-the-badge&logo=Electron&logoColor=white)
  ![Socket.io](https://img.shields.io/badge/Socket.io-black?style=for-the-badge&logo=socket.io&badgeColor=010101)
  ![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
  ![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54)
  ![Raspberry Pi](https://img.shields.io/badge/-RaspberryPi-C51A4A?style=for-the-badge&logo=Raspberry-Pi)
  
</p>


