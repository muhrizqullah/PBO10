# Final Project
## Snake
Snake adalah sebuah video game klasik yang sudah cukup lama dikenal. Game ini dibuat pada akhir tahun 70an. Dalam game ini, pemain mengontrol seekor ular. Tujuannya adalah memakan ceri sebanyak mungkin. Setiap kali ular memakan ceri, maka tubuhnya akan tumbuh. Ular harus menghindari tembok dan tubuhnya sendiri.
## Penjelasan Program
### Game ini memiliki 2 objek utama, yaitu:
* Snake
* Cherry

### Pada program snake ini memiliki beberapa class:
#### Main
Pada class ini berfungsi untuk menginisialisasi frame untuk program. Class ini juga memiliki fungsi main yaitu fungsi utama saat program dijalankan.
#### Point
Class ini merupakan parent class dari tiap-tiap objek yang ada dalam game. Pada class ini terdapat method untuk mengetahui koordinat objek, mengecek Intersect antar objek dan lain-lain.
#### Snakes
Class ini merupakan objek utama dalam game yaitu Snake yang merupakan subclass dari Point. Pada class ini berfungsi untuk menyimpan berapa panjang Snake pada Array Dinamis, serta arah yang sedang dituju oleh snake.
#### Cherry
Class ini merupakan objek yang dimakan oleh Snake dan merupakan subclass dari Point yang didalamnya terdapat fungsi untuk menggambar objek tersebut.
### BigCherry
Class ini juga termasuk objek yang dimakan oleh Snake tetapi dengan nilai score yang berbeda dan dengan waktu spawn 5 detik. Pada class ini terdapat method untuk menggambar objek dan mendapatkan waktu spawn objek.
#### HighScore
Pada class ini berfungsi untuk sistem score tertinggi, Serta juga menyimpan 10 score tertinggi menggunakan `Serializable`.
#### Game
Pada class ini merupakan class terkompleks dengan banyak fungsi. Mulai dari rendering `graphics2d` dari setiap halaman di program, alur jalannya program, update untuk ukuran Snake, Score, serta Cherry pada posisi random.
#### GameLoop
Class ini merupakan subclass dari `java.util.TimerTask ` sebagai timertask dari timer dalam game yang melakukan update game dan repaint tiap 50ms.
#### KeyListener
Pada class ini berfungsi untuk mendeteksi adanya input dari keyboard, dan menentukan apa yang dilakukan saat key tertentu ditekan.
#### SoundEffect
Class ini berfungsi untuk mengatur sound effect dalam game yang di dalamnya terdapat fungsi untuk menjalankan, melooping, dan memberhentikan sound effect.

### Pada program ini juga memiliki beberapa enum:
* Direction
* GameStatus

### Perubahan yang dilakukan dari referensi yang ada:
* Menambahkan main menu dengan pilihan "Mulai main", "Pengaturan", dan "Credits"
* Dapat mengatur warna dari Snake yang akan dimainkan terdapat tujuh pilihan warna
* Dapat mengatur level game dari mudah, sedang, sulit, dan extreme dengan kecepatan move snake dan aturan poin perlevel yang berbeda
* Untuk level extreme terdapat objek penghalang di tengah board game
* Mengatur Snake, Cherry, dan BigCherry sebagai subclass dari Point
* Menambahkan BigCherry sebagai makanan Snake dengan waktu spawn hanya 5 detik dan poin yang diperoleh 3x lipat, saat spawn BigCherry akan terlihat seperti berkedip karena ada batas waktu spawn
* Menambahkan fitur Highscore dengan JOptionPane untuk memasukkan nama dan menyimpan 10 top score dalam game
* Menambahkan sound effect dari tiap action dalam game

### Diagram Class

![Capture60](https://user-images.githubusercontent.com/57831206/104470922-cfb01b80-55ec-11eb-8625-781ab8fcb675.JPG)

### Screenshot Jalannya Program

<img height="400px" alt="DemoApp" src="READMEAssets/DemoAppSnake.gif">

[Video Youtube](https://youtu.be/wpvXzk0td7U)

### Link Referensi
[Snake](https://github.com/renanpvaz/java-snake)

[HighScore](https://github.com/gterrono/tetris/tree/master/src)
