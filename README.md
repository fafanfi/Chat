# Simple-Chat<br>
<br>
![Sample Chat](https://c1.staticflickr.com/2/1947/45229478822_3dc49bd214_b.jpg)<br>
<br>
Belajar implementasi socket dari aplikasi Chat sederhana, dan bagaimana dasar mekanisme komunikasi client-server di aplikasi chatting hanya menggunakan socket programming (terutama di java).<br>
<br>
### Cara menjalankan aplikasi<br>
Pertama compile file Server.java dan jalankan. Kedua compile file Client.java dan jalankan. Jalankan atau buka aplikasi Client seperlunya untuk mencoba komunikasi antar client.<br>
<br>
***Note:*** Saya lupa menambahkan keyListener ke component jTextArea, supaya jTextArea otomatis scroll ke bawah jika Chat melebihi view. Kalian bisa menambahkan kode ini di Constructor `((DefaultCaret) jTextArea1.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE)`
