# 🏟️ PistaYa - Reservas Deportivas

Aplicación Android para la gestión y reserva de instalaciones deportivas. Permite a los usuarios registrarse, iniciar sesión, consultar instalaciones disponibles, hacer reservas y cancelarlas.

---

## 📱 Pantallas de la aplicación

- **Splash** → Pantalla de inicio con animación de carga
- **Login** → Inicio de sesión con correo/nombre y contraseña
- **Registro** → Creación de nueva cuenta de usuario
- **Main** → Lista de instalaciones deportivas disponibles
- **Horarios** → Selección de fecha y hora para hacer una reserva
- **Mis Reservas** → Listado de reservas del usuario con opción a cancelar

---

## ⚽ Instalaciones disponibles

| Deporte | Precio/hora |
|---|---|
| Pádel | 15 € |
| Futsal | 20 € |
| Fútbol 11 | 25 € |
| Fútbol 7 | 25 € |
| Baloncesto | 13 € |

---

## 🗂️ Estructura del proyecto

```
app/src/main/java/
├── com.example.reservasdeportivas/
│   ├── SplashActivity.java
│   ├── LoginActivity.java
│   ├── RegistroActivity.java
│   ├── MainActivity.java
│   ├── HorariosActivity.java
│   ├── MisReservasActivity.java
│   ├── InstalacionAdapter.java
│   └── ReservaAdapter.java
├── models/
│   ├── Usuario.java
│   ├── Instalacion.java
│   └── Reserva.java
├── dao/
│   ├── UsuarioDAO.java
│   ├── InstalacionDAO.java
│   └── ReservaDAO.java
└── conexionBBDD/
    └── ConexionBBDD.java
```

---

## 🛠️ Tecnologías utilizadas

- **Java** → Lenguaje principal
- **Android Studio** → Entorno de desarrollo
- **PostgreSQL** → Base de datos
- **JDBC (Driver PostgreSQL)** → Conexión directa a la base de datos

---

## 👤 Autor

**Pedro** - [@Pepinako99](https://github.com/Pepinako99)
